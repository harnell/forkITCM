package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private Set<Character> seenRooms = new HashSet<Character>(0);
	private char lastRoom = 'X';

	private Solution accusation;


	private Board board = Board.getInstance();
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		
	}
	
	@Override
	public BoardCell pickLocation (int rollLength) {
		board.calcTargets(this.row, this.column, rollLength);
		Set<BoardCell> targets = board.getTargets();
		Set<BoardCell> priorityTargets = new HashSet<BoardCell>();
		for (BoardCell b : targets){
			if (!(b.getDoorDirection() == DoorDirection.NONE) && !(seenRooms.contains(b.getInitial())) && b.getInitial() != lastRoom && b.getInitial() != 'F'){
				priorityTargets.add(b);
			}
		}
		if(priorityTargets.isEmpty()){
			BoardCell ret = randomFromSet(targets);
			if (ret.getDoorDirection() != DoorDirection.NONE){
				lastRoom = ret.getInitial();
			}
			return ret;
		}
		else {
			BoardCell ret = randomFromSet(priorityTargets);
			lastRoom = ret.getInitial();
			return ret;
		}
	}
	
	@Override
	public boolean makeAccusation(){
		// if in room, make accusation else make accusaton null
		generateAccusation();
		System.out.println(this.accusation.toString());
		boolean result = board.checkAccusation(accusation);
		return result;
	}
	
	public void generateAccusation() {
		// logic for accustation
		String room;
		Set<Card> roomToAccuse = new HashSet<Card>();
		String weapon;
		Set<Card> weaponToAccuse = new HashSet<Card>();
		String person;
		Set<Card> personToAccuse = new HashSet<Card>();
		for (Card c: board.getRoomDeck()){
			if (!seenCards.contains(c)){
				roomToAccuse.add(c);
			}
		}
		for (Card c: board.getWeaponsDeck()){
			if (!seenCards.contains(c)){
				weaponToAccuse.add(c);
			}
		}
		for (Card c: board.getPersonDeck()){
			if (!seenCards.contains(c)){
				personToAccuse.add(c);
			}
		}
		room = randomFromSet(roomToAccuse).getCardName();
		weapon = randomFromSet(weaponToAccuse).getCardName();
		person = randomFromSet(personToAccuse).getCardName();
		
		Solution acc = new Solution(person, room, weapon);		
		this.accusation = acc;
	}

	public void createSuggestion(){
		
	}
	
	private <T> T randomFromSet(Set<T> needRand){
		int size = needRand.size();
		int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
		int i = 0;
		for(T b : needRand)
		{
		    if (i == item)
		        return b;
		    i = i + 1;
		}
		return null;
	}
	@Override
	public void setLastRoom(char lastRoom) {
		this.lastRoom = lastRoom;
	}
}
