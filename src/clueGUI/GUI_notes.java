package clueGUI;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Card;

public class GUI_notes extends JDialog{
	
	private JPanel buttons = new JPanel();
	private JPanel personButtons = new JPanel();
	private JPanel roomButtons = new JPanel();
	private JPanel weaponButtons = new JPanel();
	
	private JPanel boxes = new JPanel();
	private JComboBox<String> personBox = new JComboBox<String>();
	private JComboBox<String> roomBox = new JComboBox<String>();
	private JComboBox<String> weaponBox = new JComboBox<String>();
	
	private Board board = Board.getInstance();
	
	public void CheckList() {
		buttons.setLayout(new GridLayout(0,1));
		
		for (Card c: board.getPersonDeck()) {
			personButtons.add(new JRadioButton(c.getCardName()));
		}
		personButtons.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		buttons.add(personButtons);
		
		for (Card c: board.getRoomDeck()) {
			roomButtons.add(new JRadioButton(c.getCardName()));
		}
		roomButtons.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		buttons.add(roomButtons);
		
		for (Card c: board.getWeaponsDeck()) {
			weaponButtons.add(new JRadioButton(c.getCardName()));
		}
		weaponButtons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		buttons.add(weaponButtons);
		
		add(buttons);
	}
	
	public void DropList() {
		boxes.setLayout(new GridLayout(0,1));
		
		for (Card c: board.getPersonDeck()) {
			personBox.addItem(c.getCardName());
		}
		personBox.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		boxes.add(personBox);
		
		for (Card c: board.getRoomDeck()) {
			roomBox.addItem(c.getCardName());
		}
		roomBox.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		boxes.add(roomBox);
		
		for (Card c: board.getWeaponsDeck()) {
			weaponBox.addItem(c.getCardName());
		}
		weaponBox.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		boxes.add(weaponBox);
		
		add(boxes);
	}
	
	public GUI_notes() {
		setSize(300,800);
		setLayout(new GridLayout(1,2));
		setTitle("Detective Notes");
		CheckList();
		DropList();
	}
		

}
