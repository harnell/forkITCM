package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;

public class gameActionTests {
	
private static Board board;
	
	@BeforeClass
	public static void setup(){
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "Legend.txt");		
		board.initialize();
	}
	
	@Test
	public void testTargetsNoRooms(){
		
	}
	
	@Test
	public void testTargetRandomSelection() {
	    // Pick a location with no rooms in target, just three targets
		// Arbiter 3 steps
	    boolean loc_19_6 = false;
	    boolean loc_21_6 = false;
	    boolean loc_20_5 = false;
	    boolean loc_18_5 = false;
	    // Run the test a large number of times
	    for (int i=0; i<100; i++) {
	        BoardCell selected = board.getPlayers().get(1).pickLocation(3);
	        if (selected == board.getCellAt(19, 6))
	            loc_19_6 = true;
	        else if (selected == board.getCellAt(21, 6))
	            loc_21_6 = true;
	        else if (selected == board.getCellAt(20, 5))
	            loc_20_5 = true;
	        else if (selected == board.getCellAt(18, 5))
	            loc_18_5 = true;
	        else
	            fail("Invalid target selected");
	        }
	        // Ensure each target was selected at least once
	        assertTrue(loc_19_6);
	        assertTrue(loc_21_6);
	        assertTrue(loc_20_5);	
	        assertTrue(loc_18_5);
	        
	        //Cortana 4
	        boolean loc_17_15 = false;
		    boolean loc_18_16 = false;
		    boolean loc_19_17 = false;
		    boolean loc_20_16 = false;
		    boolean loc_19_15 = false;
		    // on itself
		    boolean loc_21_15 = false;


		    // Run the test a large number of times
		    for (int i=0; i<100; i++) {
		        BoardCell selected = board.getPlayers().get(2).pickLocation(4);
		        if (selected == board.getCellAt(17, 15))
		        	loc_17_15 = true;
		        else if (selected == board.getCellAt(18, 16))
		        	loc_18_16 = true;
		        else if (selected == board.getCellAt(19, 17))
		        	loc_19_17 = true;
		        else if (selected == board.getCellAt(20, 16))
		        	loc_20_16 = true;
		        else if (selected == board.getCellAt(19, 15))
		        	loc_19_15 = true;
		        else if (selected == board.getCellAt(21, 15))
		        	loc_21_15 = true;
		        else
		            fail("Invalid target selected");
		        }
		        // Ensure each target was selected at least once
		        assertTrue(loc_17_15);
		        assertTrue(loc_18_16);
		        assertTrue(loc_19_17);	
		        assertTrue(loc_20_16);
		        assertTrue(loc_19_15);	
		        assertTrue(!loc_21_15);
	}
	
	@Test
	public void testTargetInsanityBackAndForth() {
	    // Pick a location with no rooms in target, just three targets
		// Gravemind steps 3
	    boolean loc_10_3 = false;
	    boolean loc_11_2 = false;
	    boolean loc_10_1 = false;
	    boolean loc_11_0 = false;
	    boolean loc_12_0 = false;
	    // Run the test a large number of times
	    for (int i=0; i<100; i++) {
	        board.getPlayers().get(5).setLastRoom('S');
	        BoardCell selected = board.getPlayers().get(5).pickLocation(3);
	        
	        if (selected == board.getCellAt(10, 3))
	        	loc_10_3 = true;
	        else if (selected == board.getCellAt(11, 2))
	        	loc_11_2 = true;
	        else if (selected == board.getCellAt(10, 1))
	        	loc_10_1 = true;
	        else if (selected == board.getCellAt(11, 0))
	        	loc_11_0 = true;
	        else if (selected == board.getCellAt(12, 0))
	        	loc_12_0 = true;
	        else
	            fail("Invalid target selected");
	        }
	        // Ensure each target was selected at least once
	        assertTrue(loc_10_3);
	        assertTrue(loc_11_2);
	        assertTrue(loc_10_1);	
	        assertTrue(loc_11_0);
	        assertTrue(loc_12_0);
	        
	        //Cortana 6
	        boolean loc_15_15 = false;
	        boolean loc_16_16 = false;
	        boolean loc_17_17 = false;
	        boolean loc_19_18 = false;	        
	        boolean loc_17_15 = false;
		    boolean loc_18_16 = false;
		    boolean loc_19_17 = false;
		    boolean loc_20_16 = false;
		    boolean loc_19_15 = false;

		    // Run the test a large number of times
		    for (int i=0; i<100; i++) {	  
		    	ComputerPlayer tester = new ComputerPlayer("Cortana", 21, 15, Color.blue);
		    	tester.setLastRoom('R');
		    	BoardCell selected = tester.pickLocation(6);
		        if (selected == board.getCellAt(15, 15))
		        	loc_15_15 = true;
		        else if (selected == board.getCellAt(16, 16))
		        	loc_16_16 = true;
		        else if (selected == board.getCellAt(17, 17))
		        	loc_17_17 = true;
		        else if (selected == board.getCellAt(19, 18))
		        	loc_19_18 = true;
		        else if (selected == board.getCellAt(17, 15))
		        	loc_17_15 = true;
		        else if (selected == board.getCellAt(18, 16))
		        	loc_18_16 = true;
		        else if (selected == board.getCellAt(19, 17))
		        	loc_19_17 = true;
		        else if (selected == board.getCellAt(20, 16))
		        	loc_20_16 = true;
		        else if (selected == board.getCellAt(19, 15))
		        	loc_19_15 = true;
		        else
		            fail("Invalid target selected");
		        }
		        // Ensure each target was selected at least once
		        assertTrue(loc_15_15);
		        assertTrue(loc_16_16);
		        assertTrue(loc_17_17);	
		        assertTrue(loc_19_18);
		        assertTrue(loc_17_15);
		        assertTrue(loc_18_16);
		        assertTrue(loc_19_17);	
		        assertTrue(loc_20_16);
		        assertTrue(loc_19_15);	

	}
	
	@Test
	public void testTargetsRoomPriority(){
		BoardCell testOracle = board.getPlayers().get(3).pickLocation(6); //Oracle rolls 6, should enter Easter Isle
		assertTrue((testOracle.getRow() == 6 && testOracle.getCol() == 18) || (testOracle.getRow() == 10 && testOracle.getCol() == 17));
		BoardCell testGrave = board.getPlayers().get(5).pickLocation(4); //Gravemind rolls 4, should enter Solarium
		assertEquals(testGrave.getRow(), 12);
		assertEquals(testGrave.getCol(), 0);
		BoardCell testSJ = board.getPlayers().get(4).pickLocation(4); //Sargent Johnson rolls 4, should be on (4, 16)
		assertEquals(testSJ.getRow(), 4);
		assertEquals(testSJ.getCol(), 16);
	}
	
	@Test
	public void testMakeAccusation(){
		// Checking for exact solution
		ComputerPlayer tester = new ComputerPlayer("tester", 21, 15, Color.blue);
		for (Card c: board.getDeck()){
			tester.addCardToSeenCards(c);
		}
		assertTrue(tester.makeAccusation());
		
		// Checking for wrong person
		ComputerPlayer testerPerson = new ComputerPlayer("tester", 21, 15, Color.blue);
		for (Card c: board.getDeck()){
			if (!c.getType().equals(CardType.PERSON)){
				testerPerson.addCardToSeenCards(c);
			}
		}
		Card personAnswer = new Card(board.getTheAnswer().person, CardType.PERSON);
		testerPerson.addCardToSeenCards(personAnswer);
		assertFalse(testerPerson.makeAccusation());
		
		// Checking for wrong room
		ComputerPlayer testerRoom = new ComputerPlayer("tester", 21, 15, Color.blue);
		for (Card c: board.getDeck()){
			if (!c.getType().equals(CardType.ROOM)){
				testerRoom.addCardToSeenCards(c);
			}
		}
		Card roomAnswer = new Card(board.getTheAnswer().room, CardType.ROOM);
		testerRoom.addCardToSeenCards(roomAnswer);
		assertFalse(testerRoom.makeAccusation());
		
		// Checking for wrong weapon
		ComputerPlayer testerWeapon = new ComputerPlayer("tester", 21, 15, Color.blue);
		for (Card c: board.getDeck()){
			if (!c.getType().equals(CardType.WEAPON)){
				testerWeapon.addCardToSeenCards(c);
			}
		}
		Card weaponAnswer = new Card(board.getTheAnswer().weapon, CardType.WEAPON);
		testerWeapon.addCardToSeenCards(weaponAnswer);
		assertFalse(testerWeapon.makeAccusation());
		
	}
	
	@Test
	public void createSuggestionComputerNotFull(){
		ComputerPlayer testerSuggest = new ComputerPlayer("tester", 6, 18, Color.blue); // Room is Easter Isle
		Set<Card> seenTestNotFull = new HashSet<Card>();
		seenTestNotFull.add(new Card("Needler", CardType.WEAPON));
		seenTestNotFull.add(new Card("Energy Sword", CardType.WEAPON));
		seenTestNotFull.add(new Card("Sargent Johnson", CardType.PERSON));
		seenTestNotFull.add(new Card("Oracle", CardType.PERSON));
		testerSuggest.addSetToSeenCards(seenTestNotFull);
		
		boolean weapon_Needler = true;
        boolean weapon_eSword = true;
        boolean person_Oracle = true;
        boolean person_Sarg = true;	        

	    // Run the test a large number of times
	    for (int i=0; i<100; i++) {	  
	    	testerSuggest.createSuggestion();
	    	
	    	String suggestedWeapon = testerSuggest.getSuggestion().weapon;
	    	String suggestedPerson = testerSuggest.getSuggestion().person;
	        if (suggestedWeapon.equals("Needler"))
	        	weapon_Needler = false;
	        else if (suggestedWeapon.equals("Energy Sword"))
	        	weapon_eSword = false;
	        else if (suggestedPerson.equals("Oracle"))
	        	person_Oracle = false;
	        else if (suggestedPerson.equals("Sargent Johnson"))
	        	person_Sarg = false;
	        }
	        // Ensure each target was selected at least once
	        assertTrue(testerSuggest.getSuggestion().room.equals("Easter Isle"));
	        assertTrue(weapon_Needler);
	        assertTrue(weapon_eSword);	
	        assertTrue(person_Oracle);
	        assertTrue(person_Sarg);
	}
	
	
	//Test a created suggestion when there is only 1 weapon and person not seen
	@Test
	public void createSuggestionComputerSingleWeaponAndPerson(){
		ComputerPlayer testerSuggest = new ComputerPlayer("tester", 6, 18, Color.blue); // Room is Easter Isle
		Set<Card> seenTestNotFull = new HashSet<Card>();
		seenTestNotFull.add(new Card("Needler", CardType.WEAPON));
		seenTestNotFull.add(new Card("Energy Sword", CardType.WEAPON));
		seenTestNotFull.add(new Card("Spartan Laser", CardType.WEAPON));
		seenTestNotFull.add(new Card("Brute Shot", CardType.WEAPON));
		seenTestNotFull.add(new Card("Plasma Grenade", CardType.WEAPON));
		seenTestNotFull.add(new Card("Sargent Johnson", CardType.PERSON));
		seenTestNotFull.add(new Card("Oracle", CardType.PERSON));
		seenTestNotFull.add(new Card("Cortana", CardType.PERSON));
		seenTestNotFull.add(new Card("Arbiter", CardType.PERSON));
		seenTestNotFull.add(new Card("Gravemind", CardType.PERSON));
		testerSuggest.addSetToSeenCards(seenTestNotFull);
		
		boolean weapon_Needler = true;
        boolean weapon_eSword = true;
        boolean weapon_sLaser = true;
        boolean weapon_bShot = true;
        boolean weapon_pGrenade = true;
        boolean person_Oracle = true;
        boolean person_Sarg = true;
        boolean person_Cortana = true;
        boolean person_Gravemind = true;
        boolean person_Arbiter = true;

	    // Run the test a large number of times
	    for (int i=0; i<100; i++) {	  
	    	testerSuggest.createSuggestion();
	    	
	    	String suggestedWeapon = testerSuggest.getSuggestion().weapon;
	    	String suggestedPerson = testerSuggest.getSuggestion().person;
	        if (suggestedWeapon.equals("Needler"))
	        	weapon_Needler = false;
	        else if (suggestedWeapon.equals("Energy Sword"))
	        	weapon_eSword = false;
	        else if (suggestedWeapon.equals("Spartan Laser"))
	        	weapon_sLaser = false;
	        else if (suggestedWeapon.equals("Brute Shot"))
	        	weapon_bShot = false;
	        else if (suggestedWeapon.equals("Plasma Grenade"))
	        	weapon_pGrenade = false;
	        else if (suggestedPerson.equals("Oracle"))
	        	person_Oracle = false;
	        else if (suggestedPerson.equals("Sargent Johnson"))
	        	person_Sarg = false;
	        else if (suggestedPerson.equals("Cortana"))
	        	person_Cortana = false;
	        else if (suggestedPerson.equals("Gravemind"))
	        	person_Gravemind = false;
	        else if (suggestedPerson.equals("Arbiter"))
	        	person_Arbiter = false;
	        }
	        // Ensure each target was selected at least once
	        assertTrue(testerSuggest.getSuggestion().room.equals("Easter Isle"));
	        assertTrue(weapon_Needler);
	        assertTrue(weapon_eSword);
	        assertTrue(weapon_sLaser);
	        assertTrue(weapon_bShot);
	        assertTrue(weapon_pGrenade);
	        assertTrue(person_Oracle);
	        assertTrue(person_Sarg);
	        assertTrue(person_Cortana);
	        assertTrue(person_Gravemind);
	        assertTrue(person_Arbiter);
	}
	
	@Test
	public void disproveSuggestion(){
		
	}
	
	@Test
	public void handleSuggestion(){
		
	}
	
}
