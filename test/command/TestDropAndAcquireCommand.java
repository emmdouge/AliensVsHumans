package command;

import static org.junit.Assert.*;
import lifeform.Human;
import lifeform.Lifeform;

import org.junit.Test;

import weapon.ChainGun;
import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.Weapon;
import environment.Environment;

public class TestDropAndAcquireCommand {

	@Test
	public void testDropAndAcquireCommand() {
		
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		Weapon p = new Pistol();
		Lifeform bob = new Human("bob", 50, 3);
		env.setPlayer(bob, 1, 1);
		
		//gives player a pistol
		bob.setWeapon(p);
		
		//makes sure the player actually has the pistol
		assertEquals(p, bob.getWeapon()); 
		
		//creates a dropCommand and since the first weapon slot of the cell
		//is null, the weapon should be dropped in the first slot
		DropCommand dropCommand = new DropCommand();
		dropCommand.execute();
		
		//player has no weapon
		assertNull(bob.getWeapon());
		//player's weapon is in the first weapon slot of cell
		assertEquals(p, env.getWeapon(1, 1, 1));
		//the second weapon slot should still have nothing in it
		assertNull(env.getWeapon(1, 1, 2));

		Weapon c = new ChainGun();
		
		//give the player a chain gun
		bob.setWeapon(c);
		
		//makes sure the player has the chain gun
		assertEquals(c, bob.getWeapon()); 

		//creates a dropCommand and since the first weapon slot of the cell
		//is filled, the weapon should be dropped into the second slot
		dropCommand.execute();
		
		//player has no weapon
		assertNull(bob.getWeapon());
		//previous weapon dropped still in the first weapon slot
		assertEquals(p, env.getWeapon(1, 1, 1));
		//player's weapon is in the first weapon slot of cell
		assertEquals(c, env.getWeapon(1, 1, 2));
		
		Weapon pc = new PlasmaCannon();
		
		//gives the player a plasma cannon
		bob.setWeapon(pc);
		
		//makes sure the player gets the plasma cannon
		assertEquals(pc, bob.getWeapon()); 

		dropCommand.execute();
		
		//nothing changes, weaponslots in cell full
		assertEquals(pc, bob.getWeapon());
		assertEquals(p, env.getWeapon(1, 1, 1));
		assertEquals(c, env.getWeapon(1, 1, 2));
		
		//makes the player hold no weapon
		bob.setWeapon(null);
		
		//creates an acquire command and since the player has no weapon and
		//the first slot of the cell has a weapon, the player should take it
		AcquireCommand acquireCommand = new AcquireCommand();
		acquireCommand.execute();
		
		//the player has the pistol dropped before
		assertEquals(p, bob.getWeapon());
		//no weapon in the first slot after being picked up
		assertNull(env.getWeapon(1, 1, 1));
		//the chain gun is still in the second slot
		assertEquals(c, env.getWeapon(1, 1, 2));
		
		//should swap the weapon the player is holding and the one left in
		//the cell
		acquireCommand.execute();
		
		assertEquals(c, bob.getWeapon());
		assertEquals(p, env.getWeapon(1, 1, 2));
		
		//clears out the cells weapons
		bob.setWeapon(null);
		acquireCommand.execute();
		bob.setWeapon(null);
		acquireCommand.execute();
		
		//makes sure there are no weapons in the cell
		assertNull(env.getWeapon(1, 1, 1));
		assertNull(env.getWeapon(1, 1, 2));
		
		//gives the player a weapon
		bob.setWeapon(p);
		
		//executes acquire command
		acquireCommand.execute();
		
		//makes sure when there is nothing to pickup, nothing happens
		assertEquals(p, bob.getWeapon());
		assertNull(env.getWeapon(1, 1, 1));
		assertNull(env.getWeapon(1, 1, 2));
		

	}
}
