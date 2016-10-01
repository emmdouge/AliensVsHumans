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
			int randomNumber = (int) (Math.random()*2);
			System.out.println("entered else");
			if(randomNumber == 0)
			{
				System.out.println("random direction");
				ai.getLifeForm().setDirection(Direction.randomDirection());
			}
			else if(randomNumber == 1)
			{
				System.out.println("moved");
				MoveCommand command = new MoveCommand(ai.getLifeForm(), 1);
				command.execute();
			}
		}	
	}
}
