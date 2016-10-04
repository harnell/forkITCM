package ClueGame;

import java.util.Map;
import java.util.Set;

public class Board {
	private static final int MAX_BOARD_SIZE = 50;
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;



	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	public Board() {}
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
	public void calcAdjacencies() {

	}
	public void calcTargets(BoardCell cell, int pathLength) {

	}

}
