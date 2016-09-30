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
public class TestPowerBooster {

	/**
	 * tests wrapping a PowerBooster around a ChainGun
	 * 
	 * @throws WeaponException 
	 * @throws AttachmentException 
	 */
	@Test
	public void testPowerBoosterChainGun() throws AttachmentException, WeaponException {
		PowerBooster powerChain = new PowerBooster(new ChainGun()); 
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(powerChain);
		timer.update();
		
		//check that the number of attachments was updated
		assertEquals(1, powerChain.getNumAttachments()); 
		
		//check toString()
		assertEquals("ChainGun +PowerBooster", powerChain.toString()); 
		
		//fire the ChainGun once at something in range (15)
		//damage should be 7 = 15*30/60
		//with the powerBooster, damage should be 7*(1+(40/40) = 14
		assertEquals(14, powerChain.fire(15)); 
		timer.update();
		
		//test at base.maxRange (30)
		//ChainGun damage is max: 15
		//with PowerBooster: 15*(1+(39/40) = 29
		assertEquals(29, powerChain.fire(30)); 
		timer.update();
		
		//test out of scope range (45)
		assertEquals(0, powerChain.fire(45)); 
		timer.update();
		
		//fire a bunch of times, then make sure that the powerboost is responding
		for(int i = 0; i < 15; i++)
		{
			powerChain.fire(15); 
			timer.update();
		}
		
		//test at base.maxRange (30)
		//ChainGun damage is max: 15
		//with PowerBooster: 15*(1+(22/40) = 23
		assertEquals(23, powerChain.fire(30)); 
		timer.update();
		
		//fire a bunch of times, then make sure that the powerboost is responding
		for(int i = 0; i < 15; i++)
		{
			powerChain.fire(30); 
			timer.update();
		}
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with PowerBooster: 15*(1+(7/40) = 17
		assertEquals(17, powerChain.fire(30)); 
		timer.update();
	}
}
