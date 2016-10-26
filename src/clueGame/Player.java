package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player implements Comparable<Object>{
	
	 private String playerName;
	 protected int row;
	 protected int column;
	 private Color color;
	 protected Set<Card> myCards;
	 protected Set<Card> seenCards;
	 




	public Set<Card> getMyCards() {
		return myCards;
	}



	public void setMyCards(Set<Card> myCards) {
		this.myCards = myCards;
		seenCards = myCards;
	}



	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		seenCards = new HashSet<Card>();
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

	public void addCardToSeenCards(Card card) {
		this.seenCards.add(card);
	}
	public void addSetToSeenCards(Set<Card> seenCards) {
		this.seenCards.addAll(seenCards);
	}
	public void setSeenCards(Set<Card> seenCards) {
		this.seenCards = seenCards;
	}


	public abstract boolean makeAccusation();
	public abstract BoardCell pickLocation(int rollLength);
	public abstract void setLastRoom(char lastRoom);
	public abstract Card createSuggestion();
}
