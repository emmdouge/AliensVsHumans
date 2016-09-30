package environment;
import static org.junit.Assert.*;

import lifeform.Alien;
import lifeform.Human;
import lifeform.Lifeform;
import lifeform.MockLifeForm;

import org.junit.Test;

import exceptions.RecoveryRateException;
import weapon.Pistol;
import weapon.Weapon;

/**
 * @author Dr. Alice Armstrong
 * @author Brandon Shawver - Command Lab North & East Movement
 */
public class TestEnvironment 
{
	/**
	 * Tests that a life form can move south
	 * @author Joshua Bartle
	 */
	@Test
	public void testMoveSouth(){
		Environment e = Environment.getInstance();
		e.clearBoard();
		Lifeform entity = new Human("bob", 100, 0);
		entity.setDirection("south");
		entity.setCurrentSpeed(1); // movement speed is 1
		e.addLifeForm(entity, 0, 0);
		e.Move(entity, entity.getCurrentSpeed());
		assertEquals(entity, e.getLifeForm(1, 0)); // moved down (south) 1 space
	}
	
	/**
	 * Tests that a life form can move west
	 * @throws RecoveryRateException
	 * @author Joshua Bartle
	 */
	@Test
	public void testMoveWest() throws RecoveryRateException{
		Environment e = Environment.getInstance(4, 4);
		e.removeLifeForm(0, 1);
		e.removeLifeForm(0, 0);
		Lifeform entity = new Alien("alien", 100);
		entity.setDirection("west");
		entity.setCurrentSpeed(1); // speed is 1
		e.addLifeForm(entity, 0, 1); 
		e.Move(entity, entity.getCurrentSpeed());
		assertEquals(entity, e.getLifeForm(0, 0)); // moved left (west) 1 space
	}
	/**
	 * Tests that a life form can not move when another life form is in the cell
	 * @author Joshua Bartle
	 */
	@Test
	public void testMoveSouthWithObstacle(){
		Environment e = Environment.getInstance();
		e.clearBoard();
		Lifeform entity = new Human("bob", 100, 0);
		Lifeform obstacle = new MockLifeForm("obstacle", 100);
		entity.setDirection("south");
		entity.setCurrentSpeed(1); // movement speed is 1
		e.addLifeForm(entity, 0, 0);
		e.addLifeForm(obstacle, 1, 0);
		e.Move(entity, entity.getCurrentSpeed()); //try to move south
		assertEquals(obstacle, e.getLifeForm(1, 0));
		assertEquals(entity, e.getLifeForm(0, 0)); // did not move
		
	}
	
	/**
	 * Tests that a life form can not move when another life form is in the cell
	 * @throws RecoveryRateException
	 * @author Joshua Bartle
	 */
	@Test
	public void testMoveWestWithObstacle() throws RecoveryRateException{
		Environment e = Environment.getInstance(4, 4);
		e.removeLifeForm(0, 1);
		e.removeLifeForm(0, 0);
		Lifeform entity = new Alien("alien", 100);
		Lifeform obstacle = new MockLifeForm("obstacle", 100);
		entity.setDirection("west");
		entity.setCurrentSpeed(1); // speed is 1
		e.addLifeForm(entity, 0, 1); 
		e.addLifeForm(obstacle, 0, 0);
		e.Move(entity, entity.getCurrentSpeed()); // try to move
		assertEquals(entity, e.getLifeForm(0, 1)); // did not move
	}
	
	/**
	 * Tests that a life form can jump another life form if its speed allows
	 * @author Joshua Bartle
	 */
	@Test
	public void testMoveSouthOverObstacle(){
		Environment e = Environment.getInstance();
		e.clearBoard();
		Lifeform entity = new Human("bob", 100, 0);
		Lifeform obstacle = new MockLifeForm("obstacle", 100);
		entity.setDirection("south");
		entity.setCurrentSpeed(2); // movement speed is 
		e.addLifeForm(entity, 0, 0);
		e.addLifeForm(obstacle, 1, 0);
		e.Move(entity, entity.getCurrentSpeed()); //try to move south
		assertEquals(obstacle, e.getLifeForm(1, 0));
		assertEquals(entity, e.getLifeForm(2, 0)); // successfully jumped over obstacle
		
	}
	
