package tests;

import java.util.Set;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;

public class gameSetupTests {
	
	private static Board board;
	
	@BeforeClass
	public static void setup(){
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "Legend.txt");		
		board.initialize();
		
	}
	
	@Test
	public void TestPersonDeck(){
		//personsDeck
		assertTrue(board.getPersonDeck().contains(new Card("Cortana", CardType.PERSON)));
		assertTrue(board.getPersonDeck().size() == 6);
		for (int i = 0; i < 6; i++){
			assertEquals(CardType.PERSON, board.getPersonDeck().get(i).getType());
		}
	}
	@Test
	public void TestWeaponsDeck(){
		//weaponsDeck
		assertTrue(board.getWeaponsDeck().contains(new Card("Gravity Hammer", CardType.WEAPON)));
		assertTrue(board.getWeaponsDeck().size() == 6);
		for (int i = 0; i < 6; i++){
			assertEquals(CardType.WEAPON, board.getWeaponsDeck().get(i).getType());
		}
	}
	@Test
	public void TestRoomDeck(){
		//roomDeck
		assertTrue(board.getRoomDeck().contains(new Card("Easter Isle", CardType.ROOM)));
		assertTrue(board.getRoomDeck().size() == 9);
		for (int i = 0; i < 9; i++){
			assertEquals(CardType.ROOM, board.getRoomDeck().get(i).getType());
		}
	}
//	@Test
//	public void TestPersonCards(){
//		
//	}
//	@Test
//	public void TesWeaponsCards(){
//		
//	}
//
//	@Test
//	public void TestDeckSize(){
//		
//	}
//	@Test
//	public void TestPlayersInitialized(){
//		
//	}
	
	
}
