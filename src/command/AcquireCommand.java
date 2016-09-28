package command;


import weapon.Weapon;

import java.awt.Point;

import lifeform.LifeForm;
import environment.Environment;

/**
 * @author Emmanuel
 * makes the player able to pick up a weapon from a cell
 */
public class AcquireCommand implements Command {
	private Environment env;
	private LifeForm lifeform;
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 */
	public AcquireCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	public AcquireCommand(LifeForm lifeform)
	{
		this.env = Environment.getInstance(0, 0);
		this.lifeform = lifeform;
		
	}
	
	private LifeForm getLifeform() {
		return this.lifeform;
	}

	public void execute()
	{
		//gets the row and col of the player
		Point pos = env.findLifeForm(getLifeform());
		int row = (int) pos.getX();
		int col = (int) pos.getY();
		
		//if the player doesn't have a weapon and wants to acquire one
		//he should pick up a weapon from the cell
		if(getLifeform().hasWeapon() == false)
		{
			//if there is a weapon in the first weapon slot of the cell, pick it up
			if(env.getWeapon(row, col, 1) != null)
			{
				getLifeform().setWeapon(env.getWeapon(row, col, 1));
				env.addWeapon(null, row, col);
			}
			//if there is a weapon in the second weapon slot of the cell, pick it up
			else if(env.getWeapon(row, col, 2) != null)
			{	
				getLifeform().setWeapon(env.getWeapon(row, col, 2));
				env.setWeaponTwo(row, col, null);	
			}
		}
		
		//if the player is already holding a weapon and wants to acquire one,
		//he should swap it with a weapon from the cell
		else
		{
			//if there is a weapon in the first slot, swap it with the player's weapon
			if(env.getWeapon(row, col, 1) != null)
			{
				Weapon playersWeapon = getLifeform().getWeapon();
				Weapon envWeapon = env.getWeapon(row, col, 1);
				
				env.addWeapon(null, row, col);
				env.addWeapon(playersWeapon, row, col);
				
				getLifeform().setWeapon(envWeapon);
			}
			//if there is a weapon in the second slot, swap it with the player's weapon
			else if(env.getWeapon(row, col, 2) != null)
			{
				Weapon playersWeapon = getLifeform().getWeapon();
				Weapon envWeapon = env.getWeapon(row, col, 2);
			 	
				env.setWeaponTwo(row, col, playersWeapon);	
				getLifeform().setWeapon(envWeapon);
			}
		}
		
	}
}
