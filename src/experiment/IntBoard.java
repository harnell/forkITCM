package experiment;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class IntBoard {
	private BoardCell [][] board;
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private static final int NUM_COLS = 4;
	private static final int NUM_ROWS = 4;



	public IntBoard() {
		super();
		board = new BoardCell[NUM_ROWS][NUM_COLS];
		for(int i = 0; i < NUM_ROWS; i++){
			for (int j = 0; j < NUM_COLS; j++) {
				board[i][j] = new BoardCell(i, j);
			}
		}
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
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
		adjMtx.put(cell, tempCells);
	}

	public void calcTargets(BoardCell cell, int pathLength) {
		visited.add(cell);
		findAllTargets(cell, pathLength);
	}
	
	private void findAllTargets(BoardCell cell, int pathLength) {
		Set<BoardCell> adjacents = getAdjList(cell);
		for (BoardCell adjCell : adjacents) {
			if(!visited.contains(adjCell)){
				visited.add(adjCell);
				if(pathLength == 1){
					targets.add(adjCell);
				} 
				else {
					findAllTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}
	public Set<BoardCell> getAdjList(BoardCell cell) {
		if(!adjMtx.containsKey(cell)){
			calcAdjacencies(cell);
		}
		return adjMtx.get(cell);
	}

	public BoardCell getCell(int i, int j) {
		return board[i][j];
	}
}
