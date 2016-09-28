package state;

import static org.junit.Assert.*;
import environment.Environment;
import exceptions.RecoveryRateException;
import exceptions.WeaponException;
import gameplay.SimpleTimer;
import gameplay.Simulator;


import org.junit.Test;

import weapon.Pistol;
import weapon.Weapon;

/**
 * @author Emmanuel
 *Tests the outOfAmmoState with a simpleTimer
 */
public class TestOutOfAmmoState {

	/**
	 * @throws RecoveryRateException The recovery rate must be at least 0
	 * @throws InterruptedException Ammo cannot be negative
	 * @throws WeaponException Distance cannot be negative
	 */
	@Test
	public void testOutOfAmmoState() throws RecoveryRateException, InterruptedException, WeaponException {
		Environment env = Environment.getInstance(5,5);
		env.clearBoard();
		SimpleTimer st = new SimpleTimer(1000);

		Simulator sim = new Simulator(0, 1);
		Weapon p = new Pistol();
		sim.getLifeform(0).setWeapon(p);
		p.fire(5);
		p.fire(5);
		
		st.addTimeObserver(sim);
		
		st.start();
		Thread.sleep(250);
		
		int x = 0;
			
		assertEquals(x,st.getRound()); // beginning of round 0
		assertEquals(100, sim.getLifeform(0).getCurrentLifePoints());
		assertEquals(8, sim.getLifeform(0).getWeapon().getCurrentAmmo());
		Thread.sleep(1000); // end of round 0
		x++;
		
		assertEquals(x,st.getRound()); // beginning of round 1
		p.fire(5);
		p.fire(5);
		assertEquals(100, sim.getLifeform(0).getCurrentLifePoints());
		assertEquals(6, sim.getLifeform(0).getWeapon().getCurrentAmmo());
		Thread.sleep(1000); // end of round 1
		x++;
		
		assertEquals(x,st.getRound()); // beginning of round 2
		p.fire(5);
		p.fire(5);
		assertEquals(4, sim.getLifeform(0).getWeapon().getCurrentAmmo());
		Thread.sleep(1000); // end of round 2
		x++;
		
		assertEquals(x,st.getRound()); // beginning of round 3
		p.fire(5);
		p.fire(5);
		assertEquals(2, sim.getLifeform(0).getWeapon().getCurrentAmmo());
		Thread.sleep(1000); // end of round 3
		x++;
		
		assertEquals(x,st.getRound()); // beginning of round 4
		p.fire(5);
		p.fire(5);
		assertEquals(0, sim.getLifeform(0).getWeapon().getCurrentAmmo());
		//start from OutOfAmmoState
		sim.getAI(0).setCurrentState(sim.getAI(0).getOutOfAmmoState());
		Thread.sleep(1000); // end of round 4
		x++;
		
		assertEquals(x,st.getRound()); // beginning of round 5
		//makes sure it updates properly and reloads
		assertEquals(10, sim.getLifeform(0).getWeapon().getCurrentAmmo());
		Thread.sleep(1000); // end of round 5
		x++;
	}

}
