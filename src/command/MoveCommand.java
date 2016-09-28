package command;

import lifeform.LifeForm;
import environment.Environment;

/**
 * @author Emmanuel
 * moves the players x amount of spaces
 */
public class MoveCommand implements Command 
{
	private Environment env;
	private int distance;
	int distanceTraveled = 0;
	private LifeForm lifeform;
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 * 
	 * @param distance distance you want to travel
	 */
	public MoveCommand(LifeForm lifeform, int distance)
	{
		this.env = Environment.getInstance();
		this.distance = distance;
		this.lifeform = lifeform;
		if(distance > this.lifeform.getMaxSpeed())
		{
			distance = this.lifeform.getMaxSpeed();
		}
	}
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 * 
	 * @param distance distance you want to travel
	 */
	public MoveCommand(int distance)
	{
		this(Environment.getInstance().getPlayer(), distance);
	}
	
	public void execute()
	{
		//move the player in the direction he is facing
		//based on the value passed into the constructor of the MoveCommand
		move(getLifeform().getDirection());
	}


	private LifeForm getLifeform() {
		return this.lifeform;
	}

	private void move(String direction) 
	{
		//if player is facing north
		if(direction == "north")
		{
			int i = (int) env.findLifeForm(getLifeform()).getX();
			int j = (int) env.findLifeForm(getLifeform()).getY();
			
			//keep moving until distance requirement is met
			while(distance != distanceTraveled)
			{
				//out of bounds condition
				if(i <= 0)
				{
					distanceTraveled = distance;
					return;
				}
				//move up
				i--;
				
				//if no lifeform is occupying that space, move the player there
				//and increment distanceTraveled
				if(env.getLifeForm(i, j) == null)
				{
					int a = (int) env.findLifeForm(getLifeform()).getX();
					int b = (int) env.findLifeForm(getLifeform()).getY();
					env.addLifeForm(getLifeform(), i, j);
					env.removeLifeForm(a, b);
					distanceTraveled++;	
				}

			}
			
		}
		
		else if(direction == "south")
		{
			int i = (int) env.findLifeForm(getLifeform()).getX();
			int j = (int) env.findLifeForm(getLifeform()).getY();
			
			while(distance != distanceTraveled)
			{
				//out of bounds condition
				if(i >= env.getRows()-1)
				{
					distanceTraveled = distance;
					return;
				}
				//move down
				i++;
				
				//if no lifeform is occupying that space, move the player there
				//and increment distanceTraveled
				if(env.getLifeForm(i, j) == null)
				{
					int a = (int) env.findLifeForm(getLifeform()).getX();
					int b = (int) env.findLifeForm(getLifeform()).getY();
					env.addLifeForm(getLifeform(), i, j);
					env.removeLifeForm(a, b);
					distanceTraveled++;	
				}

			}			
			
		}
		
		else if(direction == "east")
		{
			int i = (int) env.findLifeForm(getLifeform()).getX();
			int j = (int) env.findLifeForm(getLifeform()).getY();
			
			while(distance != distanceTraveled)
			{
				//out of bounds condition
				if(j >= env.getColumns()-1)
				{
					distanceTraveled = distance;
					return;
				}
				//move right
				j++;
				
				//if no lifeform is occupying that space, move the player there
				//and increment distanceTraveled
				if(env.getLifeForm(i, j) == null)
				{
					int a = (int) env.findLifeForm(getLifeform()).getX();
					int b = (int) env.findLifeForm(getLifeform()).getY();
					env.addLifeForm(getLifeform(), i, j);
					env.removeLifeForm(a, b);
					distanceTraveled++;	
				}

			}
			
		}	
		
		else if(direction == "west")
		{
			int i = (int) env.findLifeForm(getLifeform()).getX();
			int j = (int) env.findLifeForm(getLifeform()).getY();
			
			while(distance != distanceTraveled)
			{
				//out of bounds condition
				if(j <= 0)
				{
					distanceTraveled = distance;
					return;
				}
				//move left
				j--;
				
				//if no lifeform is occupying that space, move the player there
				//and increment distanceTraveled
				if(env.getLifeForm(i, j) == null)
				{
					int a = (int) env.findLifeForm(getLifeform()).getX();
					int b = (int) env.findLifeForm(getLifeform()).getY();
					env.addLifeForm(getLifeform(), i, j);
					env.removeLifeForm(a, b);
					distanceTraveled++;	
				}

			}
		}
		distanceTraveled = 0;
	}
}
