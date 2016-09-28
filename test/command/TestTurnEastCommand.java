package command;

import static org.junit.Assert.*;
import lifeform.Human;
import lifeform.LifeForm;

import org.junit.Test;

import environment.Environment;

public class TestTurnEastCommand {

	/**
	 * Tests the TurnEastCommand on the player
	 * Should change the player's direction to east
	 */
	@Test
	public void testTurnEastCommand() {
		
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		LifeForm bob = new Human("bob", 50, 3);
		env.addPlayer(bob, 1, 1);
		
		//default direction is north
		assertEquals("north", bob.getDirection()); 

		//sets the direction of the player to east
		TurnEastCommand turnEastCommand = new TurnEastCommand();
		turnEastCommand.execute();
		
		//makes sure the player's direction is set to east
		assertEquals("east", bob.getDirection()); 
	}
	

}
