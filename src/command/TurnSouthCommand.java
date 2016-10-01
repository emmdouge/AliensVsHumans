package command;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import lifeform.Direction;
import lifeform.Lifeform;
import environment.Environment;
import graphics.GUI;

/**
 * @author Emmanuel
 * turns the player south
 */
public class TurnSouthCommand extends AbstractAction implements Command
{
	private Environment env;
	private Lifeform lifeform;
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 */
	public TurnSouthCommand(Lifeform lifeform)
	{
		this.env = Environment.getInstance();
		this.lifeform = lifeform;
	}
	
	public TurnSouthCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	private Lifeform getLifeform() {
		return this.lifeform;
	}
	
	public void execute()
	{
		//sets direction to south
		getLifeform().setDirection(Direction.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		execute();
		GUI.getInstance().draw();
	}
}
