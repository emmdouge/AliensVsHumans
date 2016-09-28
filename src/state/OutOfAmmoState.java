package state;

import command.ReloadCommand;

import environment.Environment;

/**
 * @author Emmanuel
 *
 */
public class OutOfAmmoState extends ActionState {

	private AIContext subject;
	private Environment env;

	public OutOfAmmoState(AIContext subject) {
		this.subject = subject;
		this.env = Environment.getInstance();
	}

	@Override
	public void handle() {
		if(subject.getLifeForm().getCurrentLifePoints() <= 0)
		{
			subject.setCurrentState(subject.getDeadState());
		}
		else
		{
			subject.getLifeForm().reload();
			subject.setCurrentState(subject.getHasWeaponState());
		}
	}

}
