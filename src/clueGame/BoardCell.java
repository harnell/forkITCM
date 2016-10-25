package clueGame;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private Boolean isDoor = false;
	private DoorDirection direction = DoorDirection.NONE;
	public static char WalkwayChar;
	
	public BoardCell(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	public BoardCell(int row, int col, String initial){
		this.row = row;
		this.col = col;
		this.initial = initial.charAt(0);
		//Checks cell to see if it is a door and uses a switch statement to figure out the direction of the door
		if(initial.length() == 2){
			switch(initial.charAt(1)){
			case 'U':
				direction = DoorDirection.UP;
				isDoor = true;
				break;
			case 'R':
				direction = DoorDirection.RIGHT;
				isDoor = true;
				break;
			case 'D':
				direction = DoorDirection.DOWN;
				isDoor = true;
				break;
			case 'L':
				direction = DoorDirection.LEFT;
				isDoor = true;
				break;
			}
		}
	}

	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public boolean isWalkway() {
		return initial == WalkwayChar;
	}
	public boolean isRoom() {
		return !(initial == WalkwayChar);
	}
	public boolean isDoorway() {
		return isDoor;
	}

	public DoorDirection getDoorDirection(){
		return direction;
	}

	public char getInitial() {
		return initial;
	}

}
