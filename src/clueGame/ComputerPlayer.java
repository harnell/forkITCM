package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private Set<String> roomsToVisit;
	private Board board = Board.getInstance();
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}
	
	@Override
	public BoardCell pickLocation (int rollLength) {
		board.calcTargets(this.row, this.column, rollLength);
		Set<BoardCell> targets = board.getTargets();
		Set<BoardCell> priorityTargets = new HashSet<BoardCell>();
		
		BoardCell thisCell = board.getCellAt(this.row, this.column);		
		for (BoardCell b : targets){
			if (roomsToVisit.contains(board.getLegend().get(b.getInitial()))){
				priorityTargets.add(b);
			}
		}
		
		return null;
	}
	
	public void makeAccusation(){
		
	}
	
	public void createSuggestion(){
		
	}
	
}
