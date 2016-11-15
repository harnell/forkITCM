package clueGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGUI.GUI_display;
import clueGUI.GUI_notes;

@SuppressWarnings("serial")
public class ClueGame extends JFrame{

	private static Board board = Board.getInstance();
	private GUI_notes dnotes = new GUI_notes();
	private int playerIndex = 0;

	public static ClueGame theInstance = new ClueGame();
	public static GUI_display gui = new GUI_display();

	public ClueGame() {
		setTitle("Clue");
		setSize(975,900);
		add(board, BorderLayout.CENTER);
		createMenu();
	}

	private JMenuItem createDnotes() {
		JMenuItem notes = new JMenuItem("Detective Notes");
		class DnotesListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				dnotes.setVisible(true);
			}
		}
		notes.addActionListener(new DnotesListener());
		return notes;
	}

	private JMenuItem createExit() {
		JMenuItem exit = new JMenuItem("Exit");
		class ExitListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		exit.addActionListener(new ExitListener());
		return exit;
	}

	private void createMenu() {
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);

		JMenu menu = new JMenu("File");
		menu.add(createDnotes());
		menu.add(createExit());
		bar.add(menu);
	}

	private JPanel createCardPanel() {
		JPanel tframe = new JPanel();
		JPanel tpanel = new JPanel();
		tpanel.setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		tpanel.setLayout(new GridLayout(3,1));

		JPanel ppanel = new JPanel();
		JPanel rpanel = new JPanel();
		JPanel wpanel = new JPanel();
		ppanel.setBorder(new TitledBorder(new EtchedBorder(), "Person Card(s)"));
		rpanel.setBorder(new TitledBorder(new EtchedBorder(), "Room Card(s)"));
		wpanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Card(s)"));
		ppanel.setLayout(new GridLayout(0,1));
		rpanel.setLayout(new GridLayout(0,1));
		wpanel.setLayout(new GridLayout(0,1));

		for (Card c: board.getPlayers().get(0).getMyCards()) {
			JTextField text = new JTextField(20);
			text.setEditable(false);
			text.setText(c.getCardName());
			if (c.getType() == CardType.PERSON) {
				ppanel.add(text);
			}
			else if (c.getType() == CardType.ROOM) {
				rpanel.add(text);
			}
			else {
				wpanel.add(text);
			}
		}
		tpanel.add(ppanel);
		tpanel.add(rpanel);
		tpanel.add(wpanel);
		tframe.add(tpanel);
		return tframe;
	}


	public void nextPlayer() {
		ArrayList<Player> players = board.getPlayers();
		Player i = players.get(playerIndex % players.size());
		if (board.mustFinish) {
			JOptionPane.showMessageDialog(ClueGame.theInstance, "You must finish your turn!", "Wait!", JOptionPane.ERROR_MESSAGE);
		}
		else {
			playerIndex++;
			int roll = rollDice();
			gui.setWhoseTurn(i.getPlayerName());
			board.calcTargets(i.getRow(), i.getColumn(), roll);
			i.makeMove();
		}
	}

	public int rollDice() {
		Random r = new Random();
		int x = r.nextInt(6) + 1;
		gui.setDice(x);
		return x;
	}
	
	public void accusePlayer() {
		int index = (playerIndex % 6) - 1;
		if (index < 0) {
			index = 5;
		}
		
		int row = board.getPlayers().get(index).getRow();
		int col = board.getPlayers().get(index).getColumn();
		if (index != 0) {
			System.out.println(index);
			JOptionPane.showMessageDialog(ClueGame.theInstance, "You can only accuse on your turn!", "Not your turn!", JOptionPane.ERROR_MESSAGE);
		}
		else if (board.getCellAt(row, col).isWalkway()) {
			JOptionPane.showMessageDialog(ClueGame.theInstance, "You must be in a room to accuse!", "Not a Room!", JOptionPane.ERROR_MESSAGE);
		}
		else if (!board.mustFinish) {
			JOptionPane.showMessageDialog(ClueGame.theInstance, "You can only accuse at the beginning of your turn!", "Wait a bit!", JOptionPane.ERROR_MESSAGE);
		}
		else {
			((HumanPlayer) board.getPlayers().get(index)).makeAccusation();
		}
	}
	
	public void accusePopUp(Player p) {
		String r = board.getLegend().get(board.getCellAt(p.getRow(), p.getColumn()).getInitial());
		
		JFrame accuse = new JFrame();
		accuse.setLayout(new GridLayout(4,1));
		
		JPanel first = new JPanel();
		first.setLayout(new GridLayout(1,2));
		JLabel rlabel = new JLabel("Your Room: ");
		JTextField rname = new JTextField(20);
		rname.setEditable(false);
		rname.setText(r);
		first.add(rlabel);
		first.add(rname);
		
		JPanel second = new JPanel();
		second.setLayout(new GridLayout(1,2));
		JLabel plabel = new JLabel("Person: ");
		JComboBox<String> pcombo = new JComboBox<String>();
		for (Card c: board.getPersonDeck()) {
			pcombo.addItem(c.getCardName());
		}
		second.add(plabel);
		second.add(pcombo);
		
		JPanel third = new JPanel();
		third.setLayout(new GridLayout(1,2));
		JLabel wlabel = new JLabel("Weapon: ");
		JComboBox<String> wcombo = new JComboBox<String>();
		for (Card c: board.getWeaponsDeck()) {
			wcombo.addItem(c.getCardName());
		}
		third.add(wlabel);
		third.add(wcombo);
		
		JPanel fourth = new JPanel();
		fourth.setLayout(new GridLayout(1,2));
		JButton submit = new JButton("Submit");
		JButton cancel = new JButton("Cancel");
		
		class SubmitListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				String person = (String) pcombo.getSelectedItem();
				String room = r;
				String weapon = (String) wcombo.getSelectedItem();
				((HumanPlayer) p).setAccusation(person, room, weapon);
				accuse.setVisible(false);
			}
		}
		
		class CancelListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				accuse.setVisible(false);
			}
		}
		
		submit.addActionListener(new SubmitListener());
		cancel.addActionListener(new CancelListener());
		fourth.add(submit);
		fourth.add(cancel);
		
		accuse.add(first);
		accuse.add(second);
		accuse.add(third);
		accuse.add(fourth);
		accuse.setSize(400, 400);
		accuse.setVisible(true);
	}
	
	public void EndGame(Player p) {
		if (p.getClass() == HumanPlayer.class) {
			JOptionPane.showMessageDialog(ClueGame.theInstance, "Congratulation, you have won Clue!", "Woohoo!", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(ClueGame.theInstance, p.getPlayerName() + " has won Clue!", "Better luck next time...", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void main(String[] args) {
		// Setting up game
		board.setConfigFiles("BoardLayout.csv", "Legend.txt", "person.txt", "weapons.txt");	
		board.initialize();
		board.dealCards();

		ClueGame clueGame = new ClueGame();
		JPanel cardPanel = clueGame.createCardPanel();
		JPanel controlPanel = new JPanel();
		controlPanel.add(gui, BorderLayout.CENTER);
		controlPanel.setBorder(new TitledBorder(new EtchedBorder(), "Control Panel"));

		clueGame.add(cardPanel, BorderLayout.EAST);
		clueGame.add(controlPanel, BorderLayout.SOUTH);
		clueGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clueGame.setVisible(true);
		clueGame.revalidate();
		clueGame.repaint();
		JOptionPane.showMessageDialog(clueGame, "You are Master Chief, press Next Player to begin playing", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

	}

}
