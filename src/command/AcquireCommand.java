package command;


import weapon.Weapon;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import lifeform.Lifeform;
import environment.Environment;
import graphics.GUI;

/**
 * @author Emmanuel
 * makes the player able to pick up a weapon from a cell
 */
public class AcquireCommand extends AbstractAction implements Command {
	private Environment env;
	private Lifeform lifeform;
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 */
	public AcquireCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	public AcquireCommand(Lifeform lifeform)
	{
		this.env = Environment.getInstance();
		this.lifeform = lifeform;
		
	}
	
	private Lifeform getLifeform() {
		return lifeform;
	}

	public void execute()
	{
		//gets the row and col of the player
		Point pos = env.findLifeform(lifeform);
		int row = (int) pos.getX();
		int col = (int) pos.getY();
		
		//if the player doesn't have a weapon and wants to acquire one
		//he should pick up a weapon from the cell
		if(getLifeform().hasWeapon() == false)
		{
			//if there is a weapon in the first weapon slot of the cell, pick it up
			if(env.getWeapon(row, col, 1) != null)
			{
				lifeform.setWeapon(env.getWeapon(row, col, 1));
				env.addWeapon(null, row, col);
			}
			//if there is a weapon in the second weapon slot of the cell, pick it up
			else if(env.getWeapon(row, col, 2) != null)
			{	
				lifeform.setWeapon(env.getWeapon(row, col, 2));
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
				Weapon playersWeapon = lifeform.getWeapon();
				Weapon envWeapon = env.getWeapon(row, col, 1);
				
				env.addWeapon(null, row, col);
				env.addWeapon(playersWeapon, row, col);
				
				lifeform.setWeapon(envWeapon);
			}
			//if there is a weapon in the second slot, swap it with the player's weapon
			else if(env.getWeapon(row, col, 2) != null)
			{
				Weapon playersWeapon = lifeform.getWeapon();
				Weapon envWeapon = env.getWeapon(row, col, 2);
			 	
				env.setWeaponTwo(row, col, playersWeapon);	
				lifeform.setWeapon(envWeapon);
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		execute();
		GUI.getInstance().redraw();
	}
}
