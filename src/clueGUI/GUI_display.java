package clueGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.ClueGame;


@SuppressWarnings("serial")
public class GUI_display extends JPanel{

	private Board board = Board.getInstance();
	
	private JPanel panel1 = new JPanel();
	
	private JButton nextPlayer = new JButton("Next Player");
	private JButton makeAccusation = new JButton("Make an Accusation");
	
	private JPanel panel4 = new JPanel();
	private JTextField rollNumber = new JTextField(4);
	
	private JPanel panel5 = new JPanel();
	private JTextField guessInput = new JTextField(20);
	
	private JPanel panel6 = new JPanel();
	private JTextField resultOutput = new JTextField(20);
	
	public GUI_display (){
		setLayout(new GridLayout(2,3));
		//First panel
		JLabel whoseTurn = new JLabel("Whose turn?");
		panel1.add(whoseTurn);
		JTextField playerTurn = new JTextField(20);
		panel1.add(playerTurn);
		add(panel1);
		
		//Second panel
		class NextListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClueGame.theInstance.nextPlayer();
			}
		}
		nextPlayer.addActionListener(new NextListener());
		add(nextPlayer);
		
		//Third panel
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
		revalidate();
	}

}
