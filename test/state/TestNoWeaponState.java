package state;

import static org.junit.Assert.*;

import java.awt.Point;

import javax.swing.JOptionPane;

import lifeform.Human;
import lifeform.Lifeform;

import org.junit.Test;

import weapon.Pistol;
import weapon.Weapon;
import environment.Environment;

/**
 * 
 * @author Chris Kjeldgaard
 *
 */
public class TestNoWeaponState 
{
	/**
	 * this test puts a single LifeForm an weapon on the map and then keeps moving the Human till it finds the weapon
	 * @throws InterruptedException
	 */
	@Test
	public void testSearch() throws InterruptedException{
		Environment map = Environment.getInstance(5, 5);
		Lifeform lf = new Human("lf",5,5);
		Weapon pistol = new Pistol();
		AIContext ai = new AIContext(lf);
		map.addLifeForm(lf, 4, 4);
		map.addWeapon(pistol, 0, 0);
		
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Is there one Human in the lower right and one pistol is the upper left?"));
		JOptionPane.showMessageDialog(null, "The Human will begin moving.");
		
		while(map.findLifeform(lf) != new Point(0,0))
		{
			ai.execute();
			Thread.sleep(50);
		}
		
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Did the human move randomly?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Did the human stop on the weapon?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Did it pick up the weapon?"));
		
		//confirmed the state changed.
		assertEquals(ai.getCurrentState(),ai.getFightingState());
		
		map.clearBoard();
	}
	
	/**
	 * this test asserts that it correctly checks for a dead LifeForm by creating a LifeForm and killing it.
	 */
	@Test
	public void testDead ()
	{
		
		Lifeform lf = new Human("lf",5,5);
		AIContext ai = new AIContext(lf);
		lf.setHealth(0);
		ai.execute();
		assertEquals(ai.getCurrentState(), ai.getDeadState());
	}

}
