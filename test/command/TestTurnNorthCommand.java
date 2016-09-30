package command;

import static org.junit.Assert.*;
import lifeform.Human;
import lifeform.Lifeform;

import org.junit.Test;

import environment.Environment;

public class TestTurnNorthCommand {

	/**
	 * Tests the TurnNorthCommand on the player
	 * Should change the player's direction to north
	 */	
	@Test
	public void testTurnNorthCommand() {
		
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		Lifeform bob = new Human("bob", 50, 3);
		env.setPlayer(bob, 1, 1);
		
		//default direction is north
		assertEquals("north", bob.getDirection()); 
		
		
		TurnEastCommand turnEastCommand = new TurnEastCommand();
		
		//turn east
		turnEastCommand.execute();
		
		//makes the player is actually facing east
		assertEquals("east", bob.getDirection()); 
		
		//sets the player's direction back to north
		TurnNorthCommand turnNorthCommand = new TurnNorthCommand();
		turnNorthCommand.execute();
		
		//makes sure the player's directon is actually set to north
		assertEquals("north", bob.getDirection()); 
	}

}
