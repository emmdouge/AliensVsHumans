/**
 * 
 */
package lifeform;

import exceptions.RecoveryRateException;
import gameplay.TimerObserver;
import recovery.RecoveryBehavior;
import recovery.RecoveryNone;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class Alien extends Lifeform implements TimerObserver
{

	private int maxLifePoints; //the maximum number of life points the Alien can have
	private RecoveryBehavior recovery; //how the alien recovery life points
	private int recoveryRate; //Aliens recovery periodically. This alien recovers every rR rounds
							  // a recoveryRate of 0 means that this Alien will never recover, even if it has a recovery behavior
	
	/**
	 * Aliens recreated with a RecoveryBehavior are RecoveryNone by default
	 * Aliens have an attack strength of 10, by default
	 * Aliens have a recovery Rate of 0 (will never recover) by default
	 * @param name
	 * @param maxHP
	 * @throws RecoveryRateException this should never be thrown
	 */
	public Alien (String name, int maxHP) throws RecoveryRateException
	{
		//refactored so that each constructor only calls one constructor
		//super(name, maxHP); 
		//maxLifePoints = maxHP; 
		//recovery = new RecoveryNone(); 
		
		//refactored constructor
		this(name, maxHP, new RecoveryNone());
	}
	
	/**
	 * Aliens have an attack strength of 10, by default
	 * Aliens have a recovery Rate of 0 (will never recover) by default
	 * @param name
	 * @param maxHP maximum possible life points, also the initial life points at instantiation
	 * @param behavior the way in which this Alien will recover life points
	 * @throws RecoveryRateException this should never be thrown
	 */
	public Alien(String name, int maxHP, RecoveryBehavior behavior) throws RecoveryRateException 
	{
		//refactored so that only one constructor is used
		//super(name, maxHP, 10); 
		//maxLifePoints = maxHP; 
		//recovery = behavior; 
		
		//refactored constructor, default recovery rate of 0
		this(name, maxHP, behavior, 0); 
	}
	
	/**
	 * Aliens have an attack strength of 10, by default
	 * @param name
	 * @param maxHP
	 * @param behavior
	 * @param recoveryRate Aliens can recover periodically. This specifies how often to recover. A recovery rate of 1 means the Alien will recover every round. 
	 * A recovery rate of 0 means this Alien will never recover points. 
	 * @throws RecoveryRateException thrown if the recoverRate is less than 0
	 */
	public Alien(String name, int maxHP, RecoveryBehavior behavior, int recoveryRate) throws RecoveryRateException 
	{
	
		super(name, maxHP, 10); 
		maxLifePoints = maxHP; 
		recovery = behavior; 
		if (recoveryRate < 0)
		{
			//still set recoveryRate to neg value. May come back and change this. 
			this.recoveryRate = recoveryRate;
			throw new RecoveryRateException("The recovery rate must be at least 0.");
		}
		this.recoveryRate = recoveryRate; 
		
		this.setMaxSpeed(2);
		
	}
	
	/**
	 * 
	 * @return the recovery rate
	 */
	public int getRecoveryRate()
	{
		return recoveryRate; 
	}

	/**
	 * 
	 * @return the maximum life points the Alien can have
	 */
	public int getMaxLifePoints()
	{
		return maxLifePoints; 
	}
	
	protected void recover()
	{
		currentLifePoints = recovery.calculateRecovery(currentLifePoints, maxLifePoints); 
	}

	@Override
	public void update(int time) 
	{
		if(recoveryRate == 0)
		{
			return;
		}
		if (time%recoveryRate == 0)
		{
			recover(); 
		}
		
	}

	@Override
	public boolean isAlien() 
	{
		return true;
	}


}
