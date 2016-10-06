package ClueGame;

public class BoardCell {
	private int row;
	private int col;
	private String initial;

	public BoardCell(int row, int col){
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public boolean isWalkway() {
		return initial.equals("A");
	}
	public boolean isRoom() {
		return (!initial.equals("A"));
	}
	public boolean isDoorway() {
		return (initial.length() != 1);
	}

	public Object getDoorDirection() {
		if(initial.length() == 2){
			switch(initial.charAt(1)){
			case 'U':
				return DoorDirection.UP;
			case 'R':
				return DoorDirection.RIGHT;
			case 'D':
				return DoorDirection.DOWN;
			case 'L':
				return DoorDirection.RIGHT;
			}
		}
		return DoorDirection.NONE;
	}

	public void setInitial(String i) {
		initial = i;
	}

	public String getInitial() {
		return initial;
	}

}
