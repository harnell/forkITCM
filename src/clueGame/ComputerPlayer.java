package clueGame;

import java.awt.Color;
import java.util.ArrayList;
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

		//Rooms
		ArrayList<Card> possibleRooms = new ArrayList<Card>();
		for (Card t : board.getRoomDeck()){
			possibleRooms.add(t);
		}
		for (Card possibleRoom: board.getRoomDeck()){
			for (Card seen : seenCards){
				if (seen.getCardName().equals(possibleRoom.getCardName())){
					possibleRooms.remove(possibleRoom);
				}
			}
		}
		roomToAccuse.addAll(possibleRooms);
		
		//Weapons
		ArrayList<Card> possibleWeapons = new ArrayList<Card>();
		for (Card t : board.getWeaponsDeck()){
			possibleWeapons.add(t);
		}
		for (Card possibleWeapon: board.getWeaponsDeck()){
			for (Card seen : seenCards){
				if (seen.getCardName().equals(possibleWeapon.getCardName())){
					possibleWeapons.remove(possibleWeapon);
				}
			}
		}
		weaponToAccuse.addAll(possibleWeapons);

		//Persons
		ArrayList<Card> possiblePersons = new ArrayList<Card>();
		for (Card t : board.getPersonDeck()){
			possiblePersons.add(t);
		}
		for (Card possiblePerson: board.getPersonDeck()){
			for (Card seen : seenCards){
				if (seen.getCardName().equals(possiblePerson.getCardName())){
					possiblePersons.remove(possiblePerson);
				}
			}
		}
		personToAccuse.addAll(possiblePersons);

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
