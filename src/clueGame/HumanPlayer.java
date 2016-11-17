package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
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
		ClueGame.theInstance.accusePopUp(this);

		//this.accusation = acc;
		//boolean result = board.checkAccusation(accusation);
		return false;
	}

	@Override
	public Solution createSuggestion() {
		ClueGame.theInstance.gui.getGuessInput().setText(suggestion.toString());
		Card disproved = board.handleSuggestion(suggestion, board.getPlayers(), board.getPlayers().indexOf(this));
		if (disproved != null) {
			shouldAccuse = false;
			ClueGame.theInstance.gui.getResultOutput().setText(disproved.getCardName());
		}
		else {
			shouldAccuse = true;
			accuseWithThis = suggestion;
			ClueGame.theInstance.gui.getResultOutput().setText("No New Clue");
		}
		ClueGame.theInstance.moveToRoom(this);
		board.validate();
		board.repaint();
		return suggestion;
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
	
	public void setAccusation(String p, String r, String w) {
		accusation = new Solution(p, r, w);
	}
	
	public void setSuggestion(String p, String r, String w) {
		suggestion = new Solution(p, r, w);
	}
	
	public void finishTurn(BoardCell moveHere) {
		board.removeMouseListener(ml);
		row = moveHere.getRow();
		column = moveHere.getCol();
		board.mustFinish = false;
		board.validate();
		board.repaint();
		if (board.getCellAt(row, column).getInitial() != 'F') {
			ClueGame.theInstance.suggestPopUp(this);
		}
	}

}
