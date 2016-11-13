package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;

public class HumanPlayer extends Player {
	private Graphics g = board.getGraphics();

	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BoardCell pickLocation(HashSet<BoardCell> targets) {
		board.highlight = true;
		board.validate();
		board.repaint();
		return board.getCellAt(row, column);
	}

	@Override
	public void setLastRoom(char lastRoom) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean makeAccusation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Solution createSuggestion() {
		return null;
		// TODO Auto-generated method stub
		
	}
	
}
