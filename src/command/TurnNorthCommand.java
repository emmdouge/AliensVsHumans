package command;

import lifeform.LifeForm;
import environment.Environment;

/**
 * @author Emmanuel
 * turns the player north
 */
public class TurnNorthCommand implements Command {
	private Environment env;
	private LifeForm lifeform;
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 */
	public TurnNorthCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	public TurnNorthCommand(LifeForm lifeform)
	{
		this.env = Environment.getInstance(0, 0);
		this.lifeform = lifeform;
	}
	
	private LifeForm getLifeform() {
		return this.lifeform;
	}
	
	public void execute()
	{
		//sets direction to north
		getLifeform().setDirection("north");
	}
}
