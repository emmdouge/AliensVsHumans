package command;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import lifeform.Lifeform;
import environment.Environment;
import graphics.GUI;

/**
 * @author Emmanuel
 * reloads the player's weapon
 */
public class ReloadCommand extends AbstractAction implements Command {
	private Environment env;
	private Lifeform lifeform;
	
	/**
	 * initializes the Environment instance variable
	 * to the only instance of Environment
	 */
	public ReloadCommand()
	{
		this(Environment.getInstance().getPlayer());
	}
	
	public ReloadCommand(Lifeform lifeform)
	{
		this.env = Environment.getInstance();
		this.lifeform = lifeform;
	}
	
	private Lifeform getLifeform() {
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

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		execute();
		GUI.getInstance().redraw();
	}
}
