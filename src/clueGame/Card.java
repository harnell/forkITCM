package clueGame;

public class Card {
	
	private String cardName;
	private CardType type;
	private char initial;
	
	
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
		for (char c : Board.getInstance().getLegend().keySet()){
			if (Board.getInstance().getLegend().get(c).equals(cardName)){
				this.initial = c;
			}
		}
	}

	public boolean equals(Card card){
		if (this.cardName.equals(card.cardName) ){
			if (this.type == card.type){
				return true;
			}
		}
		return false;
	}

	public String getCardName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}

	public char getInitial() {
		return initial;
	}

	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", type=" + type + "]";
	}
	
}