	/**
	 * Tests that a life form can jump another life form if its speed allows
	 * @throws RecoveryRateException
	 * @author Joshua Bartle
	 */
	@Test
	public void testMoveWestOverObstacle() throws RecoveryRateException{
		Environment e = Environment.getInstance(4, 4);
		e.removeLifeForm(0, 1);
		e.removeLifeForm(0, 0);
		Lifeform entity = new Alien("alien", 100);
		Lifeform obstacle = new MockLifeForm("obstacle", 100);
		entity.setDirection("west");
		entity.setCurrentSpeed(2); // speed is 2
		e.addLifeForm(entity, 0, 2); 
		e.addLifeForm(obstacle, 0, 1);
		e.Move(entity, entity.getCurrentSpeed()); // try to move
		assertEquals(entity, e.getLifeForm(0, 0)); // successfully jumped over obstacle
	}
	/**
	 * Tests the functionality of a lifeform moving North through the map with and without obstacles
	 * @author Brandon Shawver
	 * @throws RecoveryRateException
	 */
	@Test
	public void testNorthMovement() throws RecoveryRateException
	{   Environment env = Environment.getInstance();
		env.clearBoard();
		Lifeform entity = new Human("bob", 100, 0);
		Lifeform entity2 = new Alien("life2", 10);
		entity.setDirection("north");
		entity.setCurrentSpeed(1);
		
		// Test North Movement without obstacle
		env.addLifeForm(entity, 4, 0);
		env.Move(entity, entity.getCurrentSpeed());
		assertEquals(entity, env.getLifeForm(3, 0));
        
        //Test North Movement with obstacle
		assertEquals(entity, env.getLifeForm(3, 0));
		env.addLifeForm(entity2, 2, 0);
		env.Move(entity, entity.getCurrentSpeed());
        assertEquals(entity, env.getLifeForm(3,0)); // Should not move because a lifeform occupies its destination
		
		//Test North Movement over obstacle
		env.clearBoard();
        env.addLifeForm(entity2, 2, 0);
        env.addLifeForm(entity, 3, 0);
        entity.setCurrentSpeed(2);
        env.Move(entity, entity.getCurrentSpeed());
        assertEquals(entity, env.getLifeForm(1,0));
	}
	
	/**
	 * Tests the functionality of a lifeform moving East through the map with and without obstacles
	 * @author Brandon Shawver
	 * @throws RecoveryRateException
	 */
	@Test
	public void testEastMovement() throws RecoveryRateException
	{
		Environment env = Environment.getInstance(5,5);
		Lifeform l2 = new Human("life1", 10, 0);
		Lifeform l1 = new Alien("life2", 10);
		env.clearBoard();
		
		//Test East Movement no obstacle
        l1.setDirection("east");
        l1.setCurrentSpeed(1);
        env.addLifeForm(l1, 3, 0);
        env.Move(l1, l1.getCurrentSpeed());
        assertEquals(l1, env.getLifeForm(3, 1));
        
        //Test East Movement with obstacle
        assertEquals(l1, env.getLifeForm(3, 1));
        env.addLifeForm(l2, 3, 2);
        env.Move(l1, l1.getCurrentSpeed());
        assertEquals(l1, env.getLifeForm(3,1)); // Should not move because a lifeform occupies its destination
        
        //Test East Movement over obstacle
        env.clearBoard();
        env.addLifeForm(l2, 3, 2);
        env.addLifeForm(l1, 3, 1);
        l1.setCurrentSpeed(2);
        env.Move(l1, l1.getCurrentSpeed());
        assertEquals(l1, env.getLifeForm(3,3)); // An obstacle is in the LifeForms way but it is moving 2 spaces to an open slot.
        										// Should still move.
	}
	/**
	 * Singleton Lab
	 */
	/**
	 * Test the initialization of the environment.
	 */
	@Test
    public void testIntialization()
    {
		// Tests the creation of the environment
        Environment env = Environment.getInstance(5,5);
        assertEquals(5,env.getRows());
        assertEquals(5,env.getColumns());
        
        // Tests to make sure you cannot have more than one instance
        Environment env2 = Environment.getInstance(5,3);
        assertEquals(5,env2.getRows());
        assertEquals(5,env2.getColumns());
    }
	
	/**
	 * Tests the adding of Weapons to the environment
	 */
	@Test
	public void testAddWeapon()
	{	
		Environment env = Environment.getInstance(5,5);
        Weapon gun = new Pistol();
        env.addWeapon(gun, 1, 3);
        assertEquals(gun, env.getWeapon(1,3));
	}
	
	/**
	 * Tests the removal of a weapon from the environment
	 */
	@Test
	public void testRemoveWeapon()
	{
		Environment env = Environment.getInstance(5,5);
        Weapon gun = new Pistol();
        env.addWeapon(gun, 2, 3);
        assertEquals(gun, env.getWeapon(2,3));
        env.removeWeapon(2,3);
        assertEquals(null, env.getWeapon(2,3));
	}
	
