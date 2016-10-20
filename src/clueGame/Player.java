package clueGame;

import java.awt.Color;
import java.util.Set;

public class Player {
	
	 private String playerName;
	 private int row;
	 private int column;
	 private Color color;
	 private Set<Card> myCards;
	 private Set<Card> seenCards;
	 
	 
	 
	 public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}



	public Card disproveSuggestion(Solution suggestion) {
		 return null;
	 }
}
