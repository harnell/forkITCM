package clueGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

//import clueGame.Board;
import clueGame.ClueGame;


@SuppressWarnings("serial")
public class GUI_display extends JPanel{
	
	private class NextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ClueGame.theInstance.nextPlayer();
		}
	}
	
	private class AccuseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ClueGame.theInstance.accusePlayer();
		}
	}

	//private Board board = Board.getInstance();
	
	private JPanel panel1 = new JPanel();
	private JTextField playerTurn = new JTextField(20);
	
	private JButton nextPlayer = new JButton("Next Player");
	private NextListener nextL = new NextListener();
	
	private JButton makeAccusation = new JButton("Make an Accusation");
	private AccuseListener accuseL = new AccuseListener();
	
	private JPanel panel4 = new JPanel();
	private JTextField rollNumber = new JTextField(4);
	
	private JPanel panel5 = new JPanel();
	private JTextField guessInput = new JTextField(25);
	
	private JPanel panel6 = new JPanel();
	private JTextField resultOutput = new JTextField(15);
	
	public GUI_display (){
		setLayout(new GridLayout(2,3));
		//First panel
		JLabel whoseTurn = new JLabel("Whose turn?");
		panel1.add(whoseTurn);
		panel1.add(playerTurn);
		add(panel1);
		
		playerTurn.setEditable(false);
		rollNumber.setEditable(false);
		guessInput.setEditable(false);
		resultOutput.setEditable(false);
		
		//Second panel
		nextPlayer.addActionListener(nextL);
		add(nextPlayer);
		
		//Third panel
		makeAccusation.addActionListener(accuseL);
		add(makeAccusation);
		
		//Fourth panel
		panel4.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		JLabel dieRoll = new JLabel("Roll");
		panel4.add(dieRoll);
		panel4.add(rollNumber);
		add(panel4);
		
		//Fifth panel
		panel5.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		JLabel playerGuess = new JLabel("Guess");
		panel5.add(playerGuess);
		panel5.add(guessInput);
		add(panel5);
		
		//Sixth panel
		panel6.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		JLabel guessResult = new JLabel("Response");
		panel6.add(guessResult);
		panel6.add(resultOutput);
		add(panel6);
	}
	
	public void setDice(int x) {
		rollNumber.setText(Integer.toString(x));
		ClueGame.theInstance.revalidate();
	}
	
	public void setWhoseTurn(String s) {
		playerTurn.setText(s);
		ClueGame.theInstance.revalidate();
	}

	public JTextField getGuessInput() {
		return guessInput;
	}

	public JTextField getResultOutput() {
		return resultOutput;
	}
	
	public void removeButtons() {
		nextPlayer.removeActionListener(nextL);
		makeAccusation.removeActionListener(accuseL);
		
		class DoneListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(ClueGame.theInstance, "The game is over!", "Done!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		nextPlayer.addActionListener(new DoneListener());
		makeAccusation.addActionListener(new DoneListener());
	}

}
