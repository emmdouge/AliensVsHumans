package state;

import static org.junit.Assert.*;

import java.awt.Point;

import lifeform.Alien;
import lifeform.Human;
import lifeform.LifeForm;
import environment.Environment;
import exceptions.RecoveryRateException;
import gameplay.SimpleTimer;
import gameplay.Simulator;

import org.junit.Test;

import weapon.Pistol;
import weapon.Weapon;
import command.AttackCommand;

/**
 * @author Emmanuel
 *Tests the deadState with a simpleTimer
 */
public class TestDeadState {

	/**
	 * @throws InterruptedException Threads cannot access instructions at the same time
	 * @throws RecoveryRateException recoveryRate cannot be less than zero
	 */
	@Test
	public void testWithoutWeapon() throws InterruptedException, RecoveryRateException {
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		SimpleTimer st = new SimpleTimer(1000);

		Simulator sim = new Simulator(0, 1);
		AIContext ai = new AIContext(sim.getLifeform(0));
		Point oldXY = env.findLifeForm(sim.getLifeform(0));

		assertEquals(sim.getLifeform(0), env.getLifeForm((int)oldXY.getX(), (int)oldXY.getY()));
		ai.setCurrentState(ai.getOutOfAmmoState());

		st.addTimeObserver(sim);
		
		st.start();
		Thread.sleep(250);
		
		int x = 0;
			
		assertEquals(x,st.getRound()); // beginning of round 0
		assertEquals(100, sim.getLifeform(0).getCurrentLifePoints());
		Thread.sleep(1000); // end of round 0
		x++;
		
		assertEquals(x,st.getRound()); // beginning of round 1
		ai.setCurrentState(ai.getOutOfAmmoState());
		sim.getLifeform(0).takeHit(150);
		assertEquals(0, sim.getLifeform(0).getCurrentLifePoints());
		Thread.sleep(1000); // end of round 1
		x++;
		
		assertEquals(x,st.getRound()); // beginning of round 2
		Point newXY = env.findLifeForm(sim.getLifeform(0));
		int newX = (int) newXY.getX();
		int newY = (int) newXY.getY();
		assertEquals(100, env.getLifeForm(newX, newY).getMaxLifePoints());
		Thread.sleep(1000); // end of round 2
		x++;
		
		assertEquals(x,st.getRound()); // beginning of round 3
		ai.setCurrentState(ai.getOutOfAmmoState());
		assertEquals(100, sim.getLifeform(0).getCurrentLifePoints());
		Thread.sleep(1000); // end of round 3
		x++;
		
		assertEquals(x,st.getRound()); // beginning of round 3
		ai.setCurrentState(ai.getOutOfAmmoState());
		assertEquals(100, sim.getLifeform(0).getCurrentLifePoints());
		Thread.sleep(1000); // end of round 3
		x++;
	}

	/**
	 * @throws InterruptedException Threads cannot access instructions at the same time
	 * @throws RecoveryRateException recoveryRate cannot be less than zero
	 */
	@Test
	public void testWithWeapon() throws InterruptedException, RecoveryRateException {
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		SimpleTimer st = new SimpleTimer(1000);

		Simulator sim = new Simulator(0, 1);
		Weapon p = new Pistol();
		sim.getLifeform(0).setWeapon(p);
		AIContext ai = new AIContext(sim.getLifeform(0));
		Point oldXY = env.findLifeForm(sim.getLifeform(0));

		assertEquals(sim.getLifeform(0), env.getLifeForm((int)oldXY.getX(), (int)oldXY.getY()));
		ai.setCurrentState(ai.getOutOfAmmoState());
		sim.getLifeform(0).attack(new Human("bob", 5, 0), 5);

		st.addTimeObserver(sim);
		
		st.start();
		Thread.sleep(250);
		
		int x = 0;
			
		assertEquals(x,st.getRound()); // beginning of round 0
		assertEquals(100, sim.getLifeform(0).getCurrentLifePoints());
		Thread.sleep(1000); // end of round 0
		x++;
		
		assertEquals(x,st.getRound()); // beginning of round 1
		//start at outOfAmmoState
		ai.setCurrentState(ai.getOutOfAmmoState());
		sim.getLifeform(0).takeHit(150);
		assertEquals(0, sim.getLifeform(0).getCurrentLifePoints());
		Thread.sleep(1000); // end of round 1
		x++;
		
		assertEquals(x,st.getRound()); // beginning of round 2
		Point newXY = env.findLifeForm(sim.getLifeform(0));
		int newX = (int) newXY.getX();
		int newY = (int) newXY.getY();
		//the alien in env and sim are the same
		assertEquals(100, env.getLifeForm(newX, newY).getMaxLifePoints());
		assertEquals(100, sim.getLifeform(0).getMaxLifePoints());
		Thread.sleep(1000); // end of round 2
		x++;
		
		assertEquals(x,st.getRound()); // beginning of round 3
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
		
		//dropped weapon is in env
		assertTrue(found); 
		//alien is not holding a weapon
		assertNull(sim.getLifeform(0).getWeapon());
		//ammo is left intact
		assertEquals(9, respawnedWeapon.getCurrentAmmo());
		assertEquals(100, sim.getLifeform(0).getCurrentLifePoints());
		Thread.sleep(1000); // end of round 3
		x++;
		
	}

}
