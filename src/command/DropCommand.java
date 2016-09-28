package command;

import java.awt.Point;

import lifeform.LifeForm;
import weapon.Weapon;
import environment.Environment;

/**
 * @author Emmanuel
 * drops the weapon the player has
 */
public class DropCommand implements Command {
	private Environment env;
	private LifeForm lifeform;
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 */
	public DropCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	public DropCommand(LifeForm lifeform)
	{
		this.env = Environment.getInstance(0, 0);
		this.lifeform = lifeform;
	}
	
	private LifeForm getLifeform() {
		return this.lifeform;
	}
	
	public void execute()
	{
		Point pos = env.findLifeForm(getLifeform());
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
}
