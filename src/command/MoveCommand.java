package command;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import lifeform.Direction;
import lifeform.Lifeform;
import environment.Environment;
import graphics.GUI;

/**
 * @author Emmanuel
 * moves the players x amount of spaces
 */
public class MoveCommand extends AbstractAction implements Command 
{
	private Environment env;
	private int distance;
	int distanceTraveled = 0;
	private Lifeform lifeform;
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 * 
	 * @param distance distance you want to travel
	 */
	public MoveCommand(Lifeform lifeform, int distance)
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
		move(lifeform.getDirection());
	}


	private Lifeform getLifeform() {
		return lifeform;
	}

	private void move(Direction direction) 
	{
		//if player is facing north
		if(direction == Direction.NORTH)
		{
			int i = (int) env.findLifeform(lifeform).getX();
			int j = (int) env.findLifeform(lifeform).getY();
			
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
					int a = (int) env.findLifeform(lifeform).getX();
					int b = (int) env.findLifeform(lifeform).getY();
					env.addLifeForm(lifeform, i, j);
					env.removeLifeForm(a, b);
					distanceTraveled++;	
				}

			}
			
		}
		
		else if(direction == Direction.SOUTH)
		{
			int i = (int) env.findLifeform(lifeform).getX();
			int j = (int) env.findLifeform(lifeform).getY();
			
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
					int a = (int) env.findLifeform(lifeform).getX();
					int b = (int) env.findLifeform(lifeform).getY();
					env.addLifeForm(getLifeform(), i, j);
					env.removeLifeForm(a, b);
					distanceTraveled++;	
				}

			}			
			
		}
		
		else if(direction == Direction.EAST)
		{
			int i = (int) env.findLifeform(lifeform).getX();
			int j = (int) env.findLifeform(lifeform).getY();
			
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
					int a = (int) env.findLifeform(lifeform).getX();
					int b = (int) env.findLifeform(lifeform).getY();
					env.addLifeForm(lifeform, i, j);
					env.removeLifeForm(a, b);
					distanceTraveled++;	
				}

			}
			
		}	
		
		else if(direction == Direction.WEST)
		{
			int i = (int) env.findLifeform(lifeform).getX();
			int j = (int) env.findLifeform(lifeform).getY();
			
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
					int a = (int) env.findLifeform(lifeform).getX();
					int b = (int) env.findLifeform(lifeform).getY();
					env.addLifeForm(lifeform, i, j);
					env.removeLifeForm(a, b);
					distanceTraveled++;	
				}

			}
		}
		distanceTraveled = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		move(env.getPlayer().getDirection());
		GUI.getInstance().draw();
	}
}
