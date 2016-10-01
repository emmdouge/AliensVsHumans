package command;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import lifeform.Direction;
import lifeform.Lifeform;
import environment.Environment;
import graphics.GUI;

/**
 * @author Emmanuel
 * turns the player east
 */
public class TurnEastCommand extends AbstractAction implements Command {
	private Environment env;
	private Lifeform lifeform;
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 */
	public TurnEastCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	public TurnEastCommand(Lifeform lifeform)
	{
		this.env = Environment.getInstance();
		this.lifeform = lifeform;
	}
	
	private Lifeform getLifeform() {
		return this.lifeform;
	}
	
	public void execute()
	{
		//sets direction to east
		getLifeform().setDirection(Direction.EAST);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		execute();
		GUI.getInstance().draw();
	}
}
