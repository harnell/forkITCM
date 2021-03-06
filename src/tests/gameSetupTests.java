package tests;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class gameSetupTests {
	
	private static Board board;
	
	@BeforeClass
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "Legend.txt", "person.txt", "weapons.txt");		
		board.initialize();
	}
	
	@Test
	public void TestPersonDeck() {
		//personsDeck
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
		assertTrue(board.getDeck().get(7).equals(new Card("Easter Isle", CardType.ROOM)) || board.getDeck().get(7).equals(new Card("Locker Room", CardType.ROOM)));
		assertTrue(board.getDeck().get(12).equals(new Card("Brute Shot", CardType.WEAPON)) || board.getDeck().get(12).equals(new Card("Plasma Grenade", CardType.WEAPON)));
		assertTrue(board.getDeck().get(17).equals(new Card("Sargent Johnson", CardType.PERSON)) || board.getDeck().get(17).equals(new Card("Gravemind", CardType.PERSON)));
		assertTrue(board.getDeck().size() == 18);
		for (int i = 0; i < 8; i++){
			assertEquals(CardType.ROOM, board.getDeck().get(i).getType());
		}
		for (int i = 8; i < 13; i++){
			assertEquals(CardType.WEAPON, board.getDeck().get(i).getType());
		}
		for (int i = 13; i < 18; i++){
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
		board.dealCards();
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
