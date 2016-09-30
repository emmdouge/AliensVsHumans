package command;

import static org.junit.Assert.*;

import lifeform.Alien;
import lifeform.Human;
import lifeform.Lifeform;

import org.junit.Test;

import environment.Environment;
import exceptions.RecoveryRateException;

public class TestMoveCommandWest {

	/**
	 * Tests the MoveCommand on the player
	 * should move the player lifeform to the left because of it is facing west
	 * while skipping aliens and not going out of bounds
	 * @throws RecoveryRateException
	 */
	@Test
	public void test() throws RecoveryRateException {
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		Lifeform bob = new Human("bob", 50, 3);
		env.setPlayer(bob, 2, 4);
		env.getPlayer().setDirection("west");
		
		Lifeform cob = new Alien("cob", 150);
		
		//adds an alien to the left of the player
		env.addLifeForm(cob, 2, 3);
		
		//create a move command that wants to move two spaces west
		MoveCommand moveCommand = new MoveCommand(2);
		moveCommand.execute();
		
		//the player skips over the alien and moves one space
		//to travel two spaces in the direction it is facing
		assertEquals(env.getLifeForm(2, 1).getName(), "bob");
		
		//reinitialize the move command to move one space
		moveCommand = new MoveCommand(1);
		moveCommand.execute();
		
		//the player moves one space in the direction it is facing
		assertEquals(env.getLifeForm(2, 0).getName(), "bob");
		
		//should not go out of bounds when asked to move more spaces
		moveCommand.execute();
		
		//the lifeform that moved is the same as the player
		assertEquals(env.getLifeForm(2, 0).getName(), "bob");
		
		
	}

}
