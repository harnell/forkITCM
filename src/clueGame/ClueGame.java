package clueGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	public ClueGame() {
		setTitle("Clue");
		setSize(647,722);
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
	
	private void createCardPanel() {
		JFrame tframe = new JFrame();
		tframe.setSize(200, 300);
		JPanel tpanel = new JPanel();
		tpanel.setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		tpanel.setLayout(new GridLayout(3,1));
		
		for (Card c: board.getPlayers().get(0).getMyCards()) {
			JTextField text = new JTextField(20);
			text.setText(c.getCardName());
			tpanel.add(text);
		}
		tframe.add(tpanel);
		tframe.setVisible(true);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 300);
		GUI_display gui = new GUI_display();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);

		// Setting up game
		board.setConfigFiles("BoardLayout.csv", "Legend.txt", "person.txt", "weapons.txt");	
		board.initialize();
		board.dealCards();
		
		ClueGame clueGame = new ClueGame();
		clueGame.setVisible(true);
		clueGame.repaint();
		clueGame.createCardPanel();
		
		JOptionPane.showMessageDialog(frame, "You are Master Chief, press Next Player to begin playing", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		
	}

}
