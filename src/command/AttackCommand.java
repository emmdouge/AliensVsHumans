package command;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import lifeform.Lifeform;
import environment.Environment;
import graphics.GUI;

/**
 * @author Emmanuel
 * attacks enemies based on if the player has a weapon or not
 */
public class AttackCommand extends AbstractAction implements Command {
	private Environment env;
	private Lifeform lifeform;

	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 */
	public AttackCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	public AttackCommand(Lifeform lifeform)
	{
		this.env = Environment.getInstance();
		this.lifeform = lifeform;
	}
	
	public void execute()
	{
		Lifeform opponent;
		
		//if the player has a weapon and wants to attack
		if(lifeform.hasWeapon())
		{
			//find the closest enemy
			opponent = env.getLifeformClosestTo(lifeform, lifeform.getDirection());
			
			//if an enemy is found, attack
			boolean enemyIsFound = opponent != null;
			if(enemyIsFound)
			{		
				lifeform.attack(opponent, env.computeRange(lifeform, opponent));
			}
		}
		//if the player does not have a weapon and wants to attack
		else
		{
			//find the enemy that is front of the player in the direction he is facing
			opponent = env.getLifeformInFrontOf(lifeform, lifeform.getDirection());
			
			//if an enemy is found, attack
			boolean enemyIsFound = opponent != null;
			if(enemyIsFound)
			{		
				lifeform.attack(opponent, 5);
			}
		}
		
		boolean opponentWasKilled = opponent != null && opponent.getCurrentLifePoints() <= 0;
		if(opponentWasKilled)
		{
			RespawnCommand command = new RespawnCommand(opponent);
			command.execute();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		execute();
		GUI.getInstance().redraw();
	}
}
