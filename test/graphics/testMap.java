package graphics;

import static org.junit.Assert.*;

import javax.swing.JOptionPane;

import lifeform.Human;
import lifeform.Alien;
import lifeform.LifeForm;

import org.junit.Test;

import recovery.RecoveryLinear;
import weapon.ChainGun;
import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.PowerBooster;
import weapon.Scope;
import weapon.Stabilizer;
import environment.Environment;
import exceptions.AttachmentException;
import exceptions.RecoveryRateException;
import exceptions.WeaponException;
/**
 * @author Chris Kjeldgaard
 */
public class testMap {
	
	/**
	 * Tests the lifeforms and their ability to change directions
	 * @throws RecoveryRateException 
	 */
	@Test
	public void testDirection() throws RecoveryRateException
	{
		Environment map = Environment.getInstance(5,5);
		map.clearBoard();
		map.addLifeForm(new Human("Billy", 8, 5), 0, 0);
		map.addLifeForm(new Alien("Al", 3), 0, 1);
		
		map.getLifeForm(0,0).setDirection("north");
		map.getLifeForm(0, 1).setDirection("north");

		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Is there one human and one alien in the upper left?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Are they both facing north?"));
		
		map.getLifeForm(0,0).setDirection("south");
		map.getLifeForm(0, 1).setDirection("south");
		map.reDraw();
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Are they both facing south?"));
		map.getLifeForm(0,0).setDirection("east");
		map.getLifeForm(0, 1).setDirection("east");
		map.reDraw();
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Are they both facing east?"));
		map.getLifeForm(0,0).setDirection("west");
		map.getLifeForm(0, 1).setDirection("west");
		map.reDraw();
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Are they both facing west?"));
	}
	
	/**
	 * Test that weapons display when carried by lifeform
	 */
	@Test
	public void testLifeFormWeapon()
	{
		Environment map = Environment.getInstance(5,5);
		map.clearBoard();
		map.addLifeForm(new Human("Sam", 6, 76), 0, 0);
		map.getLifeForm(0, 0).pickUpWeapon(new Pistol());
		map.reDraw();
		
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Cell 0,0 contains a Human"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "This Human has a Pistol"));
	}
	

	/**
	 * test the weapons color and the shape of weapons
	 * @throws AttachmentException
	 * @throws WeaponException 
	 */
	@Test
	public void testWeapons() throws AttachmentException, WeaponException
	{
		Environment map = Environment.getInstance(5,5);
		map.clearBoard();
		
		map.addWeapon(new Pistol(), 0, 0);
		map.addWeapon(new ChainGun(), 0, 0);
		map.addWeapon(new PlasmaCannon(), 0, 0);//call that is suposed to fail and not cause a third weapon to display.
		
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Does the cell 0,0 have a Pistol and a ChainGun?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The pistol is in slot 1 and the ChainGun is in slot 2, can you see both?"));
		
		map.addWeapon(new PlasmaCannon(), 0, 1);
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Does the cell 0,1 have a Plasma Cannon?"));
	}
	
	/**
	 * checks that a map is created and apears in the display
	 */
	@Test
	public void testInitialization ()
	{
		Environment map = Environment.getInstance(5,5);
		map.clearBoard();
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Does the map Apear?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Does the legend Apear?"));
	}
	
