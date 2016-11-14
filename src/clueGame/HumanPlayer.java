package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;

import javax.swing.JOptionPane;

public class HumanPlayer extends Player {
	private pickLocationListener ml = new pickLocationListener();
	

	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
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
		board.addMouseListener(ml);
	}
	
	private class pickLocationListener implements MouseListener {
		public void mousePressed (MouseEvent event) {}
		public void mouseReleased (MouseEvent event) {}
		public void mouseEntered (MouseEvent event) {}
		public void mouseExited (MouseEvent event) {}
		public void mouseClicked (MouseEvent event) {
			Point spot = event.getPoint();
			BoardCell moveHere = board.getCellAt(spot.y / 30, spot.x / 30);
			if (board.getTargets().contains(moveHere)) {
				finishTurn(moveHere);
			}
			else {
				JOptionPane.showMessageDialog(ClueGame.theInstance, "Invalid square!", "Doh!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void finishTurn(BoardCell moveHere) {
		board.removeMouseListener(ml);
		row = moveHere.getRow();
		column = moveHere.getCol();
		board.mustFinish = false;
		board.validate();
		board.repaint();
	}

}
