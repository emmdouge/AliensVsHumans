package command;

import static org.junit.Assert.*;
import lifeform.Human;
import lifeform.LifeForm;

import org.junit.Test;

import weapon.Pistol;
import weapon.Weapon;
import environment.Environment;

public class TestReloadCommand {
	
	/**
	 * Tests the ReloadCommand on the player
	 * Should reload the weapon the player is holding
	 */
	@Test
	public void testReloadCommand() {
		
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		Weapon p = new Pistol();
		LifeForm bob = new Human("bob", 50, 3);
		LifeForm rob = new Human("rob", 50, 3);
		env.addPlayer(bob, 1, 1);
		
		//give the lifeform a weapon
		bob.setWeapon(p);
		
		//makes sure the ammo is full
		assertEquals(10, bob.getWeapon().getCurrentAmmo()); 
		
		//attacking with weapon should decrease ammo 
		bob.attack(rob, 5);
		assertEquals(9, bob.getWeapon().getCurrentAmmo()); 
		
		//should reload the weapon the player is holding
		ReloadCommand reloadCommand = new ReloadCommand();
		reloadCommand.execute();
		
		//weapon has full ammo again
		assertEquals(10, bob.getWeapon().getCurrentAmmo()); 
	}

}
