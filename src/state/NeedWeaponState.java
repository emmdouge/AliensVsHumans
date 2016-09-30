package state;

import java.awt.Point;

import lifeform.Direction;
import command.AcquireCommand;
import command.MoveCommand;
import command.TurnEastCommand;
import command.TurnNorthCommand;
import command.TurnSouthCommand;
import command.TurnWestCommand;
import environment.Environment;
/**
 * 
 * @author Emmanuel Douge
 *
 */
public class NeedWeaponState extends ActionState
{
	private AIContext ai;
	private Environment env;
	
	public NeedWeaponState(AIContext ai)
	{
		this.ai = ai;
		env = Environment.getInstance();
	}
	
	public void handle()
	{
		Point position = env.findLifeform(ai.getLifeForm());
		boolean weaponFound = env.getWeapon(position.x, position.y) != null;
		boolean lifeformIsDead = ai.getLifeForm().getCurrentLifePoints() <= 0;
		
		if(lifeformIsDead)
		{
			//switch deadState
			ai.setCurrentState(ai.getDeadState());
		}
		else if(weaponFound)
		{
			//pick it up
			AcquireCommand acquire = new AcquireCommand(ai.getLifeForm());
			acquire.execute();
			
			//switch to hasWeaponState
			ai.setCurrentState(ai.getFightingState());
		}
		else
		{
			moveRandom();
		}
		
	}
	
	private void moveRandom()
	{
		MoveCommand move= new MoveCommand (ai.getLifeForm(), 1);
		int randomDirection = (int)(Math.random()*4)+1;//generates a random number between 1 and 4 to choose the random direction
		
		if(randomDirection == Direction.NORTH.ordinal())
		{
			//moves the AI north
			TurnNorthCommand turn = new TurnNorthCommand(ai.getLifeForm());
			turn.execute();
		}
		else if(randomDirection == Direction.EAST.ordinal())
		{
			//moves the AI east
			TurnEastCommand turn = new TurnEastCommand(ai.getLifeForm());
			turn.execute();
		}
		else if(randomDirection == Direction.WEST.ordinal())
		{
			//moves the AI west
			TurnWestCommand turn = new TurnWestCommand(ai.getLifeForm());
			turn.execute();
		}
		else if(randomDirection == Direction.SOUTH.ordinal())
		{
			//moves the AI south
			TurnSouthCommand turn = new TurnSouthCommand(ai.getLifeForm());
			turn.execute();
		}
		
		move.execute();
	}
}
