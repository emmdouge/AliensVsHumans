package command;

import lifeform.LifeForm;
import environment.Environment;

/**
 * @author Emmanuel
 * reloads the player's weapon
 */
public class ReloadCommand implements Command {
	private Environment env;
	private LifeForm lifeform;
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 */
	public ReloadCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	public ReloadCommand(LifeForm lifeform)
	{
		this.env = Environment.getInstance(0, 0);
		this.lifeform = lifeform;
	}
	
	private LifeForm getLifeform() {
		return this.lifeform;
	}
	
	public void execute()
	{
		//if the player has a weapon
		if(getLifeform().hasWeapon() == true)
		{
			//reload the weapon
			getLifeform().reload();
		}
	}
}
