package state;

import java.awt.Point;

import command.AcquireCommand;
import command.MoveCommand;
import command.TurnEastCommand;
import command.TurnNorthCommand;
import command.TurnSouthCommand;
import command.TurnWestCommand;
import environment.Environment;
/**
 * 
 * @author Chris Kjeldgaard
 *
 */
public class NoWeaponState extends ActionState
{
	private AIContext subject;
	private Environment map;
	
	public NoWeaponState(AIContext subject)
	{
		this.subject = subject;
		map = Environment.getInstance();
	}
	
	public void handle()
	{
		Point location = map.findLifeForm(subject.getLifeForm());
		
		if(subject.getLifeForm().getCurrentLifePoints() == 0)
		{
			//if the subject is dead it changes the state
			subject.setCurrentState(subject.getDeadState());
		}
		else if(map.getWeapon(location.x, location.y) != null)
		{
			//If a weapon is in the cell it picks it up.
			AcquireCommand acquire = new AcquireCommand(subject.getLifeForm());
			acquire.execute();
			subject.setCurrentState(subject.getHasWeaponState());
		}
		else
		{
			//Two different movement algorithms were made a algorithm that move the LifeForm randomly
			//and one the move the LifeForms the the closest weapon on the map
			moveRandom();
//			moveStraight(location);
		}
		
	}
	
	private void moveStraight(Point location)
	{
		Point weapon = new Point();
		MoveCommand move;
		
		//searches map for the closest weapon
		for(int a = 0; a < map.getRows(); a++)
		{
			for(int b = 0; b < map.getColumns(); b++)
			{
				if(map.getWeapon(a, b) == null)
				{
				}
				else if(distance(location.x, location.y, a, b) < distance(location.x, location.y, weapon.x, weapon.y))
				{
					weapon.x = a;
					weapon.y = b;
				}
			}
		}
		
		//moves towards the weapon if facing in the right direction
		if(subject.getLifeForm().getDirection().equals("North") && weapon.x < location.x)
		{
			move = new MoveCommand(subject.getLifeForm(), Math.abs(weapon.x-location.x));
		}
		else if(subject.getLifeForm().getDirection().equals("Sout") && weapon.x > location.x)
		{
			move = new MoveCommand(subject.getLifeForm(), Math.abs(weapon.x-location.x));
		}
		else if(subject.getLifeForm().getDirection().equals("East") && weapon.y > location.y)
		{
			move = new MoveCommand(subject.getLifeForm(), Math.abs(weapon.y-location.y));
		}
		else if(subject.getLifeForm().getDirection().equals("West") && weapon.y < location.y)
		{
			move = new MoveCommand(subject.getLifeForm(), Math.abs(weapon.y-location.y));
		}
		else
		{//if the LifeForm is not facing the correct direction is selects the direction that is facing the weapon 
			//and starts moving the LifeForm in the direction
			if(weapon.x == location.x)
			{
				if(weapon.y > location.y)
				{
					TurnEastCommand turn = new TurnEastCommand(subject.getLifeForm());
					turn.execute();
					move = new MoveCommand(subject.getLifeForm(), Math.abs(weapon.y-location.y));
				}
				else
				{
					TurnWestCommand turn = new TurnWestCommand(subject.getLifeForm());
					turn.execute();
					move = new MoveCommand(subject.getLifeForm(), Math.abs(weapon.y-location.y));
				}
			}
			else
			{
				if(weapon.x > location.x)
				{
					TurnSouthCommand turn = new TurnSouthCommand(subject.getLifeForm());
					turn.execute();
					move = new MoveCommand(subject.getLifeForm(), Math.abs(weapon.x-location.x));
				}
				else
				{
					TurnNorthCommand turn = new TurnNorthCommand(subject.getLifeForm());
					turn.execute();
					move = new MoveCommand(subject.getLifeForm(), Math.abs(weapon.x-location.x));
				}
			}
			
		}
		if(move != null)
		{
			move.execute();
		}
	}
	
	private void moveRandom()
	{
		MoveCommand move= new MoveCommand(subject.getLifeForm(), 1);
		int direction = (int)(Math.random()*4)+1;//generates a random number between 1 and 4 to choose the random direction
		
		if(direction == 1)
		{//moves the AI north
			TurnNorthCommand turn = new TurnNorthCommand(subject.getLifeForm());
			turn.execute();
		}
		else if(direction == 2)
		{//moves the AI east
			TurnEastCommand turn = new TurnEastCommand(subject.getLifeForm());
			turn.execute();
		}
		else if(direction == 3)
		{//moves the AI west
			TurnWestCommand turn = new TurnWestCommand(subject.getLifeForm());
			turn.execute();
		}
		else if(direction == 4)
		{//moves the AI south
			TurnSouthCommand turn = new TurnSouthCommand(subject.getLifeForm());
			turn.execute();
		}
		
		move.execute();
	}
	
	private double distance(int row1, int col1, int row2, int col2)
	{//used to calculate the distance between 2 spots
		return Math.sqrt(Math.pow(row1-row2,2.0)+Math.pow(col1 - col2, 2.0));
	}
}
