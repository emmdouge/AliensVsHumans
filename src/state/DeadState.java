package state;

import command.RespawnCommand;
import environment.Environment;
import graphics.GUI;

/**
 * @author Emmanuel
 * The deadstate will respawn the subject
 */
public class DeadState extends ActionState {

	private AIContext subject;
	private Environment env;
	
	/**
	 * @param subject holds states and lifeform
	 */
	public DeadState(AIContext subject)
	{
		this.subject = subject;
		this.env = Environment.getInstance();
	}
	
	@Override
	public void handle() {
		//respawn subject
		RespawnCommand respawnCommand = new RespawnCommand(subject.getLifeForm());
		respawnCommand.execute();
		subject.setLifeform(respawnCommand.getLifeform());
		//set to noWeaponState
		subject.setCurrentState(subject.getNeedWeaponState());
		GUI.getInstance().draw();
	}

}
