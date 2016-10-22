package clueGame;

public class Card {
	
	private String cardName;
	private CardType type;
	
	
	
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
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



	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", type=" + type + "]";
	}
	
}
