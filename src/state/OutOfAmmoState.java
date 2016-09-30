package state;

import command.ReloadCommand;

import environment.Environment;

/**
 * @author Emmanuel
 *
 */
public class OutOfAmmoState extends ActionState {

	private AIContext ai;
	private Environment env;

	public OutOfAmmoState(AIContext subject) {
		this.ai = subject;
		this.env = Environment.getInstance();
	}

	@Override
	public void handle() 
	{
		boolean lifeformIsDead = ai.getLifeForm().getCurrentLifePoints() <= 0;
		boolean lifeformHasWeapon = ai.getLifeForm().getWeapon() != null;
		
		if(lifeformIsDead)
		{
			ai.setCurrentState(ai.getDeadState());
		}
		else if(lifeformHasWeapon)
		{
			ai.getLifeForm().reload();
		}

		ai.setCurrentState(ai.getFightingState());
	}

}
