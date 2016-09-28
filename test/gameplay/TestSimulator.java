package gameplay;

import static org.junit.Assert.*;

import javax.swing.JOptionPane;

import org.junit.Test;

import environment.Environment;
import exceptions.RecoveryRateException;

/**
 * Class for testing the functionality of the AI, specifically the Simulation for the AI
 * @author Joshua Bartle
 *
 */
public class TestSimulator {
	
	/**
	 * Tests that the world is populated with a specified number of aliens and humans and that
	 * there is a weapon for each
	 * @throws RecoveryRateException
	 * @throws InterruptedException
	 */
	@Test
	public void testPopulateWorld() throws RecoveryRateException, InterruptedException {
		int humans = 5;
		int aliens = 5;
		Environment e = Environment.getInstance(humans, aliens);
		e = Environment.getInstance();
		e.clearBoard();
		Simulator sim = new Simulator(humans, aliens);
		SimpleTimer t = new SimpleTimer(1000);
		t.addTimeObserver(sim);
		
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Is their " + (aliens + humans) + " LifeForms" + " and " + "a Weapon for each?"));
	
	}
	/**
	 * Tests that the AI will act according to their State. The State Starts at No weapon state
	 * and the life form will try to find a weapon
	 * @throws RecoveryRateException
	 * @throws InterruptedException
	 */
	@Test
	public void testAIUpdatesAllContexts() throws RecoveryRateException, InterruptedException{
		int humans = 5;
		int aliens = 5;
		Environment e = Environment.getInstance(humans, aliens);
		e = Environment.getInstance();
		e.clearBoard();
		Simulator sim = new Simulator(humans, aliens);
		SimpleTimer t = new SimpleTimer(1000);
		t.addTimeObserver(sim);
		
		t.start();
		for(int i = 0; i < 30; i++)
		{
			if(i == 5){
				assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
						(null, "Are the LifeForms moving to find a weapon?"));
			}
			
			if(i == 20){
				assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
						(null, "Are the LifeForms attacking and taking damage?"));
			}
				
			Thread.sleep(1000);
		}
	}
	/**
	 * Tests that a time update will trigger an AI Update
	 * @throws RecoveryRateException
	 */
	@Test
	public void testTimeUpdateTriggersAI() throws RecoveryRateException{
		int humans = 5;
		int aliens = 5;
		Environment e = Environment.getInstance(humans, aliens);
		e = Environment.getInstance();
		e.clearBoard();
		Simulator sim = new Simulator(humans, aliens);
		SimpleTimer t = new SimpleTimer(1000);
		t.addTimeObserver(sim);
		
		t.timeChanged();
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Time has changed to " + t.getRound()+ ". Did the AI Update?"));
		t.timeChanged();
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Time has changed to " + t.getRound()+ ". Did the AI Update?"));
		t.timeChanged();
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Time has changed to " + t.getRound()+ ". Did the AI Update?"));
	}
}
