package state;

import static org.junit.Assert.*;
import lifeform.Human;
import lifeform.LifeForm;

import org.junit.Test;

/**
 * Tests AIContext functionality
 * @author Cassia Hulshizer
 *
 */
public class TestAIContext {

	/**
	 * Tests that the currentState can be set for AIContext
	 */
	@Test
	public void testInitialization() 
	{
		LifeForm ed = new Human("Ed", 10, 5);
		AIContext ai = new AIContext(ed);
		
		ActionState state = ai.getHasWeaponState();
		
		ai.setCurrentState(state);
		assertEquals(state, ai.getCurrentState());
	}
	
	/**
	 * Tests that the currentState can be changed for AIContext
	 */
	@Test
	public void testStateChange()
	{
		LifeForm bob = new Human("Bob", 10, 8);
		AIContext ai = new AIContext(bob);
		
		ActionState s1 = ai.getHasWeaponState();
		ActionState s2 = ai.getDeadState();
		
		ai.setCurrentState(s1);
		assertEquals(s1, ai.getCurrentState());
		
		ai.setCurrentState(s2);
		assertEquals(s2, ai.getCurrentState());
	}
	
	/**
	 * Tests that the getState methods work
	 */
	@Test
	public void testGetStates()
	{
		LifeForm joe = new Human("Joe", 10, 3);
		AIContext ai = new AIContext(joe);
		
		ActionState s1 = ai.getNoWeaponState();
		ActionState s2 = ai.getOutOfAmmoState();
		ActionState s3 = ai.getHasWeaponState();
		ActionState s4 = ai.getDeadState();
		
		ai.setCurrentState(s1);
		assertEquals(ai.getCurrentState(), ai.getNoWeaponState());
		
		ai.setCurrentState(s2);
		assertEquals(ai.getCurrentState(), ai.getOutOfAmmoState());
		
		ai.setCurrentState(s3);
		assertEquals(ai.getCurrentState(), ai.getHasWeaponState());
		
		ai.setCurrentState(s4);
		assertEquals(ai.getCurrentState(), ai.getDeadState());
	}

}
