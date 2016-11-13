package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;

public class HumanPlayer extends Player {
	private Graphics g = board.getGraphics();

	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}

	@Override
	public BoardCell pickLocation(HashSet<BoardCell> targets) {
		System.out.println(targets.size());
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
	
	@Override
	public void makeMove() {
		board.mustFinish = true;
		board.validate();
		board.repaint();
	}
	
	public void finishTurn(BoardCell moveHere) {
		row = moveHere.getRow();
		column = moveHere.getCol();
		board.mustFinish = false;
		board.validate();
		board.repaint();
	}

}
