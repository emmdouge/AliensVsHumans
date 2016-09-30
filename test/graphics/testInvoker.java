package graphics;

import static org.junit.Assert.*;

import java.awt.Point;

import javax.swing.JOptionPane;

import lifeform.Alien;
import lifeform.Human;
import lifeform.Lifeform;

import org.junit.Test;

import weapon.ChainGun;
import weapon.Pistol;
import environment.Environment;
import exceptions.RecoveryRateException;

/**
 * Tests the overall functionality of the invoker UI
 * @author Cassia Hulshizer
 *
 */
public class testInvoker 
{

	@Test
	public void testFunctionality() throws RecoveryRateException 
	{
		Environment env = Environment.getInstance(4, 4);
		env.clearBoard();
		
		Lifeform entity = new Human("Bob", 1, 1);
		env.setPlayer(entity, 0, 0);
		//this should work since the default direction is north
		assertEquals("north", entity.getDirection());
		
		assertEquals(JOptionPane.YES_OPTION, JOptionPane.showConfirmDialog(null, 
				"6 buttons, multi-colored, visible at bottom of Game UI\n"
				+ "Does it show up correctly?", "Invoker Panel", JOptionPane.YES_NO_OPTION));
	
		/**
		 * Testing turn functionality		
		 */
		
		//Turn East
		JOptionPane.showMessageDialog(null, "The turn button will be clicked.");
		InvokerPanel.btnTurn.doClick();
		assertEquals(JOptionPane.YES_OPTION, JOptionPane.showConfirmDialog(null,
				"Does the turn option dialog show up?", "Turn Dialog", JOptionPane.YES_NO_OPTION));
		JOptionPane.showMessageDialog(null, "The east button will be clicked.");
		TurnDirections.btnEast.doClick();
		assertEquals(JOptionPane.YES_OPTION, JOptionPane.showConfirmDialog(null, 
				"Does the turn option dialog disappear?"));
		
		env.reDraw();//allows the change in direction to be seen
		assertEquals("east", entity.getDirection());
		
		//Turn South
		JOptionPane.showMessageDialog(null, "The turn button will be clicked.");
		InvokerPanel.btnTurn.doClick();
		JOptionPane.showMessageDialog(null, "The south button will be clicked.");
		TurnDirections.btnSouth.doClick();
		env.reDraw();//allows the change in direction to be seen
		assertEquals("south", entity.getDirection());
		
		//Turn West
		JOptionPane.showMessageDialog(null, "The turn button will be clicked.");
		InvokerPanel.btnTurn.doClick();
		JOptionPane.showMessageDialog(null, "The west button will be clicked.");
		TurnDirections.btnWest.doClick();
		env.reDraw();//allows the change in direction to be seen
		assertEquals("west", entity.getDirection());
		
		//Turn North
		JOptionPane.showMessageDialog(null, "The turn button will be clicked.");
		InvokerPanel.btnTurn.doClick();
		JOptionPane.showMessageDialog(null, "The north button will be clicked.");
		TurnDirections.btnNorth.doClick();
		env.reDraw();//allows the change in direction to be seen
		assertEquals("north", entity.getDirection());
		
		/**
		 * Testing acquire functionality
		 */
		env.addWeapon(new Pistol(), 0, 0);//adding a weapon to the cell in order to acquire
		env.reDraw();//allows the cell to now visibly hold a weapon
		
		JOptionPane.showMessageDialog(null, "The acquire button will be clicked.");
		InvokerPanel.btnAcquire.doClick();
		env.reDraw();//allows the human to now visible hold the weapon & the cell should not have it
		assertTrue(entity.hasWeapon());
		
		/**
		 * Testing drop functionality
		 */
		JOptionPane.showMessageDialog(null, "The drop button will be clicked.");
		InvokerPanel.btnDrop.doClick();
		env.reDraw();//allows the cell to now visibly hold the weapon again
		assertFalse(entity.hasWeapon());
		
		/**
		 * Testing move functionality
		 */
		//Move one space right
		entity.setDirection("east");//sets facing direction of the human to east
		env.reDraw();
		assertEquals("east", entity.getDirection());
		JOptionPane.showMessageDialog(null, "The move button will be clicked.\nWhen prompted, please input 1.");
		InvokerPanel.btnMove.doClick();
				
		env.reDraw();//shows the movement of the human on the map
		assertEquals(new Point(0,1), env.findLifeform(entity));
		
		//Move 3 spaces down
		entity.setDirection("south");//sets the facing direction of the human to south
		env.reDraw();
		assertEquals("south", entity.getDirection());
		JOptionPane.showMessageDialog(null, "The move button will be clicked.\nWhen prompted, please input 3.");
		InvokerPanel.btnMove.doClick();
				
		env.reDraw();//shows the movement of the human on the map
		assertEquals(new Point(3, 1), env.findLifeform(entity));
		
		//Move 2 spaces up
		entity.setDirection("north");//sets the facing direction of the human to north
		env.reDraw();
		assertEquals("north", entity.getDirection());
		JOptionPane.showMessageDialog(null,  "The move button will be clicked.\nWhen prompted, please input 2.");
		InvokerPanel.btnMove.doClick();
				
		env.reDraw();//shows the movement of the human on the map
		assertEquals(new Point(1, 1), env.findLifeform(entity));
		
		/**
		 * Testing attack functionality
		 */
		env.addWeapon(new ChainGun(), 1, 1);//adds a weapon to the cell for the human to acquire in order to attack
		InvokerPanel.btnAcquire.doClick();
		
		Lifeform entity2 = new Alien("Jabba", 40);
		env.addLifeForm(entity2, 0, 1);
		env.reDraw();//shows the presence of an enemy of the map
		
		JOptionPane.showMessageDialog(null, "The attack button will be clicked.");
		InvokerPanel.btnAttack.doClick();
		env.reDraw();
		assertEquals(35, entity2.getCurrentLifePoints());
		
		/**
		 * Testing reload functionality
		 */
		JOptionPane.showMessageDialog(null, "The reload button will be clicked.");
		InvokerPanel.btnReload.doClick();
		env.reDraw();
		assertEquals(40, entity.getWeapon().getCurrentAmmo());
	}

}
