package environment;
import lifeform.MockLifeForm;
import static org.junit.Assert.*;

import org.junit.Test;

import weapon.Pistol;
import weapon.Weapon;

/** 
 * The test cases for the Cell class 
 * @author Dr. Dudley Girard -- first author
 * @author Dr. Alice Armstrong -- revisions
 * @authoter Brandon Shawver -- Singleton lab
 */ 
public class TestCell 
{ 
	/**
	 * Singleton code
	 */
    @Test
    public void testCanKeepTrackOfWeaponOne()
    {
        Weapon pistol = new Pistol();
        Cell cell = new Cell();
        cell.setWeapon(pistol);
        assertEquals(pistol,cell.getWeapon());
        
        Weapon pistol2 = new Pistol();
        cell.setWeapon(pistol2);
        assertEquals(pistol,cell.getWeapon());
        
        cell.setWeapon(null);
        assertNull(cell.getWeapon());
    }
    
    @Test
    public void testCanKeepTrackOfWeaponTwo()
    {
        Weapon pistol = new Pistol();
        Cell cell = new Cell();
        cell.setWeapon(pistol);
        assertEquals(pistol,cell.getWeapon());
        
        Weapon pistol2 = new Pistol();
        cell.setWeapon(pistol2);
        assertEquals(pistol,cell.getWeapon());
        
        cell.setWeapon(null);
        assertNull(cell.getWeapon());
    }
    
 /************ LAB 2 (Strategy) TESTS BELOW THIS POINT ******************/
 /** 
  * At initialization, the Cell should be empty and not contain a    
  * LifeForm. 
  */ 
    @Test 
    public void testInitialization() 
    { 
        Cell cell = new Cell(); 
        assertNull(cell.getLifeForm()); 
    } 
    
/** 
 * Checks to see if we change the LifeForm held by the Cell that
 * getLifeForm properly responds to this change. 
 */ 
   @Test 
   public void testSetLifeForm() 
   { 
       MockLifeForm bob = new MockLifeForm("Bob", 40); 
       MockLifeForm fred = new MockLifeForm("Fred", 40);
       Cell cell = new Cell();
     // The cell is empty so this should work.
       boolean success = cell.addLifeForm(bob);
       assertTrue(success);
       assertEquals(bob,cell.getLifeForm());
     // The cell is not empty so this should fail.
       success = cell.addLifeForm(fred);
       assertFalse(success);
       assertEquals(bob,cell.getLifeForm());

   } 

   @Test
   public void testRemoveLifeForm()
   {
	   MockLifeForm bob = new MockLifeForm("Bob", 40); 
       Cell cell = new Cell();
       boolean success = cell.addLifeForm(bob);
       assertTrue(success);
       assertEquals(bob,cell.getLifeForm());
       
       //now remove the LifeForm from the cell
       cell.removeLifeForm(); 
       assertNull(cell.getLifeForm()); 
       
       //now remove from an empty cell
       
       Cell cell2 = new Cell(); 
       assertNull(cell2.getLifeForm()); 
       cell2.removeLifeForm(); 
       assertNull(cell2.getLifeForm()); 
    
   }
}

