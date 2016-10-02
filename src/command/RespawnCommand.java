package command;

import lifeform.Alien;
import lifeform.Lifeform;
import environment.Environment;
import graphics.GUI;

import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;

import weapon.Weapon;

/**
 * @author Emmanuel
 *
 */
public class RespawnCommand implements Command{
	private Environment env;
	private Lifeform lifeform;
	
	public RespawnCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	public RespawnCommand(Lifeform lifeform)
	{
		this.env = Environment.getInstance();
		this.lifeform = lifeform;
	}
	
	@Override
	public void execute() 
	{
		int lifeformX = (int)env.findLifeform(lifeform).getX();
		int lifeformY = (int)env.findLifeform(lifeform).getY();
		Point[] availableRespawnSlots = new Point[env.getRows()*env.getColumns()];
		Point[] availableWeaponSlots = new Point[env.getRows()*env.getColumns()];
		int a = 0;
		int b = 0;
		for(int i = 0; i < env.getRows(); i++)
		{
			for(int j = 0; j < env.getColumns(); j++)
			{
				if(env.getLifeForm(i, j) == null)
				{
					availableRespawnSlots[a] = new Point(i, j);
					a++;
				}
				if(env.getWeapon(i, j, 1) == null || env.getWeapon(i, j, 2) == null)
				{
					availableWeaponSlots[b] = new Point(i, j);
					b++;
				}
			}
		}
		
		Point randomRespawnXY = availableRespawnSlots[(int)(Math.random()*a)];

		env.addLifeForm(lifeform, (int)randomRespawnXY.getX(), (int)randomRespawnXY.getY());
		env.removeLifeForm(lifeformX, lifeformY);

		lifeform.setHealth(lifeform.getMaxLifePoints());
		env.removeLifeForm(lifeformX, lifeformY);
		
		GUI.getInstance().draw();
		
		lifeform = env.getLifeForm((int)randomRespawnXY.getX(), (int)randomRespawnXY.getY());
		Point randomWeaponXY = availableWeaponSlots[(int)(Math.random()*b)];
		int row = (int) randomWeaponXY.getX();
		int col = (int) randomWeaponXY.getY();
		
		if(lifeform.hasWeapon() == true)
		{
			//if there is nothing in the first weapon slot, drop the weapon there
			if(env.getWeapon(row, col, 1) == null)
			{
				Weapon weaponToBeDropped = lifeform.getWeapon();
				lifeform.setWeapon(null);
				env.addWeapon(weaponToBeDropped, row, col);
			}
			//if there is nothing in the second weapon slot, drop the weapon there
			else if(env.getWeapon(row, col, 2) == null)
			{
				Weapon weaponToBeDropped = env.getLifeForm(row, col).getWeapon();
				lifeform.setWeapon(null);
				env.addWeapon(weaponToBeDropped, row, col);
			}

		}
		GUI.getInstance().draw();
	}
	
	public Lifeform getLifeform() {
		return lifeform;
	}

}
