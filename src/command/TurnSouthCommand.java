package command;

import lifeform.LifeForm;
import environment.Environment;

/**
 * @author Emmanuel
 * turns the player south
 */
public class TurnSouthCommand implements Command {
	private Environment env;
	private LifeForm lifeform;
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 */
	public TurnSouthCommand(LifeForm lifeform)
	{
		this.env = Environment.getInstance(0, 0);
		this.lifeform = lifeform;
	}
	
	public TurnSouthCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	private LifeForm getLifeform() {
		return this.lifeform;
	}
	
	public void execute()
	{
		//sets direction to south
		getLifeform().setDirection("south");
	}
}
