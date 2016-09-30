package command;

import static org.junit.Assert.*;
import lifeform.Alien;
import lifeform.Human;
import lifeform.Lifeform;

import org.junit.Test;

import environment.Environment;
import exceptions.RecoveryRateException;

public class TestMoveCommandSouth {

	/**
	 * Tests the MoveCommand on the player
	 * should move the player lifeform down because of it is facing south
	 * while skipping aliens and not going out of bounds
	 * @throws RecoveryRateException
	 */
	@Test
	public void test() throws RecoveryRateException {
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		Lifeform bob = new Human("bob", 50, 3);
		env.setPlayer(bob, 2, 2);
		env.getPlayer().setDirection("south");
		
		Lifeform cob = new Alien("cob", 150);
		
		//adds a lifeform directly beneath the player
		env.addLifeForm(cob, 3, 2);
		
		//create a move command that wants to move the player one space
		MoveCommand moveCommand = new MoveCommand(1);
		moveCommand.execute();
		
		//makes sure the lifeform is skipped
		assertEquals(env.getLifeForm(4, 2).getName(), "bob");
		
		//move the player one more space south
		moveCommand = new MoveCommand(1);
		moveCommand.execute();
		
		//the player does not go out of bounds
		assertEquals(env.getLifeForm(4, 2).getName(), "bob");
	}

}
