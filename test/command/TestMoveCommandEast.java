package command;

import static org.junit.Assert.*;

import lifeform.Alien;
import lifeform.Human;
import lifeform.LifeForm;

import org.junit.Test;

import environment.Environment;
import exceptions.RecoveryRateException;

public class TestMoveCommandEast {

	/**
	 * Tests the MoveCommand on the player
	 * should move the player lifeform to the right because of it is facing east
	 * while skipping aliens and not going out of bounds
	 * @throws RecoveryRateException
	 */
	@Test
	public void test() throws RecoveryRateException {
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		LifeForm bob = new Human("bob", 50, 3);
		env.addPlayer(bob, 2, 1);
		env.getPlayer().setDirection("east");
		
		LifeForm cob = new Alien("cob", 150);
		
		//adds an alien directly to the right of the player
		env.addLifeForm(cob, 2, 2);
		
		//creates a move command that wants to move two spaces to the right
		MoveCommand moveCommand = new MoveCommand(2);
		moveCommand.execute();
		
		//player is in the correct cell
		assertEquals(env.getLifeForm(2, 4).getName(), "bob");
		
		//creates a move command that wants to move one more space to the right
		moveCommand = new MoveCommand(1);
		moveCommand.execute();
		
		//does not go out of bounds
		assertEquals(env.getLifeForm(2, 4).getName(), "bob");
		
	}

}
