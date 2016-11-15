package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private Set<Character> seenRooms = new HashSet<Character>(0);
	private char lastRoom = 'X';

	private Board board = Board.getInstance();
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}
	
	public BoardCell pickLocation (HashSet<BoardCell> targets) {
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
		// if in room, make accusation else make accusation null
		String room = randomFromSet(removeSeen(board.getRoomDeck())).getCardName();
		String weapon = randomFromSet(removeSeen(board.getWeaponsDeck())).getCardName();
		String person = randomFromSet(removeSeen(board.getPersonDeck())).getCardName();
		Solution acc = new Solution(person, room, weapon);
		this.accusation = acc;
		boolean result = board.checkAccusation(accusation);
		return result;
	}

	@Override
	public Solution createSuggestion(){
		String roomImIn = board.getLegend().get(board.getCellAt(this.row, this.column).getInitial());
		String weaponIChoose = randomFromSet(removeSeen(board.getWeaponsDeck())).getCardName();
		String personIChoose = randomFromSet(removeSeen(board.getPersonDeck())).getCardName();
		this.suggestion = new Solution(personIChoose, roomImIn, weaponIChoose);
		return this.suggestion;
	}
	
	private Collection<Card> removeSeen(ArrayList<Card> totalSet){
		Collection<Card> possibilities = new ArrayList<Card>();
		for (Card t : totalSet){
			possibilities.add(t);
		}
		for (Card possible: totalSet){
			for (Card seen : seenCards){
				if (seen.equals(possible)){
					possibilities.remove(possible);
				}
			}
		}
		return possibilities;
	}
	
	private <T> T randomFromSet(Collection<T> needRand){
		int size = needRand.size();
		int item = new Random().nextInt(size);
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
	public void makeMove() {
		if (seenCards.size() == 18){
			makeAccusation();
		}
		BoardCell bc = pickLocation((HashSet<BoardCell>) board.getTargets());
		row = bc.getRow();
		column = bc.getCol();
		board.validate();
		board.repaint();
	}

	public void setLastRoom(char lastRoom) {
		this.lastRoom = lastRoom;
	}
	
	public Solution getSuggestion() {
		return suggestion;
	}
	
	public void addToSeenRooms(char room) {
		this.seenRooms.add(room);
	}
}