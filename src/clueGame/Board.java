package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private static final int MAX_BOARD_SIZE = 50;
	private static final String WALKWAY_NAME = "Walkway";
	//Size of board
	private int numRows = 0;
	private int numColumns = 0;
	
	//Board
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	
	//Creating adjacent matrix
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	//Config file names
	private String boardConfigFile;
	private String roomConfigFile;
	private String personConfigFile = "person.txt";
	private String weaponsConfigFile = "weapons.txt";
	
	//Solution
	private Solution theAnswer;
	


	//Decks of cards
	private ArrayList<Card> deck = new ArrayList<Card>(21);
	private ArrayList<Card> personDeck = new ArrayList<Card>();
	private ArrayList<Card> weaponsDeck = new ArrayList<Card>();
	private ArrayList<Card> roomDeck = new ArrayList<Card>();
	
	//Players
	private ArrayList<Player> players;								
	



	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is static to ensure only one can be created
	private Board() {}
	
	public void initialize() {
		try{
			loadRoomConfig();
			loadBoardConfig();
			loadWeaponsConfig();
			loadPersonConfig();
			makeSolution();
		}catch(FileNotFoundException | BadConfigFormatException e){
			e.printStackTrace();
		}

	}
	/*
	 * Reads in data from roomconfig file
	 */
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{	

		FileReader reader = new FileReader(roomConfigFile);

		rooms = new HashMap<Character, String>();

		Scanner in = new Scanner(reader);
		//Breaks up each line by commas
		while(in.hasNextLine()){
			String line = in.nextLine();
			String info[] = line.split(", ");
			if(info[1].equals(WALKWAY_NAME)) {
				BoardCell.WalkwayChar = info[0].charAt(0);
			}
			rooms.put(info[0].charAt(0), info[1]);
			if (info[2].equals("Card")){
				roomDeck.add(new Card(info[1], CardType.ROOM));
				deck.add(new Card(info[1], CardType.ROOM));
			}
			//Check if config file has proper formatting
			if(info.length != 3 || (!(info[2].contains("Card")) && !(info[2].contains("Other")))) { 
				in.close();
				throw new BadConfigFormatException("Config file not in proper format");
			}
		}
		in.close();
	}
	
	/*
	 * Initializes class level variables and reads in data from board config file
	 */
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {

		FileReader reader = new FileReader(boardConfigFile);

		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];

		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();

		//Makes scanner to read text from file
		Scanner in = new Scanner(reader);
		int lineNumber = 0;
		while(in.hasNextLine()){
			//Takes every line and breaks them up based on commas
			String line = in.nextLine();
			String info[] = line.split(",");
			numColumns = info.length;
			for (int i = 0; i < info.length; i++) {
				board[lineNumber][i] = new BoardCell(lineNumber, i, info[i]);
				//Throw exception if room is not contained in legend
				if(!rooms.containsKey(info[i].charAt(0))){ 
					in.close();
					throw new BadConfigFormatException("Room is not in legend");
				}
			}
			lineNumber++;
		}
		numRows = lineNumber;
		//Makes sure that that the columns are all the same length
		for (int i = 0; i < numRows; i++) {
			int j = 0;
			while(board[i][j] != null){
				j++;
			}
			//Throw exception if all of the columns are not the same length
			if(j != numColumns){
				in.close();
				throw new BadConfigFormatException("Number of columns are not consistent for all rows.");
			}
		}
		in.close();
	}
	
	/*
	 * Loads in and creates the weapon, player, and room cards
	 */
	public void loadPersonConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader personReader = new FileReader(personConfigFile);

		Scanner personIn = new Scanner(personReader);
		players = new ArrayList<Player>();
		
		//This will import all of the person cards
		for (int i = 0; i < 6; i++){
			String line = personIn.nextLine();
			String info[] = line.split(", ");
			personDeck.add(new Card(info[1], CardType.PERSON));
			deck.add(new Card(info[1], CardType.PERSON));
			int playerRow = Integer.parseInt(info[2]);
			int playerColumn = Integer.parseInt(info[3]);
			Color playerColor = convertColor(info[4]);
			if (info[1].equals("Master Chief")){
				Player temp = new HumanPlayer(info[1], playerRow, playerColumn, playerColor);
				players.add(temp);
			}
			else {
				Player temp = new ComputerPlayer(info[1], playerRow, playerColumn, playerColor);
				players.add(temp);
			}
		}
		personIn.close();
	}
	
	/*
	 * Loads in and creates the weapon cards
	 */
	public void loadWeaponsConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader weaponsReader = new FileReader(weaponsConfigFile);

		Scanner weaponsIn = new Scanner(weaponsReader);
		
		//This will import all of the weapons cards
		for (int i = 0; i < 6; i++){
			String line = weaponsIn.nextLine();
			String info[] = line.split(", ");
			if (!(info[0].equals("WEAPON"))){
				weaponsIn.close();
				throw new BadConfigFormatException();
			}
			else{
				weaponsDeck.add(new Card(info[1], CardType.WEAPON));
				deck.add(new Card(info[1], CardType.WEAPON));
			}
		}
		weaponsIn.close();
	}
	
	/*
	 * Calulates the adjacencies for a cell that is passed in
	 */
	public void calcAdjacencies(BoardCell cell) {
		int cellRow = cell.getRow();
		int cellCol = cell.getCol();
		Set<BoardCell> cellsToAdd = new HashSet<BoardCell>();
		if(cell.isRoom() && !cell.isDoorway()) {
			adjMatrix.put(cell, cellsToAdd);
			return;
		}
		//Checks if player starts in doorway and adds exit as only adjacency.
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
		//Makes set of cells that are invalid as move spaces to later be removed
		Set<BoardCell> cellsToDelete = new HashSet<BoardCell>();
		//Following if statments check door direction and only adds them when in correct cell to enter
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
	
	/*
	 * Calculates the targets that the player can move to 
	 */
	public void calcTargetsRecursive(BoardCell cell, int pathLength) {
		visited.add(cell);
		Set<BoardCell> adjacents = getAdjList(cell);
		for (BoardCell adjCell : adjacents) {
			if(!visited.contains(adjCell)){
				visited.add(adjCell);
				//Adds adjacent cell is no more moves or there is a doorway
				if(pathLength == 1 || adjCell.isDoorway()){
					targets.add(adjCell);
				} 
				else {
					//Calls the function again if the path length is not yet 1
					calcTargetsRecursive(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
	}

	/*
	 * Sets up for recursive method to be called to calculate the targets
	 */
	public void calcTargets(int row, int col, int pathLength) {
		BoardCell cell = board[row][col];
		//clears out targets list for new calculation
		targets.clear();
		calcTargetsRecursive(cell, pathLength);
	}
	
	/*
	 * Checks if the doorway is in the correct direction for the player to enter it based on the cell location
	 */
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
	
	/*
	 * Converts colors when inputting person.txt items
	 */
	public Color convertColor(String strColor) {
	    Color color; 
	    try {     
	        // We can use reflection to convert the string to a color
	        Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
	        color = (Color)field.get(null); 
	    } catch (Exception e) {  
	        color = null; // Not defined  
	    }
	    return color;
	}
	
	/*
	 * Creates the solution to the game
	 */
	public void makeSolution(){
		int randroom = (int) (Math.random()*9);
		int randweapon = (int) (Math.random()*6 + 8);
		int randperson = (int) (Math.random()*6 + 13);
		String room = deck.remove(randroom).getCardName();
		String weapon = deck.remove(randweapon).getCardName();
		String person = deck.remove(randperson).getCardName();
		theAnswer = new Solution(person, room, weapon);
	}
	
	/*
	 * Deals the deck<> to players
	 */
	public void dealCards(){
		ArrayList<Card> tempDeck = new ArrayList<Card>(18);
		for (Card c: deck){
			tempDeck.add(c);
		}
		for (Player p: players){
			Set<Card> cards = new HashSet<Card>();
			for (int i = 0; i < 3; i++){
				int rand = (int) (Math.random()*tempDeck.size());
				cards.add(tempDeck.remove(rand));
			}
			p.setMyCards(cards);
		}
	}
	
	public boolean checkAccusation(Solution accusation){
		return theAnswer.equals(accusation);
	}
	
	public Card handleSuggestion(Solution suggestion, ArrayList<Player> playas, int currentPlayerIndex){
		//failsafe if passed in empty players arraylist, meant for testing purposes
		if (playas.isEmpty()){
			playas = this.players;
		}
		
		//used to ensure that for loop does not start at playas.size(), which is out of bounds
		int startPlayerIndex = (currentPlayerIndex+1)%playas.size();
		for (int i = startPlayerIndex; i != currentPlayerIndex;){			//this for loop is used to simulate a queue
			Card possibleDisproved = playas.get(i).disproveSuggestion(suggestion);
			if (possibleDisproved != null){
				return possibleDisproved;
			}
			i++;
			i = i % playas.size();
		}
		return null;
	}

	/* Getters
	 * and
	 * Setters
	 */
	
	/*
	 * this method returns the only Board
	 */
	public static Board getInstance() {
		return theInstance;
	}
	
	/*
	 * Returns adjacencey list
	 */
	public Set<BoardCell> getAdjList(BoardCell cell) {
		//calculate adjacencies if not yet calculated
		if(!adjMatrix.containsKey(cell)){
			calcAdjacencies(cell);
		}
		return adjMatrix.get(cell);
	}

	/*
	 * Returns adjacencey list
	 */
	public Set<BoardCell> getAdjList(int row, int col) {
		BoardCell cell = board[row][col];
		//calculate adjacencies if not yet calculated
		if(!adjMatrix.containsKey(cell)){
			calcAdjacencies(cell);
		}
		return adjMatrix.get(cell);
	}

	/*
	 * Sets file names for for the configuration files
	 */
	public void setConfigFiles(String string, String string2) {
		roomConfigFile = string2;
		boardConfigFile = string;		
	}
	
	/*
	 * Returns the legend read in from config file
	 */
	public Map<Character, String> getLegend() {
		return rooms;
	}
	
	/*
	 * Returns the number of rows on the board
	 */
	public int getNumRows() {
		return numRows;
	}
	/*
	 * returns the number of columns on the board
	 */
	public int getNumColumns() {
		return numColumns;
	}
	
	/*
	 * Returns the cell at row, column location
	 */
	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}
	
	/*
	 * Returns the targets the the player can move to
	 */
	public Set<BoardCell> getTargets() {
		return targets;
	}

	public ArrayList<Card> getPersonDeck() {
		return personDeck;
	}
	
	public ArrayList<Card> getWeaponsDeck() {
		return weaponsDeck;
	}
	
	public ArrayList<Card> getRoomDeck() {
		return roomDeck;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
	public Solution getTheAnswer() {
		return theAnswer;
	}

	
	
	
	
}
