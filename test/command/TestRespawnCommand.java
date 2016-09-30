package command;

import static org.junit.Assert.*;

import java.awt.Point;

import lifeform.Alien;
import lifeform.Human;
import lifeform.Lifeform;

import org.junit.Test;

import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.Scope;
import weapon.Weapon;
import environment.Environment;
import exceptions.AttachmentException;
import exceptions.RecoveryRateException;

public class TestRespawnCommand {

	@Test
	public void testRespawnCommand() throws RecoveryRateException, AttachmentException {
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		Lifeform bob = new Human("bob", 50, 3);
		env.setPlayer(bob, 2, 2);
		
		//gives player a plasma cannon and is set to north by default
		bob.setWeapon(new Scope(new Scope(new PlasmaCannon())));
		
		Point pos = env.findLifeform(bob);
		
		assertEquals(2, (int)pos.getX());
		assertEquals(2, (int)pos.getY());
		
		Lifeform rob = new Alien("rob", 5);
		rob.setDirection("south");
		//adds enemies to the environment
		env.addLifeForm(rob, 1, 2);
		Weapon p = new Pistol();
		rob.setWeapon(p);
		rob.attack(bob, 5);
		Point oldXY = env.findLifeform(rob);
		int oldX = (int) oldXY.getX();
		int oldY = (int) oldXY.getY();	
		
		RespawnCommand respawnRob = new RespawnCommand(rob);
		
		AttackCommand attack = new AttackCommand();
		
		attack.execute();
		
		assertEquals(0, rob.getCurrentLifePoints());
		
		respawnRob.execute();
		assertNull(rob.getWeapon());
		
		Point newXY = env.findLifeform(rob);
		
		int newX = (int) newXY.getX();
		int newY = (int) newXY.getY();
		
		assertNull(env.getLifeForm(oldX, oldY));
		assertEquals("rob", env.getLifeForm(newX, newY).getName());
		assertEquals(5, env.getLifeForm(newX, newY).getMaxLifePoints());
		assertNotEquals(oldXY, newXY);	
		
		
		boolean found = false;
		Weapon respawnedWeapon = null;
		for(int i = 0; i < env.getRows(); i++)
		{
			for(int j = 0; j < env.getColumns(); j++)
			{
				if(env.getWeapon(i, j, 1) == p || env.getWeapon(i, j, 2) == p)
				{
					found = true;
					respawnedWeapon = env.getWeapon(i, j);
				}
			}
		}
		
		assertEquals(9, respawnedWeapon.getCurrentAmmo());
		assertTrue(found);
		
		respawnRob.execute();
		assertNull(rob.getWeapon());
		
		oldXY = newXY;
		
		newXY = env.findLifeform(rob);
		
		newX = (int) newXY.getX();
		newY = (int) newXY.getY();
		
		assertNull(env.getLifeForm(oldX, oldY));
		assertEquals("rob", env.getLifeForm(newX, newY).getName());
		assertEquals(5, env.getLifeForm(newX, newY).getMaxLifePoints());
		assertNotEquals(oldXY, newXY);	
		
	}

}
