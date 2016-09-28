package lifeform;
import static org.junit.Assert.*; 

import org.junit.Test; 

import environment.Environment;
import exceptions.RecoveryRateException;
import gameplay.SimpleTimer;
import weapon.MockWeapon;
import weapon.Pistol;
import weapon.PlasmaCannon;
 
/** 
 * Tests the functionality provided by the Book class 
 * @author Brandon Shawver - Singleton Lab revisions
 */ 
public class TestLifeForm 
{ 
	
	/**
	 * Command Pattern
	 */
	
	/**
	 * Tests to make sure that the LifeFOrm can 
	 * change directions
	 * @author - Brandon Shawver
	 */
	@Test
	public void testChangingDirections()
	{
		LifeForm life = new MockLifeForm("bob", 10);
		life.setDirection("South");
		assertEquals(life.getDirection(), "South");
		life.setDirection("East");
		assertEquals(life.getDirection(), "East");
		life.setDirection("West");
		assertEquals(life.getDirection(), "West");
		life.setDirection("North");
		assertEquals(life.getDirection(), "North");
	}
	
    /**
     * Tests to make sure that the maxSpeed and 
     * currentDirection are all correct at 
     * initialization
     * @author - Brandon Shawver
     * @throws RecoveryRateException 
     */
	@Test
	public void testMaxSpeedAndDirectionInitialization() throws RecoveryRateException
	{
		LifeForm life = new MockLifeForm("Mock",10);
		
		assertEquals(0, life.getMaxSpeed());
		assertEquals("north", life.getDirection());
	}

	/************ LAB 4 (Decorator) TESTS START HERE ******************/
	
	/**
	 * make sure that a LifeForm with no weapon cannot attack
	 * an opponent that is more that 5 feet away
	 */
	@Test
	public void testHandToHandOutOfRange()
	{
		MockLifeForm bob;
		MockLifeForm sheryl; 
		bob = new MockLifeForm("Bob", 40, 5); 
		sheryl = new MockLifeForm("Sheryl", 50, 7); 
		
		Environment env = Environment.getInstance(5,5);
		assertEquals(true, env.addLifeForm(bob, 1, 1));
		assertEquals(true, env.addLifeForm(sheryl, 1, 3));
		assertEquals(20, env.computeRange(bob,sheryl));
		
		//if Bob attacks Sheryl, Sheryl should take 0 points of damage
		//because they are too far apart
        bob.attack(sheryl, env.computeRange(bob,sheryl)); 
        assertEquals(50, sheryl.getCurrentLifePoints()); 
        
        env.removeLifeForm(1,1);
        env.removeLifeForm(1,3);
	}
	
	@Test
	public void testAttackWithWeapon()
	{
		MockLifeForm bob;
		MockLifeForm sheryl; 
		bob = new MockLifeForm("Bob", 100, 1); 
		sheryl = new MockLifeForm("Sheryl", 50, 2); 
		
		Environment env = Environment.getInstance(5,5);
		env.addLifeForm(bob, 1, 1);
		env.addLifeForm(sheryl, 1, 3);
		assertEquals(20, env.computeRange(bob,sheryl));
		
		Pistol p = new Pistol(); 
		PlasmaCannon pC = new PlasmaCannon(); 
		
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(p);
		timer.addTimeObserver(pC);
		
		//give Bob the Pistol and Sheryl the PlasmaCannon
		bob.pickUpWeapon(p); 
		sheryl.pickUpWeapon(pC); 
		
		//bob should to 5 points of damage to Sheryl
		bob.attack(sheryl, env.computeRange(bob,sheryl));
		assertEquals(44, sheryl.getCurrentLifePoints()); 
		
		//sheryl should to 50 points of damage to Bob
		sheryl.attack(bob, env.computeRange(bob,sheryl));
		assertEquals(50, bob.getCurrentLifePoints()); 
		assertEquals(pC.getCurrentAmmo(), 3); 
		
		//bob should do 3 points of damage to Sheryl
		bob.attack(sheryl, env.computeRange(bob,sheryl));
		assertEquals(38, sheryl.getCurrentLifePoints()); 
		
		//update the time, so all shots can be fired
		timer.timeChanged();
		//empty the Plasma Cannon clip
		sheryl.attack(bob, env.computeRange(bob,sheryl));
		assertEquals(pC.getCurrentAmmo(), 2); 
		//update the time, so all shots can be fired
		timer.timeChanged();
		sheryl.attack(bob, env.computeRange(bob,sheryl));
		assertEquals(pC.getCurrentAmmo(), 1); 
		
		env.removeLifeForm(1,1);
		env.removeLifeForm(1,3);
		
		env.addLifeForm(bob, 1, 1);
		env.addLifeForm(sheryl, 1, 2);
		
		//sheryl attacks bob hand to hand
		sheryl.attack(bob, env.computeRange(bob,sheryl));
		assertEquals(0, bob.getCurrentLifePoints()); 
		env.removeLifeForm(1,1);
		env.removeLifeForm(1,2);
	}
	
