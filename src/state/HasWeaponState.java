package state;

import java.awt.Point;
import java.util.Random;

import lifeform.LifeForm;
import environment.Environment;

/**
 * @author Brandon Shawver
 */
public class HasWeaponState extends ActionState
{
	private LifeForm lifeform;
	private LifeForm target;
	private String Direction;
	private Point point;
	private Environment map;
	private AIContext subject;
	Random generator = new Random();
	int temp = generator.nextInt(4) + 1;
		
	public HasWeaponState(AIContext ai) 
	{
		this.map = Environment.getInstance();
		subject = ai;
		lifeform = ai.getLifeForm();
	}
	
	/**
	 * Checks if LifeForm is dead if not then proceeds to look for target.
	 * If it finds a target it will attack.
	 * If no target is available it will turn a random direction
	 * then move to a new cell
	 */
	@Override
	public void handle()
	{
		if(lifeform.getCurrentLifePoints() == 0)                // if dead change states if not attack
		{
			subject.setCurrentState(subject.getDeadState());
		}
		else
		{
			Direction = lifeform.getDirection();
			point = map.findLifeForm(lifeform);
			point.getX();
			point.getY();
				
			// if facing North
			if(Direction == "north")
			{
				for(int x = (int) point.getX() - 1; x >= 0 ; x--)
				{
					if(map.getLifeForm(x, (int)point.getY()) != null && map.getLifeForm(x, (int)point.getY()) != lifeform)
					{
						target = map.getLifeForm(x, (int)point.getY());
					}
				}
			}
				
			// if facing East
			if(Direction == "east")
			{
				for(int y = (int) point.getY() + 1; y <= map.getColumns() - 1 ; y++)
				{
					if(map.getLifeForm((int)point.getX(), y) != null && map.getLifeForm((int)point.getX(), y) != lifeform) 
					{
						target = map.getLifeForm((int)point.getX(),y);
					}
				}
			}
				
			// if facing South
			if(Direction == "south")
			{
				for(int x = (int) point.getX() + 1; x <= map.getRows() - 1 ; x++)
				{
					if(map.getLifeForm(x, (int)point.getY()) != null && map.getLifeForm(x, (int)point.getY()) != lifeform)
					{
						target = map.getLifeForm(x, (int)point.getY());
					}
				}
			}
				
			// if facing West
			if(Direction == "west")
			{
				for(int y = (int) point.getY() - 1; y >= 0 ; y--)
				{
					if(map.getLifeForm((int)point.getX(), y) != null && map.getLifeForm((int)point.getX(), y) != lifeform)
					{
						target = map.getLifeForm((int)point.getX(), y);
					}
				}	
			}
				
			if(target != null)				// If there is a target attack if not search
			{
				if(lifeform.getWeapon().getCurrentAmmo() > 0)  // If has ammo then fire if not change state
				{
						
					if(!(lifeform.getClass().equals(target.getClass())))
					{
						if(lifeform.getWeapon().getMaxRange() > map.computeRange(lifeform,target))
						{
							lifeform.attack(target, map.computeRange(lifeform, target));
						}
						else
						{
							map.Move(lifeform, (map.computeRange(lifeform,target)-lifeform.getWeapon().getMaxRange())/5);
							lifeform.attack(target, map.computeRange(lifeform, target));
						}
					}
				}
				else
				{
					subject.setCurrentState(subject.getOutOfAmmoState());
				}
			}
			else
			{
				//Search for target
				if(temp == 1)
				{
					Direction = "north";
				}
				else
				{}
				
				if(temp == 2)
				{
					Direction = "east";
				}
				else
				{}
				
				if(temp == 3)
				{
					Direction = "south";
				}
				else{}
				
				if (temp == 4)
				{
					Direction = "west";
				}
				else{}
				
				lifeform.setDirection(Direction);
				map.Move(lifeform, lifeform.getCurrentSpeed());
			}
		}
	}
	
	/**
	 * @return the Lifeform of the AIContext
	 */
	public LifeForm getLifeForm()
	{
		return lifeform;
	}
	
	/**
	 * @return the target of the lifeform
	 */
	public LifeForm getTarget()
	{
		return target;
	}
	
	/**
	 * @return the randomly generated number for the searching of a target
	 */
	public int getTemp()
	{
		return temp;
	}
}