	/**
	 * BONUS TEST
	 * Checks that a map can be made with multiple cells and they are located correctly.
	 */
	@Test
	public void testSize()
	{
		Environment map = Environment.getInstance(5,5);
		map.clearBoard();
		map.addLifeForm(new Human("Bob", 5, 5), 2, 2);
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Is the map a " + map.getRows() +"x" + map.getColumns() + " grid?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "In each cell is the coordinates displayed?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Is there a human LifeForm in the cell 2,2?"));
	}
	
	/**
	 * BONUS TEST
	 * Testing to make sure each weapon looks different.
	 */
	@Test
	public void testWeaponsDifferent()
	{
		Environment map = Environment.getInstance(5,5);
		map.clearBoard();
		map.addWeapon(new Pistol(), 0, 0);
		map.addWeapon(new ChainGun(), 1, 0);
		map.addWeapon(new PlasmaCannon(), 0, 1);
		
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Cell 1,0 has a Plasma Cannon"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Cell 0,1 has a ChainGun"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Cell 0,0 has a Pistol"));
	}
	
	/**
	 * BONUS TEST
	 * Testing to make sure each weapon a LifeForm carries looks different
	 */
	@Test
	public void testLifeFormsWeaponsDifferent()
	{
		Environment map = Environment.getInstance(5,5);
		map.clearBoard();
		LifeForm huma = new Human("Bob", 6,6);
		LifeForm hum = new Human("Sally", 5,5);
		LifeForm human = new Human("Karl", 1,1);
		huma.pickUpWeapon(new Pistol());
		hum.pickUpWeapon(new ChainGun());
		human.pickUpWeapon(new PlasmaCannon());
		
		map.addLifeForm(huma, 0, 0);
		map.addLifeForm(hum, 1, 0);
		map.addLifeForm(human, 2, 0);
		
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Human in 0,0 has a Pistol"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Human in 0,1 has a ChainGun"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Human in 0,2 has a PlasmaCannon"));
	}
	
	/**
	 * BONUS TEST
	 * Testing to make sure the ammo status adjusts as the weapon is fired.
	 * @throws WeaponException 
	 */
	@Test
	public void testWeaponAmmo() throws WeaponException
	{
		Environment map = Environment.getInstance(5,5);
		map.clearBoard();
		map.addWeapon(new Pistol(), 0, 0);
		
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "There is a pistol in the cell 0,0"));
		map.getWeapon(0, 0).fire(0);
		map.reDraw();
		
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Pistol fired.  Has the ammo bar gone down"));
		
		for(; map.getWeapon(0, 0).getCurrentAmmo()>0;)
		{
			map.getWeapon(0, 0).fire(0);
			map.getWeapon(0, 0).updateTime(1);
		}
		map.reDraw();
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The pistol just had its clip emptied.  Is the bar entirly red?"));
		
	}
	
	/**
	 * BONUS TEST
	 * test to make sure lifePoints as displayed.
	 * @throws RecoveryRateException 
	 */
	@Test
	public void testRemainingLife() throws RecoveryRateException
	{
		LifeForm heal = new Alien("Heal", 50, new RecoveryLinear(10),1);
		Environment map = Environment.getInstance(5,5);
		map.clearBoard();
		map.addLifeForm(heal, 0, 0);
		heal.takeHit(10);
		map.reDraw();
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Is the alien in 0,0 at 40 lifePoints?"));
		heal.takeHit(15);
		map.reDraw();
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Is the alien in 0,0 at 25 lifePoints?"));
	}
	
	/**
	 * BONUS TEST
	 * test that attachments are color currently
	 * @throws AttachmentException 
	 */
	@Test
	public void testAttachmentColor() throws AttachmentException
	{
		Environment map = Environment.getInstance(5,5);
		map.clearBoard();
		map.addWeapon(new Pistol(), 0, 0);
		map.addWeapon(new Scope(map.getWeapon(0, 0)), 1, 0);
		map.addWeapon(new Stabilizer(map.getWeapon(0, 0)), 2, 0);
		map.addWeapon(new PowerBooster(map.getWeapon(0, 0)), 3, 0);
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 0,0 has no attachments is it the correct color?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 0,1 has a Scope is it the correct color?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 0,2 has a Stabilizer is it the correct color?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 0,3 has a Powerbooster is it the correct color?"));
		
		map.addWeapon(new Scope(new Pistol()), 0, 1);
		map.addWeapon(new Scope(map.getWeapon(0, 1)), 1, 1);
		map.addWeapon(new Stabilizer(map.getWeapon(0, 1)), 2, 1);
		map.addWeapon(new PowerBooster(map.getWeapon(0, 1)), 3, 1);
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 1,0 has a Scope is it the correct color?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 1,1 has a Scope with a Scope is it the correct color?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 1,2 has a Stabilizer with a Scope is it the correct color?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 1,3 has a Powerbooster with a Scope is it the correct color?"));
		
		map.addWeapon(new Stabilizer(new Pistol()), 0, 2);
		map.addWeapon(new Scope(map.getWeapon(0, 2)), 1, 2);
		map.addWeapon(new Stabilizer(map.getWeapon(0, 2)), 2, 2);
		map.addWeapon(new PowerBooster(map.getWeapon(0, 2)), 3, 2);
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 2,0 has a Stabilizer is it the correct color?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 2,1 has a Scope with a Stabilizer is it the correct color?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 2,2 has a Stabilizer with a Stabilizer is it the correct color?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 2,3 has a Powerbooster with a Stabilizer is it the correct color?"));
		
		map.addWeapon(new PowerBooster(new Pistol()), 0, 3);
		map.addWeapon(new Scope(map.getWeapon(0, 3)), 1, 3);
		map.addWeapon(new Stabilizer(map.getWeapon(0, 3)), 2, 3);
		map.addWeapon(new PowerBooster(map.getWeapon(0, 3)), 3, 3);
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 3,0 has a PowerBooster is it the correct color?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 3,1 has a Scope with a PowerBooster is it the correct color?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 3,2 has a Stabilizer with a PowerBooster is it the correct color?"));
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "The Weapon in 3,3 has a Powerbooster with a PowerBooster is it the correct color?"));
	}
}