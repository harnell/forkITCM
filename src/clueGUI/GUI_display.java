package clueGUI;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GUI_display extends JPanel{

	public GUI_display (){
		setLayout(new GridLayout(2,3));
		//First panel
		JPanel panel1 = new JPanel();
		JLabel whoseTurn = new JLabel("Whose turn?");
		panel1.add(whoseTurn);
		JTextField playerTurn = new JTextField(20);
		panel1.add(playerTurn);
		add(panel1);
		
		//Second panel
		JButton nextPlayer = new JButton("Next Player");
		add(nextPlayer);
		
		//Third panel
		JButton makeAccusation = new JButton("Make an Accusation");
		add(makeAccusation);
		
		//Fourth panel
		JPanel panel4 = new JPanel();
		panel4.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		JLabel dieRoll = new JLabel("Roll");
		panel4.add(dieRoll);
		JTextField rollNumber = new JTextField(4);
		panel4.add(rollNumber);
		add(panel4);
		
		//Fifth panel
		JPanel panel5 = new JPanel();
		panel5.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		JLabel playerGuess = new JLabel("Guess");
		panel5.add(playerGuess);
		JTextField guessInput = new JTextField(20);
		panel5.add(guessInput);
		add(panel5);
		
		//Sixth panel
		JPanel panel6 = new JPanel();
		panel6.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		JLabel guessResult = new JLabel("Response");
		panel6.add(guessResult);
		JTextField resultOutput = new JTextField(20);
		panel6.add(resultOutput);
		add(panel6);	
	}

}
