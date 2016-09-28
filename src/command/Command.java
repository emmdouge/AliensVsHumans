package command;

/**
 * 
 * @author Emmanuel
 *interface for all the commands
 */
public interface Command {
	
	/**
	 * All commands must have an execute method
	 */
	public abstract void execute();
}
