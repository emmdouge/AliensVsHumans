package state;

import static org.junit.Assert.*;

import java.awt.Point;

import lifeform.Alien;
import lifeform.Human;
import lifeform.Lifeform;

import org.junit.Test;

import weapon.Pistol;
import weapon.Weapon;
import environment.Environment;
import exceptions.RecoveryRateException;

/**
 * @author Brandon Shawver
 */
public class TestHasWeaponState 
{
	/**
	 * Tests to see if the state changes if the lifeform is dead.
	 */
	@Test
	public void testDeadStateChange() 
	{
		Lifeform human = new Human("Bob", 0, 50);
		AIContext ai = new AIContext(human);
		ActionState hasWeapon = new FightingState(ai);
		Weapon gun = new Pistol();
			
		human.setWeapon(gun);
			
		// Switches to dead state LifePoints are at 0
		hasWeapon.handle();
		assertEquals(ai.getCurrentState(), ai.getDeadState());
	}
	
	/**
	 * Tests to see if the life form will attack
	 * Only if it has ammo
	 * and only if its not its same type
	 * (i.e. humans can't attack humans)
	 * @throws RecoveryRateException 
	 */
	@Test
	public void testAttackTarget() throws RecoveryRateException
	{
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		
		Lifeform human = new Human("Bob", 100, 50);
		Lifeform alien = new Alien("zerg", 100);
		Lifeform civilian = new Human("civilian", 100, 0);
		
		AIContext ai = new AIContext(human);
		FightingState hasWeapon = new FightingState(ai);
		Pistol gun = new Pistol();
		
		human.setWeapon(gun);
		env.addLifeForm(human, 2, 2);
		env.addLifeForm(alien, 3, 2);
		env.addLifeForm(civilian, 2, 1);
		
		assertEquals(human, env.getLifeForm(2,2));
		assertEquals(alien, env.getLifeForm(3,2));
		
		
		// Target alien and ammo - should attack
		human.setDirection("south");
		hasWeapon.handle();
		assertEquals(alien, hasWeapon.getTarget());
		assertEquals(90, alien.getCurrentLifePoints());
		
		// Target alien and 1 shot left - should attack
		gun.setCurrentAmmo(1);
		assertEquals(1, human.getWeapon().getCurrentAmmo());
		hasWeapon.handle();
		assertEquals(alien, hasWeapon.getTarget());
		assertEquals(80, alien.getCurrentLifePoints());
		assertEquals(0, human.getWeapon().getCurrentAmmo());
		
		// Target human and ammo - shouldn't attack
		human.setDirection("west");
		hasWeapon.handle();
		assertEquals(civilian, hasWeapon.getTarget());
		assertEquals(100, civilian.getCurrentLifePoints());
	}
	
	/**
	 * Tests the to see if a lifeform with no target will move to search for a 
	 * target to attack.
	 */
	@Test
	public void testNoTarget()
	{
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		
		Human human = new Human("Bob", 100, 50);
		Pistol gun = new Pistol();
		AIContext ai = new AIContext(human);
		FightingState hasWeapon = new FightingState(ai);
		
		human.setWeapon(gun);
		env.addLifeForm(human, 2, 2);
		human.setCurrentSpeed(1);
		
		// The human will have no target so it will move.
		hasWeapon.handle();
		int temp = hasWeapon.getTemp();
		assertNull(hasWeapon.getTarget());
		assertNull(env.getLifeForm(2,2));
		if(temp == 1)
		{
			assertEquals(human, env.getLifeForm(1,2));
		}
		if(temp == 2)
		{
			assertEquals(human, env.getLifeForm(2,3));
		}
		if(temp == 3)
		{
			assertEquals(human, env.getLifeForm(3,2));
		}
		if(temp == 4)
		{
			assertEquals(human, env.getLifeForm(2,1));
		}
	}
	
	/**
	 * Tests the behavior if the target it out of range
	 * @throws RecoveryRateException 
	 */
	@Test
	public void testTargetOutOfRange() throws RecoveryRateException
	{
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		
		Human human = new Human("Bob", 100, 50);
		Lifeform alien = new Alien("zerg", 100);
		Pistol gun = new Pistol();
		AIContext ai = new AIContext(human);
		FightingState hasWeapon = new FightingState(ai);
		
		human.setWeapon(gun);
		env.addLifeForm(human, 4, 0);
		env.addLifeForm(alien, 1, 0);
		
		//Target is out of range so the lifeform should move towards its target.
		// Once in range it will attack.
		hasWeapon.handle();
		assertEquals(human, env.getLifeForm(3,0));
		assertEquals(94, alien.getCurrentLifePoints());
	}
}