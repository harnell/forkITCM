package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private Set<Character> seenRooms = new HashSet<Character>(0);
	private char lastRoom = 'X';
	private int count = 0;


	private Board board = Board.getInstance();
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		
	}
	
	@Override
	public BoardCell pickLocation (int rollLength) {
		board.calcTargets(this.row, this.column, rollLength);
		Set<BoardCell> targets = board.getTargets();
		Set<BoardCell> priorityTargets = new HashSet<BoardCell>();
//		for (BoardCell b : targets){
//			System.out.println(b.getInitial() + " " + b.getRow() + " " + b.getCol());
//		}
//		System.out.println(" ");
		for (BoardCell b : targets){
			//System.out.println(lastRoom + ", b.init = " + b.getInitial());
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
	
	public void makeAccusation(){
		
	}
	
	public void createSuggestion(){
		
	}
	
	private BoardCell randomFromSet(Set<BoardCell> needRand){
		for (BoardCell t : needRand){
        	//System.out.println(t.getRow() + " " + t.getCol());
        }
		int size = needRand.size();
		int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
		int i = 0;
		for(BoardCell b : needRand)
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
