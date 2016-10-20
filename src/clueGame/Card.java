package clueGame;

public class Card {
	
	private String cardName;
	private CardType type;
	
	public boolean equals(Card card){
		if (this.cardName == card.cardName){
			if (this.type == card.type){
				return true;
			}
		}
		return false;
	}
	
}
