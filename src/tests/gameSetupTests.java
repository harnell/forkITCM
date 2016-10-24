package tests;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

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
		//System.out.println(board.getPersonDeck().get(2).toString());
		assertTrue(board.getPersonDeck().get(2).equals(new Card("Cortana", CardType.PERSON)));			//Tests if certain car matches the item in that location in person.txt
		assertTrue(board.getPersonDeck().size() == 6);													//Tests if size of array correct, should ensure along with above that entire array is correct
		for (int i = 0; i < 6; i++){
			assertEquals(CardType.PERSON, board.getPersonDeck().get(i).getType());
		}
	}
	@Test
	public void TestWeaponsDeck(){
		//weaponsDeck
		assertTrue(board.getWeaponsDeck().get(3).equals(new Card("Gravity Hammer", CardType.WEAPON)));	//Tests if certain car matches the item in that location in weapons.txt
		assertTrue(board.getWeaponsDeck().size() == 6);													//Tests if size of array correct, should ensure along with above that entire array is correct
		for (int i = 0; i < 6; i++){
			assertEquals(CardType.WEAPON, board.getWeaponsDeck().get(i).getType());
		}
	}
	@Test
	public void TestRoomDeck(){
		//roomDeck
		assertTrue(board.getRoomDeck().get(7).equals(new Card("Easter Isle", CardType.ROOM)));
		assertTrue(board.getRoomDeck().size() == 9);
		for (int i = 0; i < 9; i++){
			assertEquals(CardType.ROOM, board.getRoomDeck().get(i).getType());
		}
	}
	@Test
	public void TestWholeDeck(){
		//deck
		assertTrue(board.getDeck().get(7).equals(new Card("Easter Isle", CardType.ROOM)));
		assertTrue(board.getDeck().get(12).equals(new Card("Gravity Hammer", CardType.WEAPON)));
		assertTrue(board.getDeck().get(17).equals(new Card("Cortana", CardType.PERSON)));
		assertTrue(board.getDeck().size() == 21);
		for (int i = 0; i < 9; i++){
			assertEquals(CardType.ROOM, board.getDeck().get(i).getType());
		}
		for (int i = 9; i < 15; i++){
			assertEquals(CardType.WEAPON, board.getDeck().get(i).getType());
		}
		for (int i = 15; i < 21; i++){
			assertEquals(CardType.PERSON, board.getDeck().get(i).getType());
		}
	}
	
	//Testing that the players were loaded correctly in the players Set in Board
	@Test
	public void TestPersonCards(){
		Set<Player> players = new TreeSet<Player>();
		for (Player x : board.getPlayers()){
			players.add(x);
		}
		ArrayList<Player> inOrder = new ArrayList<Player>(players);
		Player[] Order = new Player[6];
		int i = 0;
		for (Player y: inOrder){
			Order[i] = y;
			i++;
		}
		assertEquals(Order[0].getPlayerName(), "Arbiter");
		assertEquals(Order[3].getColor(), Color.green);
		assertEquals(Order[5].getColumn(), 16);
		assertEquals(Order[1].getRow(), 21);
		assertTrue(board.getPlayers().size() == 6);
	}
	

	
	@Test
	public void TestPlayersDealtHands(){
		board.makeSolution();
		board.dealCards();
		ArrayList<Card> toBeDealt = board.getDeck();
		ArrayList<Card> hasBeenDealt = new ArrayList<Card>();
		ArrayList<Player> tempPlayers = board.getPlayers();
		for (Player p: tempPlayers){
			Set<Card> tempCards = p.getMyCards();
			for (Card c: tempCards){
				//  No Duplicates
				assertTrue(!hasBeenDealt.contains(c));
				hasBeenDealt.add(c);
			}
			// Players should have roughly the same number of cards.
			assertTrue(tempCards.size() >= 3);	
		}
		// All cards dealt
		for(Card c: hasBeenDealt){
			assertTrue(board.getDeck().contains(c));
		}
	}
}