	/**
	 * makes sure a LifeForm can pick up a Weapon
	 * makes sure a LifeForm can only hold one Weapon at a time
	 * makes sure a Weapon can be dropped and returned to the caller
	 */
	@Test
	public void testWeaponPickUpAndDrop()
	{
		MockLifeForm bob;
		bob = new MockLifeForm("Bob", 40, 5); 
		MockWeapon gun = new MockWeapon(); 
		
		//check that bob is not holding a weapon
		assertFalse(bob.hasWeapon()); 
		
		//pick up the gun
		assertTrue(bob.pickUpWeapon(gun)); 
		
		//check that bob has a weapon
		assertTrue(bob.hasWeapon()); 
		
		//try to pick up the gun again
		assertFalse(bob.pickUpWeapon(gun)); 
		
		//drop the gun & pass it back
		MockWeapon temp = (MockWeapon)bob.dropWeapon(); 
		assertEquals(temp, gun); 
		
		//check the bob no longer has the gun
		assertFalse(bob.hasWeapon()); 
		
		//pick up the gun again
		assertTrue(bob.pickUpWeapon(gun)); 
		
		//check the bob has the gun again
		assertTrue(bob.hasWeapon()); 
		
	}

	
	/************ LAB 3 (Observer) Tests were refactored to hand ranged touch attacks *****/
	
	/**
	 * makes sure we can 
	 * * create a LifeForm with an attack strength
	 * * check the attack strength
	 * * attack another LifeForm that is within hand-to-hand attack range
	 * * see the other LifeForm take the damage
	 */
	@Test
	public void testAttack()
	{
		MockLifeForm bob;
		MockLifeForm sheryl; 
		bob = new MockLifeForm("Bob", 40, 5); 
		sheryl = new MockLifeForm("Sheryl", 50, 7);
		Environment env = Environment.getInstance(5,5);
		env.addLifeForm(bob, 1, 1);
		env.addLifeForm(sheryl, 1, 2);
		assertEquals(10, env.computeRange(bob,sheryl));
		
		//check that we can get the attack strength
        assertEquals(5, bob.getAttackStrength()); 
        
        //if Bob attacks Sheryl, Sheryl should take 5 points of damage
        bob.attack(sheryl,env.computeRange(bob,sheryl)); 
        assertEquals(45, sheryl.getCurrentLifePoints()); 
        
        //if Sheryl attacks Bob, Bob should take 7 points of damage
        sheryl.attack(bob, env.computeRange(bob,sheryl)); 
        assertEquals(33, bob.getCurrentLifePoints()); 
        
        env.removeLifeForm(1,1);
        env.removeLifeForm(1,2);
	}

	
	/**
	 * makes sure that a LifeForm with zero life point does no damage
	 */
	@Test
	public void testDeadCantAttack()
	{
		MockLifeForm bob;
		MockLifeForm sheryl; 
		
		//Bob is dead
		bob = new MockLifeForm("Bob", 0, 5); 
		sheryl = new MockLifeForm("Sheryl", 50, 7); 
		
		Environment env = Environment.getInstance(5,5);
		env.addLifeForm(bob, 1, 1);
		env.addLifeForm(sheryl, 1, 2);
		assertEquals(10, env.computeRange(bob,sheryl));
	
        //if Bob attacks Sheryl, Sheryl should take 0 points of damage
        bob.attack(sheryl, env.computeRange(bob,sheryl)); 
        assertEquals(50, sheryl.getCurrentLifePoints()); 
        
        env.removeLifeForm(1,1);
        env.removeLifeForm(1,2);
	}

/************ LAB 2 (Strategy) TESTS BELOW THIS POINT ******************/
	
 /** 
  * When a LifeForm is created, it should know its name and how
  * many life points it has. 
  */ 
    @Test 
    public void testInitialization() 
    { 
        MockLifeForm entity; 
        entity = new MockLifeForm("Bob", 40); 
        assertEquals("Bob", entity.getName()); 
        assertEquals(40, entity.getCurrentLifePoints()); 
    } 
    
    @Test
    public void testTakeHit()
    {
    	 MockLifeForm entity; 
         entity = new MockLifeForm("Bob", 40); 
         
         //take a 5 point hit 
         entity.takeHit(5); 
         assertEquals(35, entity.getCurrentLifePoints()); 
         
         //take a 1 point hit
         entity.takeHit(1); 
         assertEquals(34, entity.getCurrentLifePoints());
         
         //take a 34 point hit (down to 0 HP)
         entity.takeHit(34); 
         assertEquals(0, entity.getCurrentLifePoints());
         
         //take another hit (HP can't go below 0)
         entity.takeHit(5); 
         assertEquals(0, entity.getCurrentLifePoints());
    }
} 
