package ClueGame;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private Boolean isDoor = false;
	private DoorDirection direction;
	
	public BoardCell(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	public BoardCell(int row, int col, String initial){
		this.row = row;
		this.col = col;
		this.initial = initial.charAt(0);
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
		return initial == 'A';
	}
	public boolean isRoom() {
		return !(initial == 'A');
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
