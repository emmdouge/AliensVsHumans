package state;

import lifeform.Lifeform;

/**
 * 
 * @author Cassia Hulshizer
 *
 */
public class AIContext 
{
	private Lifeform lifeform;
	private ActionState currentState;
	private ActionState outOfAmmo;
	private ActionState fighting;
	private ActionState needWeapon;
	private ActionState dead;
	
	/**
	 * creates an AIContext
	 * @param lifeform
	 */
	public AIContext(Lifeform lifeform)
	{
		this.lifeform = lifeform;
		
		outOfAmmo = new OutOfAmmoState(this);
		fighting = new FightingState(this);
		needWeapon = new NeedWeaponState(this);
		dead = new DeadState(this);
		
		currentState = fighting;
	}
	
	/**
	 * sets the current state of the AI
	 * @param state the current state
	 */
	public void setCurrentState(ActionState state)
	{
		currentState = state;
	}
	
	/**
	 * 
	 * @return the current state of the AI
	 */
	public ActionState getCurrentState()
	{
		return currentState;
	}
	
	/**
	 * allows the current state to handle the execution
	 */
	public void execute()
	{
		currentState.handle();
	}
	
	/**
	 * sets the lifeForm being controlled by this AI
	 * @param lifeForm
	 */
	public void setLifeform(Lifeform lifeForm)
	{
		this.lifeform = lifeForm;
	}
	
	/**
	 * @return the specific lifeForm being controlled by this AI
	 */
	public Lifeform getLifeForm()
	{
		return lifeform;
	}
	
	/**
	 * @return the outOfAmmo state for state change purposes
	 */
	public ActionState getOutOfAmmoState()
	{
		return outOfAmmo;
	}
	
	/**
	 * @return the hasWeapon state for state change purposes
	 */
	public ActionState getFightingState()
	{
		return fighting;
	}
	
	/**
	 * @return the noWeapon state for state change purposes
	 */
	public ActionState getNeedWeaponState()
	{
		return needWeapon;
	}
	
	/**
	 * @return the dead state for state change purposes
	 */
	public ActionState getDeadState()
	{
		return dead;
	}
}
