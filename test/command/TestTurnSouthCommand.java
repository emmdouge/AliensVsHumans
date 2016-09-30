package command;

import static org.junit.Assert.*;
import lifeform.Human;
import lifeform.Lifeform;

import org.junit.Test;

import environment.Environment;

public class TestTurnSouthCommand {

	/**
	 * Tests the TurnSouthCommand on the player
	 * Should change the player's direction to west
	 */
	@Test
	public void testTurnSouthCommand() {
		
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		Lifeform bob = new Human("bob", 50, 3);
		env.setPlayer(bob, 1, 1);

		TurnSouthCommand turnSouthCommand = new TurnSouthCommand();
		
		//sets the player's direction to south
		turnSouthCommand.execute();
		
		//makes sure the player's direction is south
		assertEquals("south", bob.getDirection()); 
	}

}
