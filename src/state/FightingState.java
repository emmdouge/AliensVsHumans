package state;

import java.awt.Point;
import java.util.Random;

import command.AttackCommand;
import command.MoveCommand;
import lifeform.Direction;
import lifeform.Lifeform;
import environment.Environment;

/**
 * @author Emmanuel Douge
 */
public class FightingState extends ActionState
{
	private Lifeform lifeform;
	private Lifeform target;
	private Environment env;
	private AIContext ai;
		
	public FightingState(AIContext ai) 
	{
		this.env = Environment.getInstance();
		this.ai = ai;
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
		//if dead change states if not attack
		if(lifeform.getCurrentLifePoints() <= 0)                
		{
			ai.setCurrentState(ai.getDeadState());
		}
		else
		{
			boolean enemyFoundInDirectionFaced = env.getLifeformClosestTo(lifeform, lifeform.getDirection()) != null;
			boolean enemyFoundInFront = env.getLifeformInFrontOf(lifeform, lifeform.getDirection()) != null;
			boolean lowHealth = lifeform.getCurrentLifePoints() < lifeform.getCurrentLifePoints()/4;
			if(enemyFoundInFront)
			{
				AttackCommand command = new AttackCommand(lifeform);
				command.execute();
			}
			else if(enemyFoundInDirectionFaced)
			{
				MoveCommand command = new MoveCommand(lifeform, 1);
				command.execute();
			}
			else if(lowHealth)
			{
				ai.setCurrentState(ai.getNeedWeaponState());
			}
			else
			{
				lifeform.setDirection(Direction.randomDirection());
			}
			
		}
	}
	


	/**
	 * @return the Lifeform of the AIContext
	 */
	public Lifeform getLifeform()
	{
		return lifeform;
	}
	
	/**
	 * @return the target of the lifeform
	 */
	public Lifeform getTarget()
	{
		return target;
	}
}

