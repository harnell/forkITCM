package ClueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private static final int MAX_BOARD_SIZE = 50;
	private static final int NUM_ROWS = 22;
	private static final int NUM_COLS = 21;
	private int numRows = 0;
	private int numColumns = 0;
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
		try{
			loadRoomConfig();
			loadBoardConfig();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
			
	}
	public void loadRoomConfig() throws FileNotFoundException{	
		
		FileReader reader = new FileReader(roomConfigFile);
		
		rooms = new HashMap<Character, String>();
		
		Scanner in = new Scanner(reader);
		while(in.hasNextLine()){
			String line = in.nextLine();
			String info[] = line.split(",");
			rooms.put(info[0].charAt(0), info[1]);
		}
		in.close();
	}
	public void loadBoardConfig() throws FileNotFoundException {
	
		FileReader reader = new FileReader(boardConfigFile);
		
		board = new BoardCell[NUM_ROWS][NUM_COLS];
		
		Scanner in = new Scanner(reader);
		int lineNumber = 0;
		while(in.hasNextLine()){
			String line = in.nextLine();
			String info[] = line.split(",");
			numColumns = info.length;
			for (int i = 0; i < info.length; i++) {
				board[lineNumber][i] = new BoardCell(lineNumber, i);
				board[lineNumber][i].setInitial(info[i]);
			}
			lineNumber++;
		}
		numRows = lineNumber;
		in.close();
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
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		if(!adjMatrix.containsKey(cell)){
			calcAdjacencies(cell);
		}
		return adjMatrix.get(cell);
	}
	
	public void setConfigFiles(String string, String string2) {
		roomConfigFile = string2;
		boardConfigFile = string;		
	}
	public Map<Character, String> getLegend() {
		return rooms;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}

}
