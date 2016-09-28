package graphics;

import static org.junit.Assert.*;

import javax.swing.JOptionPane;

import lifeform.Human;
import lifeform.LifeForm;

import org.junit.Test;

import environment.Environment;
/**
 * 
 * @author Chris Kjeldgaard
 *
 */
public class testScoreBoard {

	/**
	 * test adding LifeForms to the list and then tests adding kills to that LifeForm
	 */
	@Test
	public void test() {
		Environment map = Environment.getInstance(5, 5);
		LifeForm test = null;
		//adds 5 "Unique" named Lifeforms
		for(int x = 0; x < 5; x++)
		{
			test = new Human(x+"Test",5,5);
			map.addLifeForm(test, x, x);
		}
		
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Are there 5 humans on the score board?"));
		//increments one of their kills
		for(int x = 0; x < 5; x++)
		{
			map.getScoreBoard().killConfirm(test);
		}
		map.reDraw();
		assertEquals(JOptionPane.YES_OPTION,JOptionPane.showConfirmDialog
				(null, "Did one just get 5 kills and get moved to the top of the scoreboard?"));
	}

}
