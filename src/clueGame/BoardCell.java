package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {
	private int row;
	private int col;
	private int x;
	private int y;
	private int rectWidth = 30;
	private int rectHeight = 30;
	private int doorThickness = 3;
	private int doorX;
	private int doorY;
	private int doorWidth = 30;
	private int doorHeight = 30;
	private Color walkFillColor = Color.YELLOW;
	private Color walkBorderColor = Color.BLACK;
	private Color roomColor = Color.LIGHT_GRAY;
	private Color doorColor = Color.BLUE;
	private char initial;
	private Boolean isDoor = false;
	private Boolean drawRoomName = false;
	private DoorDirection direction = DoorDirection.NONE;
	public static char WalkwayChar;
	
	public BoardCell(int row, int col){
		this.row = row;
		this.col = col;
		this.x = col*30;
		this.y = row*30;
	}
	
	public BoardCell(int row, int col, String initial){
		this.row = row;
		this.col = col;
		this.x = col*30;
		this.y = row*30;
		this.initial = initial.charAt(0);
		//Checks cell to see if it is a door and uses a switch statement to figure out the direction of the door
		if(initial.length() == 2){
			doorX = this.x;
			doorY = this.y;
			switch(initial.charAt(1)){
			case 'U':
				direction = DoorDirection.UP;
				isDoor = true;
				doorHeight = doorThickness;
				break;
			case 'R':
				direction = DoorDirection.RIGHT;
				isDoor = true;
				doorWidth = doorThickness;
				doorX += 30 - doorThickness;
				break;
			case 'D':
				direction = DoorDirection.DOWN;
				isDoor = true;
				doorHeight = doorThickness;
				doorY += 30 - doorThickness;
				break;
			case 'L':
				direction = DoorDirection.LEFT;
				isDoor = true;
				doorWidth = doorThickness;
				break;
			case 'G':
				drawRoomName = true;
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
	
	public void draw(Graphics g){
		if (this.isRoom()){
			drawRect(g, x, y, rectWidth, rectHeight, true, roomColor);
			if (this.isDoorway())
				drawRect(g, doorX, doorY, doorWidth, doorHeight, true, doorColor);
			if (this.drawRoomName){
				g.setColor(doorColor);
				g.drawString(Board.getInstance().getLegend().get(this.initial), x, y);
			}
		}
		else{
			drawRect(g, x, y, rectWidth, rectHeight, true, walkFillColor);
			drawRect(g, x, y, rectWidth, rectHeight, false, walkBorderColor);
		}
	}
	
	private void drawRect(Graphics g, int xCoord, int yCoord, int width, int height, boolean isFill, Color color){
		g.setColor(color);
		if (isFill)
			g.fillRect(xCoord, yCoord, width, height);
		else
			g.drawRect(xCoord, yCoord, width, height);
	}

}
