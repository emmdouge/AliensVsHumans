package command;

import static org.junit.Assert.*;

import java.awt.Point;

import lifeform.Alien;
import lifeform.Human;
import lifeform.LifeForm;

import org.junit.Test;

import weapon.PlasmaCannon;
import weapon.Scope;
import environment.Environment;
import exceptions.AttachmentException;
import exceptions.RecoveryRateException;

public class TestAttackCommandSouth {

	@Test
	public void test() throws RecoveryRateException, AttachmentException {
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		LifeForm bob = new Human("bob", 50, 3);
		env.addPlayer(bob, 2, 2);
		
		//gives player a plasma cannon and turns west
		bob.setWeapon(new PlasmaCannon());
		bob.setDirection("south");
		Point pos = env.findLifeForm(bob);
		
		assertEquals(2, (int)pos.getX());
		assertEquals(2, (int)pos.getY());
		
		LifeForm rob = new Alien("rob", 150);
		LifeForm cob = new Alien("cob", 150);
		
		//adds enemies to the environment
		env.addLifeForm(rob, 3, 2);
		env.addLifeForm(cob, 4, 2);
		
		//creates the attack command and since the player has a weapon, 
		//it should attack the closest alien in the direction it's facing
		AttackCommand attackCommand = new AttackCommand();
		attackCommand.execute();
		
		//rob, the closest lifeform, loses lifepoints, and cob doesn't
		assertEquals(100, rob.getCurrentLifePoints());
		assertEquals(150, cob.getCurrentLifePoints());
		
		//removes rob, making cob the closest lifeform by default
		env.removeLifeForm(3, 2);
		
		//changed bob's weapon to reach cob
		bob.setWeapon(new Scope(new Scope(new PlasmaCannon())));
		
		//attacks closest lifeform, which is now cob
		attackCommand.execute();
		
		//cob loses lifepoints
		assertEquals(51, cob.getCurrentLifePoints());
		
		//the player no longer has a weapon
		bob.setWeapon(null);
		
		//rob is added back to environment
		env.addLifeForm(rob, 3, 2);
		
		//since the player is weaponless, should attack the lifeform directly
		//beneath it with its base damage
		attackCommand.execute();
		
		//rob loses lifepoints with base damage of the player
		assertEquals(95, rob.getCurrentLifePoints());
		
	}

}
