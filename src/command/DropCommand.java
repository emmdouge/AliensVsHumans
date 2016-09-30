package command;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import lifeform.Lifeform;
import weapon.Weapon;
import environment.Environment;
import graphics.GUI;

/**
 * @author Emmanuel
 * drops the weapon the player has
 */
public class DropCommand extends AbstractAction implements Command {
	private Environment env;
	private Lifeform lifeform;
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 */
	public DropCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	public DropCommand(Lifeform lifeform)
	{
		this.env = Environment.getInstance();
		this.lifeform = lifeform;
	}
	
	private Lifeform getLifeform() {
		return this.lifeform;
	}
	
	public void execute()
	{
		Point pos = env.findLifeform(getLifeform());
		int row = (int) pos.getX();
		int col = (int) pos.getY();
		
		//if the player has a weapon and wants to drop it
		if(getLifeform().hasWeapon() == true)
		{
			//if there is nothing in the first weapon slot, drop the weapon there
			if(env.getWeapon(row, col, 1) == null)
			{
				Weapon weaponToBeDropped = getLifeform().getWeapon();
				getLifeform().setWeapon(null);
				env.addWeapon(weaponToBeDropped, row, col);
			}
			//if there is nothing in the second weapon slot, drop the weapon there
			else if(env.getWeapon(row, col, 2) == null)
			{
				Weapon weaponToBeDropped = env.getLifeForm(row, col).getWeapon();
				getLifeform().setWeapon(null);
				env.addWeapon(weaponToBeDropped, row, col);
			}

		}
		
		//no spaces left in cell, player doesn't drop anything
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		execute();
		GUI.getInstance().redraw();
	}
}
