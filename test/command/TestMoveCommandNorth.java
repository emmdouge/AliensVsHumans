package command;

import static org.junit.Assert.*;

import lifeform.Alien;
import lifeform.Human;
import lifeform.Lifeform;

import org.junit.Test;

import environment.Environment;
import exceptions.RecoveryRateException;

public class TestMoveCommandNorth {

	/**
	 * Tests the MoveCommand on the player
	 * should move the player lifeform up because of it is facing north
	 * while skipping aliens and not going out of bounds
	 * @throws RecoveryRateException
	 */
	@Test
	public void test() throws RecoveryRateException {
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		Lifeform bob = new Human("bob", 50, 3);
		env.setPlayer(bob, 4, 2);

		Lifeform cob = new Alien("cob", 150);
		
		//adds a lifeform three spaces above the player
		env.addLifeForm(cob, 1, 2);
		
		//create a move command that wants to move one space north
		MoveCommand moveCommand = new MoveCommand(1);
		moveCommand.execute();
		
		//player is in the correct cell
		assertEquals(env.getLifeForm(3, 2).getName(), "bob");
		
		//move one space north again
		moveCommand = new MoveCommand(1);
		moveCommand.execute();
		
		//player is in the correct cell
		assertEquals(env.getLifeForm(2, 2).getName(), "bob");
		
		//move one space north again, should skip the alien
		moveCommand = new MoveCommand(1);
		moveCommand.execute();
		
		//player is in the correct cell
		assertEquals(env.getLifeForm(0, 2).getName(), "bob");
		
		//move one space north again
		moveCommand.execute();
		
		//does not go out of bounds
		assertEquals(env.getLifeForm(0, 2).getName(), "bob");
	}

}
