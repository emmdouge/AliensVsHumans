package environment;
import weapon.Weapon;
import lifeform.LifeForm;
/** 
 * A Cell that can hold a LifeForm. 
 * @author Dr. Dudley Girard -- first author
 * @author Dr. Alice Armstrong -- revisions
 * @authot Brandon Shawver -- Singleton lab
 */ 
public class Cell 
{ 
  private LifeForm cellLife;  //the LifeForm that exists in thsi cell
  private Weapon weaponOne = null;
  private Weapon weaponTwo = null;
  
 /** 
  * @return the LifeForm in this Cell. 
  */ 
    public LifeForm getLifeForm() 
    { 
        return cellLife; 
    }  
    
    /**
     * Returns Weapons in the Cell.
     * @return
     */
    public Weapon getWeapon()
    {
    	Weapon weapon;
    	if(weaponOne != null)
    	{
    		weapon = this.getWeaponOne();
    	}
    	else
    	{
    		if(weaponTwo != null)
    		{
    			weapon = this.getWeaponTwo();
    		}
    		weapon = null;
    	}
    		
        return weapon;
    }
    
    /**
     * returns weaponOne
     * @return
     */
	public Weapon getWeaponOne() 
	{
		return weaponOne;
	}
	
	/**
	 * returns weaponTwo
	 * @return
	 */
	public Weapon getWeaponTwo()
	{
		return weaponTwo;
	}
    
    /**
     * If there is space it places the weapon in the weaponOne slot
     * or else it checks the second slot for space and if open it sets the
     * weapon to the second slot.
     * @param weapon
     */
    public void setWeapon(Weapon weapon)
    {
        if ((weaponOne == null) || (weapon == null))
        {
        	this.setWeaponOne(weapon);
        }
        else if((weaponTwo == null) || (weapon==null))
        {
        	this.setWeaponTwo(weapon);
        }
    }
    
    /**
     * If there is space it places the weapon in the weaponOne slot.
     * @param weapon
     */
    private void setWeaponOne(Weapon weapon)
    {
            weaponOne = weapon;
    }
    
    /**
     * If there is space it places the weapon in the weaponTwo slot.
     * @param weapon
     */
    protected void setWeaponTwo(Weapon weapon)
    {
            weaponTwo = weapon;
    }
    
/** 
 * Tries to add the LifeForm to the Cell.  Will not add if a 
 * LifeForm is already present.
 * @return true if the LifeForm was added the Cell, false otherwise. 
 */ 
    public boolean addLifeForm(LifeForm entity) 
    { 
    	//if there is not LifeForm in this cell, put entity here
    	if (cellLife == null)
    	{
    		cellLife = entity; 
    		return true; 
    	}
    	
    	//otherwise, the cell is already full. Do not place entity here
        return false; 
    }  

    /**
     * Removes any LifeForm from this Cell. 
     * @author Dr. Alice Armstrong
     */
    public void removeLifeForm()
    {
    	cellLife = null; 
    }
}

