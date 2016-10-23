package clueGame;

import java.awt.Color;
import java.util.Set;

public class Player implements Comparable<Object>{
	
	 private String playerName;
	 private int row;
	 private int column;
	 private Color color;
	 private Set<Card> myCards;
	 private Set<Card> seenCards;
	 
	 public Set<Card> getMyCards() {
		return myCards;
	}



	public void setMyCards(Set<Card> myCards) {
		this.myCards = myCards;
	}



	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}

	 

	public String getPlayerName() {
		return playerName;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public Color getColor() {
		return color;
	}

	public Card disproveSuggestion(Solution suggestion) {
		 return null;
	 }


	public int compareTo(Object p) {
		Player p1 = (Player)p;
		return this.playerName.compareTo(p1.getPlayerName());
	}
}
