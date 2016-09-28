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
public class TestStabilizer {



	/**
	 * tests wrapping a Stabilizer around a PlasmaCannon
	 * @throws WeaponException 
	 */
	@Test
	public void testStabilizerPlasmaCannon() throws AttachmentException, WeaponException {
		Stabilizer stabilizerPlasma = new Stabilizer(new PlasmaCannon()); 
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(stabilizerPlasma);
		timer.timeChanged();
		
		//check that the number of attachments was updated
		assertEquals(1, stabilizerPlasma.getNumAttachments()); 
		
		//check toString()
		assertEquals("PlasmaCannon +Stabilizer", stabilizerPlasma.toString()); 
		
		//fire the PlasmaCannon once at something in range (30)
		//power weakens with each shot
		//damage should be 50*100% = 50
		//with the stabilizer, damage should be 50*(1.25) = 62
		assertEquals(62, stabilizerPlasma.fire(15)); 
		timer.timeChanged();
		
		//test at base.maxRange (40)
		//PlasmaCannon damage is 50*75% = 37
		//with Stabilizer: 37*1.25 = 46
		assertEquals(46, stabilizerPlasma.fire(20)); 
		timer.timeChanged();
		
		//test out of base.maxRange (45)
		//PlasmaCannon damage is 0
		//with stabilizer boost = 0*1.25 = 0
		assertEquals(0, stabilizerPlasma.fire(45)); 
		timer.timeChanged();
		
		//test at base.maxRange (40)
		//PlasmaCannon damage is 50*25% = 12
		//with Stabilizer: 12*1.25 = 15
		assertEquals(15, stabilizerPlasma.fire(20)); 
		timer.timeChanged();
		
		//check that the PlasmaCannon automatically reloaded
		assertEquals(4, stabilizerPlasma.getCurrentAmmo()); 
		
		//test out of scope range (70)
		assertEquals(0, stabilizerPlasma.fire(35)); 
		timer.timeChanged();
	}
}
