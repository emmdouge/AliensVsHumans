/**
 * 
 */
package weapon;

import static org.junit.Assert.*;
import exceptions.AttachmentException;
import exceptions.WeaponException;
import gameplay.SimpleTimer;

import org.junit.Test;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class TestScope {
	
	@Test
	public void testScopeStabilizerPistol() throws AttachmentException, WeaponException
	{
		Stabilizer stabilizerPistol = new Stabilizer(new Pistol()); 
		Scope scopeStabilizerPistol = new Scope(stabilizerPistol); 
		
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(scopeStabilizerPistol);
		timer.timeChanged();
		
		//check that the number of attachments was updated
		assertEquals(2, scopeStabilizerPistol.getNumAttachments()); 
		//check that the clip is full
		assertEquals(10, scopeStabilizerPistol.getCurrentAmmo()); 
		
		//check that the maxRange has been updated
		assertEquals(35, scopeStabilizerPistol.getMaxRange()); 
		
		//check toString()
		assertEquals("Pistol +Stabilizer +Scope", scopeStabilizerPistol.toString()); 
		
		//check that the damage is amped appropriately
		//fire the stabilizerPistol once at something in range (30)
		//damage should be 10*((50-30+10)/50)*1.25 = 7
		//with the scope, damage should be 7+(1+((60-30)/60)) =10
		assertEquals(10, scopeStabilizerPistol.fire(30));
		assertEquals(9, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test at base.maxRange (50)
		//Pistol damage is 2
		//with stabilizer: 2*1.25 = 2
		//with Scope: 2*(1+(1/5)) = 2.4 = 2
		assertEquals(6, scopeStabilizerPistol.fire(25)); 
		assertEquals(8, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test at edge of scope range (60)
		assertEquals(10, scopeStabilizerPistol.fire(30)); 
		assertEquals(7, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test out of scope range (70)
		assertEquals(0, scopeStabilizerPistol.fire(70)); 
		assertEquals(6, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test out of scope range (70)
		assertEquals(0, scopeStabilizerPistol.fire(70)); 
		assertEquals(5, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//clip size is 10, and we have shot 5
		//shot 4
		assertEquals(15, scopeStabilizerPistol.fire(15));
		assertEquals(4, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		assertEquals(15, scopeStabilizerPistol.fire(15));
		assertEquals(3, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		assertEquals(15, scopeStabilizerPistol.fire(15));
		assertEquals(2, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		assertEquals(15, scopeStabilizerPistol.fire(15));
		//check that clip is at 1
		assertEquals(1, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//shoot last buller
		//stabilizer should reload the clip
		assertEquals(15, scopeStabilizerPistol.fire(15));
		assertEquals(10, scopeStabilizerPistol.getCurrentAmmo()); 
	}
}
