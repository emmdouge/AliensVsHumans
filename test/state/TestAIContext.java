package state;

import static org.junit.Assert.*;
import lifeform.Human;
import lifeform.Lifeform;

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
		Lifeform ed = new Human("Ed", 10, 5);
		AIContext ai = new AIContext(ed);
		
		ActionState state = ai.getFightingState();
		
		ai.setCurrentState(state);
		assertEquals(state, ai.getCurrentState());
	}
	
	/**
	 * Tests that the currentState can be changed for AIContext
	 */
	@Test
	public void testStateChange()
	{
		Lifeform bob = new Human("Bob", 10, 8);
		AIContext ai = new AIContext(bob);
		
		ActionState s1 = ai.getFightingState();
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
		Lifeform joe = new Human("Joe", 10, 3);
		AIContext ai = new AIContext(joe);
		
		ActionState s1 = ai.getNeedWeaponState();
		ActionState s2 = ai.getOutOfAmmoState();
		ActionState s3 = ai.getFightingState();
		ActionState s4 = ai.getDeadState();
		
		ai.setCurrentState(s1);
		assertEquals(ai.getCurrentState(), ai.getNeedWeaponState());
		
		ai.setCurrentState(s2);
		assertEquals(ai.getCurrentState(), ai.getOutOfAmmoState());
		
		ai.setCurrentState(s3);
		assertEquals(ai.getCurrentState(), ai.getFightingState());
		
		ai.setCurrentState(s4);
		assertEquals(ai.getCurrentState(), ai.getDeadState());
	}

}
