package lifeform;

import environment.Environment;
import exceptions.WeaponException;
import gameplay.TimerObserver;
import weapon.Weapon;

/** 
 * Keeps track of the information associated with a simple life form.
 * Also provides the functionality related to the life form. 
 * 
 * @author Emmanuel
 */ 
public abstract class Lifeform
{ 
    private String myName;
    protected int currentLifePoints; 
    protected int maxLifePoints; 
    protected int attackStrength; 
    private Weapon weapon; 
    protected Direction currentDirection = Direction.NORTH;
    private int maxSpeed = 0;
    protected int currentSpeed = getMaxSpeed();
    public boolean isPlayer = false;
    private int numKills;
 
 /** 
  * Create an instance with default attack strength of 1
  *  
  * @param name the name of the life form
  * @param points the current starting life points of the life form. 
  */ 
    public Lifeform(String name, int points) 
    { 
    	//refactored to make sure there is only one meaningful constructor
    	this(name, points, 1); 
    } 
 
    /**
     * Create an instance
     * @param name
     * @param points the life points 
     * @param attack the attack strength
     */
    public Lifeform(String name, int points, int attack) 
    { 
    	myName = name; 
    	currentLifePoints = points; 
    	maxLifePoints = points;
    	attackStrength = attack;
    	numKills = 0;
    	setWeapon(null); 
    } 
    
    
    /**
     * The LifeForm's lifepoints are reduced by the damage
     * A LifeForm cannot have life points less than zero. If the damage is greater than the remaining lifepoint, 
     * the LifeForm's lifepoints will be zero. 
     * @param damage
     */
    public void takeHit(int damage)
    {
    	if ((currentLifePoints - damage) >=0)
    	{
    		currentLifePoints -= damage; 
    	}
    	else
    	{
    		currentLifePoints = 0; 
    	}
    }
    
    /**
     * attack another Lifeform with full attack strength
     * if the attacker has a weapon with ammo, the weapon will be used
     * if the attacker has no weapon or the weapon is out of ammo, the ranged touch attack (hand to hand attack strength) will be used
     * ranged touch attack will only work within a distance <= 5
     * weapon attacks will work only within the range of the weapon used
     * @param opponent
     * @param distance the distance between two LifeForms
     */
    public void attack(Lifeform opponent, int distance)
    {
    	if (currentLifePoints >  0)
    	{
    		//if there is no weapon AND the opponent is within 5 feet
    		//use the ranged touch attack
    		if (!hasWeapon() && distance <= 10)
    		{
    			
    			//it's important that the oppenent take the hit because
	    		//humans will have armour that reduces the effect of the attack
	        	opponent.takeHit(attackStrength);
    		}
    		
    		//if there is a weapon, but it is out of ammo
    		//AND the opponent is within 5 feet
    		//use the ranged touch attack
    		else if (hasWeapon() && getWeapon().getCurrentAmmo() == 0 && distance <= 10)
    		{
    			//it's important that the oppenent take the hit because
	    		//humans will have armour that reduces the effect of the attack
	        	opponent.takeHit(attackStrength);
    		}
    		
    		//if there is a weapon
    		//use weapon's attack 
    		//there may not be any damage if the opponent is out of range
    		//there may not be any damage is the weapon is out of ammo
    		else if (hasWeapon())
    		{
    			try
    			{
    				opponent.takeHit(getWeapon().fire(distance));
    			}
    			catch(WeaponException e)
    			{
    				//the distance is negative, which it should not ever be
    				//just print the error message, but do not execute the attack
    				System.out.println(e.getMessage()); 
    			}
    		}
    	}  
    	
    	if(opponent.getCurrentLifePoints() <= 0)
    	{
    		incrementKillCount();
    	}
    }
    
    /**
     * If this LifeForm does not already hold a weapon, it will take possession of this one
     * @param weapon
     * @return true if the weapon was acquired
     */
    public boolean pickUpWeapon(Weapon weapon)
    {
    	if(!hasWeapon())
    	{
    		this.setWeapon(weapon); 
    		return true; 
    	}
    	else
    	{
    		return false; 
    	}
    }
    
    /**
     * drops this LifeForm's Weapon and returns it to the caller
     * @return the Weapon this lifeform was holding
     */
    public Weapon dropWeapon()
    {
    	if (hasWeapon())
    	{
    		Weapon temp = getWeapon(); 
    		//the lifeform no holds no weapon
    		setWeapon(null); 
    		//return the old weapon
    		return temp; 
    	}
    	
    	//otherwise there is nothing to return
    	return null; 
    }
    
    /**
	 * @return
	 */
	public boolean hasWeapon() 
	{
		if (getWeapon() != null)
		{
			return true; 
		}
		return false;
	}

	/** 
     * @return the name of the life form. 
     */ 
       public String getName() 
       { 
           return myName; 
       } 
    
    /** 
     * @return the amount of current life points the life form has. 
     */ 
       public int getCurrentLifePoints() 
       { 
           return currentLifePoints; 
       } 
       
       /**
        * 
        * @return this LifeForm's attack strength
        */
       public int getAttackStrength()
       {
    	   return attackStrength; 
       }
       
       public void reload()
       {
    	   getWeapon().reload();
       }
       
       /**
        * @return the current direction the life form is facing.
        */
   		public Direction getDirection()
   		{
   			return currentDirection;
   		}
	
   		/**
   		 * 
   		 * @param direction desired direction to be facing.
   		 */
   		public void setDirection(Direction direction)
   		{
   			currentDirection = direction;
   		}
	
   		/**
   		 * @return current speed of the lifeform
   		 */
   		public int getCurrentSpeed()
   		{
   			return currentSpeed;
   		}

		public Weapon getWeapon() 
		{
			return weapon;
		}

		public void setWeapon(Weapon weapon) 
		{
			this.weapon = weapon;
		}

		public int getMaxSpeed() 
		{
			return maxSpeed;
		}

		public void setCurrentSpeed(int speed) 
		{
			this.currentSpeed = speed;
		}
		
		public void setMaxSpeed(int speed)
		{
			this.maxSpeed = speed;
		}

		public int getMaxLifePoints() {
			return maxLifePoints;
		}

		public void setHealth(int lifePoints)
		{
			this.currentLifePoints = lifePoints;
		}

		public abstract boolean isAlien();

		public void incrementKillCount() 
		{
			numKills++;
		}

		public int getNumKills() 
		{
			return numKills;
		}
}
