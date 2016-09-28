package command;

import lifeform.LifeForm;
import environment.Environment;

/**
 * @author Emmanuel
 * attacks enemies based on if the player has a weapon or not
 */
public class AttackCommand implements Command {
	private Environment env;
	private LifeForm lifeform;

	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 */
	public AttackCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	public AttackCommand(LifeForm lifeform)
	{
		this.env = Environment.getInstance(0, 0);
		this.lifeform = lifeform;
	}
	
	private LifeForm getLifeform() {
		return this.lifeform;
	}
	
	public void execute()
	{
		LifeForm opponent;
		
		//if the player has a weapon and wants to attack
		if(getLifeform().hasWeapon() == true)
		{
			//find the closest enemy
			opponent = findClosest(getLifeform().getDirection());
			
			//if an enemy is found, attack
			if(opponent != null)
			{		
				getLifeform().attack(opponent, env.computeRange(getLifeform(), opponent));
			}
		}
		//if the player does not have a weapon and wants to attack
		else
		{
			//find the enemy that is front of the player in the direction he is facing
			opponent = findInFrontOf(getLifeform().getDirection());
			
			//if an enemy is found, attack
			if(opponent != null)
			{		
				getLifeform().attack(opponent, env.computeRange(getLifeform(), opponent));
			}
		}
	}

	private LifeForm findInFrontOf(String direction) {
		
		if(direction == "north")
		{
			int i = (int) env.findLifeForm(getLifeform()).getX();
			int j = (int) env.findLifeForm(getLifeform()).getY();
			
			if(i > 0)
			{
				i--;
				if(env.getLifeForm(i, j) != null)
				{
					return env.getLifeForm(i, j);
				}	
			}
			
		}
		
		else if(direction == "south")
		{
			int i = (int) env.findLifeForm(getLifeform()).getX();
			int j = (int) env.findLifeForm(getLifeform()).getY();
			
			if(i < env.getRows())
			{
				i++;
				if(env.getLifeForm(i, j) != null)
				{
					return env.getLifeForm(i, j);
				}	
			}			
			
		}
		
		else if(direction == "east")
		{
			int i = (int) env.findLifeForm(getLifeform()).getX();
			int j = (int) env.findLifeForm(getLifeform()).getY();
			
			if(j < env.getColumns())
			{
				j++;
				if(env.getLifeForm(i, j) != null)
				{
					return env.getLifeForm(i, j);
				}	
			}
			
		}	
		
		else if(direction == "west")
		{
			int i = (int) env.findLifeForm(getLifeform()).getX();
			int j = (int) env.findLifeForm(getLifeform()).getY();
			
			if(j > 0)
			{
				j--;
				if(env.getLifeForm(i, j) != null)
				{
					return env.getLifeForm(i, j);
				}	
			}
		}
		
		return null;
	}
	
	private LifeForm findClosest(String direction) {

		if(direction == "north")
		{
			int i = (int) env.findLifeForm(getLifeform()).getX();
			int j = (int) env.findLifeForm(getLifeform()).getY();
			
			while(i > 0)
			{
				i--;
				if(env.getLifeForm(i, j) != null)
				{
					return env.getLifeForm(i, j);
				}	
			}
			
		}
		
		else if(direction == "south")
		{
			int i = (int) env.findLifeForm(getLifeform()).getX();
			int j = (int) env.findLifeForm(getLifeform()).getY();
			
			while(i < env.getRows())
			{
				i++;
				if(env.getLifeForm(i, j) != null)
				{
					return env.getLifeForm(i, j);
				}	
			}			
			
		}
		
		else if(direction == "east")
		{
			int i = (int) env.findLifeForm(getLifeform()).getX();
			int j = (int) env.findLifeForm(getLifeform()).getY();
			
			while(j < env.getColumns())
			{
				j++;
				if(env.getLifeForm(i, j) != null)
				{
					return env.getLifeForm(i, j);
				}	
			}
			
		}	
		
		else if(direction == "west")
		{
			int i = (int) env.findLifeForm(getLifeform()).getX();
			int j = (int) env.findLifeForm(getLifeform()).getY();
			
			while(j > 0)
			{
				j--;
				if(env.getLifeForm(i, j) != null)
				{
					return env.getLifeForm(i, j);
				}	
			}
		}
		
		return null;
	}
}
