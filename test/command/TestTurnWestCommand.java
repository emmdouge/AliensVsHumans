package command;

import static org.junit.Assert.*;
import lifeform.Human;
import lifeform.Lifeform;

import org.junit.Test;

import environment.Environment;

public class TestTurnWestCommand {

	/**
	 * Tests the TurnWestCommand on the player
	 * Should change the player's direction to west
	 */
	@Test
	public void testTurnWestCommand() {
		
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		Lifeform bob = new Human("bob", 50, 3);
		
		env.setPlayer(bob, 1, 1);
		
		//makes sure the default direction is north
		assertEquals("north", bob.getDirection()); 

		
		TurnWestCommand turnWestCommand = new TurnWestCommand();
		
		//sets player's direction to  west
		turnWestCommand.execute();
		
		//makes sure the player's direction is set to west
		assertEquals("west", bob.getDirection()); 
	}
	

}
