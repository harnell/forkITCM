package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class gameActionTests {
	
private static Board board;
	
	@BeforeClass
	public static void setup(){
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "Legend.txt");		
		board.initialize();
	}
	
	@Test
	public void testTargets(){
		BoardCell testOracle = board.getPlayers().get(3).pickLocation(6); //Oracle rolls 6, should enter Easter Isle
		assertEquals(testOracle.getRow(), 6);
		assertEquals(testOracle.getCol(), 18);
		BoardCell testGrave = board.getPlayers().get(5).pickLocation(4); //Gravemind rolls 4, should enter Solarium
		assertEquals(testGrave.getRow(), 12);
		assertEquals(testGrave.getCol(), 0);
		BoardCell testSJ = board.getPlayers().get(4).pickLocation(4); //Sargent Johnson rolls 4, should be on (4, 16)
		assertEquals(testSJ.getRow(), 4);
		assertEquals(testSJ.getCol(), 16);
	}
	
	@Test
	public void makeAccusation(){
		
	}
	
	@Test
	public void creatSuggestion(){
		
	}
	
	@Test
	public void disproveSuggestion(){
		
	}
	
	@Test
	public void handleSuggestion(){
		
	}
	
}
