package clueGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JFrame;
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
	public static JPanel frame = new JPanel();
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
//		if (players.get(playerIndex % players.size()).getClass() == HumanPlayer.class) {
//			if (((HumanPlayer) players.get(playerIndex % players.size())).moved) {
//				if (((HumanPlayer) players.get(playerIndex % players.size())).)
//				playerIndex++;
//				return;
//			}
//			else {
//				
//			}
//			
//		}
//		else {
//			System.out.println("Computer");
//			playerIndex++;
//		}
		
		ArrayList<Player> players = board.getPlayers();
		// Skipping human player for testing purposes
		Player i = players.get(playerIndex % players.size());
		if (i.mustMove) {
			JOptionPane.showMessageDialog(ClueGame.theInstance, "You must finish your turn!", "Wait!", JOptionPane.ERROR_MESSAGE);
		}
		else {
			playerIndex++;
			int roll = rollDice();
			board.calcTargets(i.getRow(), i.getColumn(), roll);
			BoardCell bc = i.pickLocation((HashSet<BoardCell>) board.getTargets());
			i.makeMove(bc);
			System.out.println(bc.getRow() + " " + bc.getCol());
			revalidate();
			repaint();
		}
	}
	
	public int rollDice() {
		Random r = new Random();
		int x = r.nextInt(6) + 1;
		System.out.println(x);
		gui.setDice(x);
		return x;
	}

	public static void main(String[] args) {
		frame.add(gui, BorderLayout.CENTER);
		frame.setBorder(new TitledBorder(new EtchedBorder(), "Control Panel"));


		// Setting up game
		board.setConfigFiles("BoardLayout.csv", "Legend.txt", "person.txt", "weapons.txt");	
		board.initialize();
		board.dealCards();
		
		ClueGame clueGame = new ClueGame();
		JPanel cardFrame = clueGame.createCardPanel();

		clueGame.add(cardFrame, BorderLayout.EAST);
		clueGame.add(frame, BorderLayout.SOUTH);
		clueGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clueGame.setVisible(true);
		clueGame.repaint();
		JOptionPane.showMessageDialog(clueGame, "You are Master Chief, press Next Player to begin playing", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

	}

}