	/**
	 * Tests the calculation of distance between two lifeforms
	 * along the same row.
	 * 
	 * @throws RecoveryRateException
	 */
    @Test
    public void testComputeRangeSameRow() throws RecoveryRateException
    {
        Lifeform life1 = new Human("Human",10,14);
        Lifeform life2 = new Alien("Alien",10);
        Environment env = Environment.getInstance(5, 5);
        env.addLifeForm(life1, 1, 1);
        env.addLifeForm(life2, 1, 4);
        assertEquals(30,env.computeRange(life1,life2));
        env.removeLifeForm(1, 1);
        env.removeLifeForm(1, 4);
    }
    
    /**
     * Tests the calculation of distance between two lifeforms
     * along the same column
     * 
     * @throws RecoveryRateException
     */
    @Test
    public void testComputeRangeSameColumn() throws RecoveryRateException
    {
        Lifeform life1 = new Human("Human",10,14);
        Lifeform life2 = new Alien("Alien",10);
        Environment env = Environment.getInstance(5,5);
        env.clearBoard();
        env.addLifeForm(life1, 2, 2);
        env.addLifeForm(life2, 3, 2);
        assertEquals(10,env.computeRange(life1,life2));
        env.removeLifeForm(2, 2);
        env.removeLifeForm(3, 2);
    }
    
    /**
     * Tests the calculation of distance between two lifeforms
     * along different rows and columns.
     * 
     * @throws RecoveryRateException
     */
    @Test
    public void testComputeRangeDifferentRowAndColumn() throws RecoveryRateException
    {
        Lifeform life1 = new Human("Human",10,14);
        Lifeform life2 = new Alien("Alien",10);
        Environment env = Environment.getInstance(5, 5);
        env.addLifeForm(life1, 2, 0);
        env.addLifeForm(life2, 0, 4);
        assertEquals(45,env.computeRange(life1,life2));
        env.removeLifeForm(2, 0);
        env.removeLifeForm(0, 4);
    }
	
	/************ LAB 2 (Strategy) TESTS BELOW THIS POINT ******************/

	/**
	 * checks that we can add a LifeForm to a mulitdimensional Environment
	 */
	@Test
	public void testAddLifeForm()
	{
		
		Environment e = Environment.getInstance(5, 5); 
		MockLifeForm entity; 
        entity = new MockLifeForm("Bob", 40); 
        
        //border case 1,2
        assertTrue(e.addLifeForm(entity, 1, 2));
        MockLifeForm checkLF = (MockLifeForm) e.getLifeForm(1, 2); 
        assertEquals("Bob", checkLF.getName()); 
        assertEquals(40, checkLF.getCurrentLifePoints()); 
        
        //border case 0,2
        assertTrue(e.addLifeForm(entity, 0, 2));
        checkLF = null; 
        checkLF = (MockLifeForm) e.getLifeForm(0, 2); 
        assertEquals("Bob", checkLF.getName()); 
        assertEquals(40, checkLF.getCurrentLifePoints());
        
        //border case 0,0
        assertTrue(e.addLifeForm(entity, 0, 0));
        checkLF = null; 
        checkLF = (MockLifeForm) e.getLifeForm(0, 0); 
        assertEquals("Bob", checkLF.getName()); 
        assertEquals(40, checkLF.getCurrentLifePoints());
        
        //border case 1, 0
        assertTrue(e.addLifeForm(entity, 1, 0));
        checkLF = null; 
        checkLF = (MockLifeForm) e.getLifeForm(1, 0); 
        assertEquals("Bob", checkLF.getName()); 
        assertEquals(40, checkLF.getCurrentLifePoints());
	}
	
	/**
	 * adds a LifeFormto the Environment, then removes it. 
	 * checks that removing from an empty cell is OK
	 */
	@Test
	public void testRemoveLifeForm()
	{
		Environment e = Environment.getInstance(5, 5); 
		e.clearBoard();
		MockLifeForm entity; 
        entity = new MockLifeForm("Bob", 40); 
        
        //add a LF to 1,2
        assertTrue(e.addLifeForm(entity, 1, 2));
        MockLifeForm checkLF = (MockLifeForm) e.getLifeForm(1, 2); 
        assertEquals("Bob", checkLF.getName()); 
        assertEquals(40, checkLF.getCurrentLifePoints()); 
        
        //remove the LF
        e.removeLifeForm(1,2); 
        assertNull(e.getLifeForm(1, 2)); 
        
        //try removing from the same cell again
        e.removeLifeForm(1,2); 
        assertNull(e.getLifeForm(1, 2));
        
        //try removing from a cell that never had a LF in it
        assertNull(e.getLifeForm(0, 0)); 
        e.removeLifeForm(0, 0); 
        assertNull(e.getLifeForm(0, 0)); 
       
		
	}
}
