package state;

/**
 * 
 * @author Brandon Shawver
 */
public abstract class ActionState
{
	private AIContext subject;
	
	public abstract void handle();
}
