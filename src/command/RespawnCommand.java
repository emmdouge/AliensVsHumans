package command;

import lifeform.Alien;
import lifeform.LifeForm;
import environment.Environment;

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
	private LifeForm lifeform;
	
	public RespawnCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	public RespawnCommand(LifeForm lifeform)
	{
		this.env = Environment.getInstance(0, 0);
		this.lifeform = lifeform;
	}
	
	@Override
	public void execute() {
		int lifeformX = (int)env.findLifeForm(this.lifeform).getX();
		int lifeformY = (int)env.findLifeForm(this.lifeform).getY();
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

		env.addLifeForm(this.lifeform, (int)randomRespawnXY.getX(), (int)randomRespawnXY.getY());
		env.removeLifeForm(lifeformX, lifeformY);

		lifeform.setHealth(lifeform.getMaxLifePoints());
		env.removeLifeForm(lifeformX, lifeformY);
		this.lifeform = env.getLifeForm((int)randomRespawnXY.getX(), (int)randomRespawnXY.getY());
		Point randomWeaponXY = availableWeaponSlots[(int)(Math.random()*b)];
		int row = (int) randomWeaponXY.getX();
		int col = (int) randomWeaponXY.getY();
		
		if(this.lifeform.hasWeapon() == true)
		{
			//if there is nothing in the first weapon slot, drop the weapon there
			if(env.getWeapon(row, col, 1) == null)
			{
				Weapon weaponToBeDropped = this.lifeform.getWeapon();
				this.lifeform.setWeapon(null);
				env.addWeapon(weaponToBeDropped, row, col);
			}
			//if there is nothing in the second weapon slot, drop the weapon there
			else if(env.getWeapon(row, col, 2) == null)
			{
				Weapon weaponToBeDropped = env.getLifeForm(row, col).getWeapon();
				this.lifeform.setWeapon(null);
				env.addWeapon(weaponToBeDropped, row, col);
			}

		}
		
	}
	
	public LifeForm getLifeform() {
		return this.lifeform;
	}

}
