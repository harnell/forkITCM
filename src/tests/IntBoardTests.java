package tests;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	private IntBoard gameBoard;
	@Before
	public void boardSetup() {
		gameBoard = new IntBoard();
	}
	@Test
	public void testAdj0_0() {
		BoardCell testCell = gameBoard.getCell(0,0);
		Set<BoardCell> testList = gameBoard.getAdjList(testCell);
		assertTrue(testList.contains(gameBoard.getCell(1, 0)));
		assertTrue(testList.contains(gameBoard.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdj3_3() {
		BoardCell testCell = gameBoard.getCell(3,3);
		Set<BoardCell> testList = gameBoard.getAdjList(testCell);
		assertTrue(testList.contains(gameBoard.getCell(2, 3)));
		assertTrue(testList.contains(gameBoard.getCell(3, 2)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdj1_3() {
		BoardCell testCell = gameBoard.getCell(1,3);
		Set<BoardCell> testList = gameBoard.getAdjList(testCell);
		assertTrue(testList.contains(gameBoard.getCell(0, 3)));
		assertTrue(testList.contains(gameBoard.getCell(1, 2)));
		assertTrue(testList.contains(gameBoard.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	@Test
	public void testAdj3_0() {
		BoardCell testCell = gameBoard.getCell(3,0);
		Set<BoardCell> testList = gameBoard.getAdjList(testCell);
		assertTrue(testList.contains(gameBoard.getCell(2, 0)));
		assertTrue(testList.contains(gameBoard.getCell(3, 1)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdj1_1() {
		BoardCell testCell = gameBoard.getCell(1,1);
		Set<BoardCell> testList = gameBoard.getAdjList(testCell);
		assertTrue(testList.contains(gameBoard.getCell(0, 1)));
		assertTrue(testList.contains(gameBoard.getCell(1, 0)));
		assertTrue(testList.contains(gameBoard.getCell(2, 1)));
		assertTrue(testList.contains(gameBoard.getCell(1, 2)));
		assertEquals(4, testList.size());
	}
	@Test
	public void testAdj2_2() {
		BoardCell testCell = gameBoard.getCell(2,2);
		Set<BoardCell> testList = gameBoard.getAdjList(testCell);
		assertTrue(testList.contains(gameBoard.getCell(1, 2)));
		assertTrue(testList.contains(gameBoard.getCell(2, 1)));
		assertTrue(testList.contains(gameBoard.getCell(3, 2)));
		assertTrue(testList.contains(gameBoard.getCell(2, 3)));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testTargets0_1()
	{
		BoardCell cell = gameBoard.getCell(0, 0);
		gameBoard.calcTargets(cell, 1);
		Set targets = gameBoard.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(gameBoard.getCell(1, 0)));
		assertTrue(targets.contains(gameBoard.getCell(0, 1)));
		
		
	}
	@Test
	public void testTargets0_2()
	{
		BoardCell cell = gameBoard.getCell(0, 0);
		gameBoard.calcTargets(cell, 2);
		Set targets = gameBoard.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(gameBoard.getCell(2, 0)));
		assertTrue(targets.contains(gameBoard.getCell(0, 2)));
		assertTrue(targets.contains(gameBoard.getCell(1, 1)));
	}
	@Test
	public void testTargets0_3()
	{
		BoardCell cell = gameBoard.getCell(0, 0);
		gameBoard.calcTargets(cell, 3);
		Set targets = gameBoard.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(gameBoard.getCell(3, 0)));
		assertTrue(targets.contains(gameBoard.getCell(2, 1)));
		assertTrue(targets.contains(gameBoard.getCell(1, 2)));
		assertTrue(targets.contains(gameBoard.getCell(0, 3)));
		
		
	}
	@Test
	public void testTargets10_1()
	{
		BoardCell cell = gameBoard.getCell(2, 2);
		gameBoard.calcTargets(cell, 1);
		Set targets = gameBoard.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(gameBoard.getCell(1, 2)));
		assertTrue(targets.contains(gameBoard.getCell(2, 1)));
		assertTrue(targets.contains(gameBoard.getCell(3, 2)));
		assertTrue(targets.contains(gameBoard.getCell(2, 3)));
		
		
	}
	@Test
	public void testTargets10_2()
	{
		BoardCell cell = gameBoard.getCell(2, 2);
		gameBoard.calcTargets(cell, 2);
		Set targets = gameBoard.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(gameBoard.getCell(0, 2)));
		assertTrue(targets.contains(gameBoard.getCell(1, 1)));
		assertTrue(targets.contains(gameBoard.getCell(1, 3)));
		assertTrue(targets.contains(gameBoard.getCell(2, 0)));
		assertTrue(targets.contains(gameBoard.getCell(3, 1)));
		assertTrue(targets.contains(gameBoard.getCell(3, 3)));
		
		
	}
	@Test
	public void testTargets10_3()
	{
		BoardCell cell = gameBoard.getCell(2, 2);
		gameBoard.calcTargets(cell, 3);
		Set targets = gameBoard.getTargets();
		assertEquals(4, targets.size());
		System.out.println("adasdad");
		assertTrue(targets.contains(gameBoard.getCell(3, 0)));
		assertTrue(targets.contains(gameBoard.getCell(0, 1)));
		assertTrue(targets.contains(gameBoard.getCell(1, 0)));
		assertTrue(targets.contains(gameBoard.getCell(0, 3)));
	}

	
	
}
	