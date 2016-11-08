package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import clueGUI.GUI_display;
import clueGUI.GUI_notes;

public class ClueGame extends JFrame{
	
	private static Board board = Board.getInstance();
	private static GUI_notes dnotes;
	
	public ClueGame() {
		setTitle("Clue");
		setSize(1000,1000);
		add(board, BorderLayout.CENTER);
		
		createMenu();
	}
	
	private JMenuItem createDnotes() {
		JMenuItem notes = new JMenuItem("Detective Notes");
		class DnotesListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (notes.isSelected()) {
					dnotes = new GUI_notes();
					dnotes.setVisible(true);
				}
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
				if (exit.isSelected()) {
					System.exit(0);
				}
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

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 300);
		GUI_display gui = new GUI_display();
		frame.add(gui);
		frame.setVisible(true);
		
		// Setting up game
		board.setConfigFiles("BoardLayout.csv", "Legend.txt", "person.txt", "weapons.txt");	
		board.initialize();
		
		ClueGame clueGame = new ClueGame();
		clueGame.setVisible(true);
		clueGame.repaint();
	}

}
