package tests;

import java.util.Set;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {

	private static Board board;

	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "Legend.txt");		
		board.initialize();
	}

	@Test
	public void testWalkWayAdjs()
	{
		Set<BoardCell> testList = board.getAdjList(4, 4);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(3, 4)));
		assertTrue(testList.contains(board.getCellAt(4, 3)));
		assertTrue(testList.contains(board.getCellAt(4, 5)));
		assertTrue(testList.contains(board.getCellAt(5, 4)));
	}

	@Test
	public void testEdgeAdjs()
	{
		Set<BoardCell> testList = board.getAdjList(0, 5);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(0, 4)));
		assertTrue(testList.contains(board.getCellAt(1, 5)));

		testList = board.getAdjList(21, 5);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(20, 5)));
		assertTrue(testList.contains(board.getCellAt(21, 6)));

		testList = board.getAdjList(21, 15);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(21, 16)));
		assertTrue(testList.contains(board.getCellAt(20, 15)));

		testList = board.getAdjList(21, 16);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(21, 15)));
		assertTrue(testList.contains(board.getCellAt(20, 16)));

	}

	@Test
	public void testRoomBorderNoDoorAdjs()
	{
		Set<BoardCell> testList = board.getAdjList(17, 15);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(16, 15)));
		assertTrue(testList.contains(board.getCellAt(17, 16)));
		assertTrue(testList.contains(board.getCellAt(18, 15)));

		testList = board.getAdjList(8, 19);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 18)));
		assertTrue(testList.contains(board.getCellAt(8, 20)));
		assertTrue(testList.contains(board.getCellAt(9, 19)));

	}

	@Test
	public void testRoomBorderDoorAdjs()
	{
		Set<BoardCell> testList = board.getAdjList(2, 2);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(2, 1)));
		assertTrue(testList.contains(board.getCellAt(1, 2)));
		assertTrue(testList.contains(board.getCellAt(2, 3)));
		assertTrue(testList.contains(board.getCellAt(3, 2)));


		testList = board.getAdjList(4, 5);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(3, 5)));
		assertTrue(testList.contains(board.getCellAt(5, 5)));
		assertTrue(testList.contains(board.getCellAt(4, 4)));
		assertTrue(testList.contains(board.getCellAt(4, 6)));


		testList = board.getAdjList(7, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 3)));
		assertTrue(testList.contains(board.getCellAt(8, 3)));
		assertTrue(testList.contains(board.getCellAt(7, 2)));
		assertTrue(testList.contains(board.getCellAt(7, 4)));


		testList = board.getAdjList(16, 3);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 3)));
		assertTrue(testList.contains(board.getCellAt(17, 3)));
		assertTrue(testList.contains(board.getCellAt(16, 4)));

	}

	@Test
	public void testInDoorAdj()
	{
		Set<BoardCell> testList = board.getAdjList(16, 13);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 13)));

		testList = board.getAdjList(19, 18);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(19, 17)));
	}

	@Test
	public void testWalkwayMovement()
	{
		board.calcTargets(0, 16, 1);
		Set<BoardCell> testList = board.getTargets();
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(1, 16)));

		board.calcTargets(7, 15, 1);
		testList = board.getTargets();
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 15)));
		assertTrue(testList.contains(board.getCellAt(6, 15)));
		assertTrue(testList.contains(board.getCellAt(7, 16)));
		assertTrue(testList.contains(board.getCellAt(7, 14)));

		board.calcTargets(5, 0, 2);
		testList = board.getTargets();
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(5, 2)));
		assertTrue(testList.contains(board.getCellAt(4, 1)));
	}

	@Test
	public void testLeavingRoomTargets()
	{
		board.calcTargets(14, 19, 3);
		Set<BoardCell> testList = board.getTargets();
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(16, 20)));
		assertTrue(testList.contains(board.getCellAt(16, 18)));
		assertTrue(testList.contains(board.getCellAt(15, 17)));
		
		board.calcTargets(10, 17, 2);
		testList = board.getTargets();
		for(BoardCell o: testList) {
			System.out.println(o.getRow() + " " + o.getCol());
		}
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 18)));
		assertTrue(testList.contains(board.getCellAt(9, 16)));
		assertTrue(testList.contains(board.getCellAt(8, 17)));

	}

	@Test
	public void testEnteringRoomTargets()
	{
		board.calcTargets(4, 13, 1);
		Set<BoardCell> testList = board.getTargets();
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(3, 13)));
		assertTrue(testList.contains(board.getCellAt(5, 13)));

		board.calcTargets(11, 1, 2);
		testList = board.getTargets();
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(12, 0)));
		assertTrue(testList.contains(board.getCellAt(10, 0)));
		assertTrue(testList.contains(board.getCellAt(10, 2)));
		assertTrue(testList.contains(board.getCellAt(11, 3)));
	}

}
