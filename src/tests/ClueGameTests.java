package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ClueGame.Board;
import ClueGame.BoardCell;
import ClueGame.DoorDirection;

public class ClueGameTests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 21;

	private static Board board;

	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt");		
		board.initialize();
	}
	@Test
	public void testRooms() {
		Map<Character, String> legend = board.getLegend();
		assertEquals("Bean Bag Room", legend.get('B'));
		assertEquals("Rumpus Room", legend.get('R'));
		assertEquals("Feet Area", legend.get('A'));
		assertEquals(LEGEND_SIZE, legend.size());
	}
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_COLUMNS, board.getNumColumns());
		assertEquals(NUM_ROWS, board.getNumRows());		
	}
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(3, 13);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(6, 18);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(8, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(17, 13);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(10, 10);
		assertFalse(room.isDoorway());	
		BoardCell cell = board.getCellAt(0, 0);
		assertFalse(cell.isDoorway());		

	}
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row=0; row < board.getNumRows(); row++)
			for (int col=0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(11, numDoors);
	}
	@Test
	public void testRoomInitials() {
		assertEquals('B', board.getCellAt(0, 0).getInitial());
		assertEquals('C', board.getCellAt(0, 6).getInitial());
		assertEquals('G', board.getCellAt(0, 12).getInitial());
		assertEquals('E', board.getCellAt(0, 19).getInitial());
		assertEquals('M', board.getCellAt(8, 0).getInitial());
	}
}
