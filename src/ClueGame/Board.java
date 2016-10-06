package ClueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import experiment.BoardCell;

public class Board {
	private static final int MAX_BOARD_SIZE = 50;
	private static final int NUM_ROWS = 22;
	private static final int NUM_COLS = 21;
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String boardConfigFile;
	private String roomConfigFile;



	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {

	}
	public void loadRoomConfig() {

	}
	public void loadBoardConfig() {

	}
	public void calcAdjacencies(BoardCell cell) {
		int cellRow = cell.getRow();
		int cellCol = cell.getCol();
		Set<BoardCell> tempCells = new HashSet<BoardCell>();
		if(cellRow - 1 >= 0) {
			tempCells.add(board[cellRow - 1][cellCol]);
		}
		if(cellCol - 1 >= 0) {
			tempCells.add(board[cellRow][cellCol - 1]);
		}
		if(cellRow + 1 < NUM_ROWS) {
			tempCells.add(board[cellRow + 1][cellCol]);
		}
		if(cellCol + 1 < NUM_COLS) {
			tempCells.add(board[cellRow][cellCol + 1]);
		}
		adjMatrix.put(cell, tempCells);
	}
	public void calcTargets(BoardCell cell, int pathLength) {
		visited.add(cell);
		Set<BoardCell> adjacents = getAdjList(cell);
		for (BoardCell adjCell : adjacents) {
			if(!visited.contains(adjCell)){
				visited.add(adjCell);
				if(pathLength == 1){
					targets.add(adjCell);
				} 
				else {
					calcTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
	}
	public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		
	}
	public Map<Character, String> getLegend() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getNumRows() {
		// TODO Auto-generated method stub
		return (Integer) null;
	}
	public int getNumColumns() {
		// TODO Auto-generated method stub
		return (Integer) null;
	}
	public BoardCell getCellAt(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

}
