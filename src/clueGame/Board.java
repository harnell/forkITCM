package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.InflaterOutputStream;

public class Board {
	private static final int MAX_BOARD_SIZE = 50;
	private static final String WALKWAY_NAME = "Walkway";
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
		}catch(FileNotFoundException | BadConfigFormatException e){
			e.printStackTrace();
		}

	}
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{	

		FileReader reader = new FileReader(roomConfigFile);

		rooms = new HashMap<Character, String>();

		Scanner in = new Scanner(reader);
		while(in.hasNextLine()){
			String line = in.nextLine();
			String info[] = line.split(", ");
			if(info[1].equals(WALKWAY_NAME)) {
				BoardCell.WalkwayChar = info[0].charAt(0);
			}
			rooms.put(info[0].charAt(0), info[1]);
			//Check if config file has proper formatting
			if(info.length != 3 || (!(info[2].contains("Card")) && !(info[2].contains("Other")))) { 
				throw new BadConfigFormatException("Config file not in proper format");
			}
		}
		in.close();
	}
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {

		FileReader reader = new FileReader(boardConfigFile);

		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];

		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();

		Scanner in = new Scanner(reader);
		int lineNumber = 0;
		while(in.hasNextLine()){
			String line = in.nextLine();
			String info[] = line.split(",");
			numColumns = info.length;
			for (int i = 0; i < info.length; i++) {
				board[lineNumber][i] = new BoardCell(lineNumber, i, info[i]);
				//Throw exception if room is not contained in legend
				if(!rooms.containsKey(info[i].charAt(0))){ 
					throw new BadConfigFormatException("Room is not in legend");
				}
			}
			lineNumber++;
		}
		numRows = lineNumber;
		for (int i = 0; i < numRows; i++) {
			int j = 0;
			while(board[i][j] != null){
				j++;
			}
			//Throw exception if all of the columns are not the same length
			if(j != numColumns){
				throw new BadConfigFormatException("Number of columns are not consistent for all rows.");
			}
		}
		in.close();
	}

	public void calcAdjacencies(BoardCell cell) {
		int cellRow = cell.getRow();
		int cellCol = cell.getCol();
		Set<BoardCell> cellsToAdd = new HashSet<BoardCell>();
		if(cell.isRoom() && !cell.isDoorway()) {
			adjMatrix.put(cell, cellsToAdd);
			return;
		}
		if(cell.isDoorway()) {
			switch (cell.getDoorDirection()) {
			case UP:
				cellsToAdd.add(board[cellRow - 1][cellCol]);
				break;
			case DOWN:
				cellsToAdd.add(board[cellRow + 1][cellCol]);
				break;
			case LEFT:
				cellsToAdd.add(board[cellRow][cellCol - 1]);
				break;
			case RIGHT:
				cellsToAdd.add(board[cellRow][cellCol + 1]);
				break;
			default:
				break;
			}
			adjMatrix.put(cell, cellsToAdd);
			return;
		}
		Set<BoardCell> cellsToDelete = new HashSet<BoardCell>();
		if(cellRow - 1 >= 0) {
			cellsToAdd.add(board[cellRow - 1][cellCol]);
			if(!isDoorRightWay(board, cellRow - 1, cellCol, DoorDirection.DOWN)) {
				cellsToDelete.add(board[cellRow - 1][cellCol]);
			}
		}
		if(cellCol - 1 >= 0) {
			cellsToAdd.add(board[cellRow][cellCol - 1]);
			if(!isDoorRightWay(board, cellRow, cellCol - 1, DoorDirection.RIGHT)) {
				cellsToDelete.add(board[cellRow][cellCol - 1]);
			}
		}
		if(cellRow + 1 < numRows) {
			cellsToAdd.add(board[cellRow + 1][cellCol]);
			if(!isDoorRightWay(board, cellRow + 1, cellCol, DoorDirection.UP)) {
				cellsToDelete.add(board[cellRow + 1][cellCol]);
			}
		}
		if(cellCol + 1 < numColumns) {
			cellsToAdd.add(board[cellRow][cellCol + 1]);
			if(!isDoorRightWay(board, cellRow, cellCol + 1, DoorDirection.LEFT)) {
				cellsToDelete.add(board[cellRow][cellCol + 1]);
			}
		}
		for(BoardCell o: cellsToAdd) {
			if(!o.isDoorway() && !o.isWalkway()) {
				cellsToDelete.add(o);
			}
		}
		cellsToAdd.removeAll(cellsToDelete);
		adjMatrix.put(cell, cellsToAdd);
	}
	public void calcTargetsRecursive(BoardCell cell, int pathLength) {
		visited.add(cell);
		Set<BoardCell> adjacents = getAdjList(cell);
		for (BoardCell adjCell : adjacents) {
			if(!visited.contains(adjCell)){
				visited.add(adjCell);
				if(pathLength == 1 || adjCell.isDoorway()){
					targets.add(adjCell);
				} 
				else {
					calcTargetsRecursive(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
	}

	public void calcTargets(int row, int col, int pathLength) {
		BoardCell cell = board[row][col];
		targets.clear();
		calcTargetsRecursive(cell, pathLength);
	}


	public Set<BoardCell> getAdjList(BoardCell cell) {
		if(!adjMatrix.containsKey(cell)){
			calcAdjacencies(cell);
		}
		return adjMatrix.get(cell);
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		BoardCell cell = board[row][col];
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
	public Set<BoardCell> getTargets() {
		return targets;
	}
	private static boolean isDoorRightWay(BoardCell[][] board, int cellRow, int cellCol, DoorDirection whichWay) {
		if(board[cellRow][cellCol].isDoorway()) {
			if(board[cellRow][cellCol].getDoorDirection() != whichWay) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return true;
		}
	}

}
