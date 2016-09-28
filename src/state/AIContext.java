package state;

import lifeform.LifeForm;

/**
 * 
 * @author Cassia Hulshizer
 *
 */
public class AIContext 
{
	private LifeForm entity;
	private ActionState currentState;
	private ActionState outOfAmmo;
	private ActionState hasWeapon;
	private ActionState noWeapon;
	private ActionState dead;
	
	/**
	 * creates an AIContext
	 * @param lifeform
	 */
	public AIContext(LifeForm lifeform)
	{
		entity = lifeform;
		outOfAmmo = new OutOfAmmoState(this);
		hasWeapon = new HasWeaponState(this);
		noWeapon = new NoWeaponState(this);
		dead = new DeadState(this);
		currentState = noWeapon;
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
	public void setLifeform(LifeForm lifeForm)
	{
		this.entity = lifeForm;
	}
	
	/**
	 * @return the specific lifeForm being controlled by this AI
	 */
	public LifeForm getLifeForm()
	{
		return entity;
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
	public ActionState getHasWeaponState()
	{
		return hasWeapon;
	}
	
	/**
	 * @return the noWeapon state for state change purposes
	 */
	public ActionState getNoWeaponState()
	{
		return noWeapon;
	}
	
	/**
	 * @return the dead state for state change purposes
	 */
	public ActionState getDeadState()
	{
		return dead;
	}
}
