package gameplay;

import java.awt.Point;

import environment.Environment;
import exceptions.RecoveryRateException;
import graphics.GUI;
import recovery.*;
import state.AIContext;
import weapon.*;
import lifeform.*;
/**
 * Class for creating a simulator for AI
 * @author Emmanuel Douge
 *
 */
public class Simulator implements TimerObserver
{

	private Lifeform allAILifeforms[];
	private AIContext ai[];
	private Environment e;
	private Weapon weapon[];
	
	public Simulator(int humans, int aliens) throws RecoveryRateException
	{
		e = Environment.getInstance();
		
		int totalNumOfLifeforms = aliens + humans;
		
		allAILifeforms = new Lifeform[totalNumOfLifeforms];
		ai = new AIContext[totalNumOfLifeforms];
		weapon = new Weapon[totalNumOfLifeforms];
		
		int currentIndex = 0;
		
		//starts at top left block
		Point alienPosition = new Point(0, 0);
		while(aliens > 0)
		{
			//no lifeform is occupying this spot
			if(e.getLifeForm(alienPosition.x, alienPosition.y) == null)
			{
				// random recovery types are set for each alien
				double rand = Math.random() * 3;
				RecoveryBehavior recoveryBehavior = null;
				if(rand < 1)
				{
					recoveryBehavior = new RecoveryNone();
				}	
				if(1 < rand && rand < 2)
				{
					recoveryBehavior = new RecoveryLinear(3);
				}
				if(2 < rand)
				{
					recoveryBehavior = new RecoveryFractional(.1);
				}
			
				allAILifeforms[currentIndex] = new Alien("alien"+aliens, 1, recoveryBehavior);
			
				e.addLifeForm(allAILifeforms[currentIndex], alienPosition.x, alienPosition.y);
				
				currentIndex++;
				aliens--;
			}
			
			//go right
			alienPosition.y++;
			
		}
			
		
		//starts at bottom right block
		Point humanPosition = new Point(e.getRows()-1, e.getColumns()-1);
			
		while(humans > 0)
		{
			if(e.getLifeForm(humanPosition.x, humanPosition.y) == null)
			{
				//each human has a random amount of armor
				allAILifeforms[currentIndex] = new Human("human"+humans, 100, (int)(Math.random() * 10));
				e.addLifeForm(allAILifeforms[currentIndex], humanPosition.x, humanPosition.y);
				
	
			
				currentIndex++;
				humans--;
			}	
			
			//go left
			humanPosition.y--;
		}
		
		// adds the lifeforms to the AIContext
		for(int i = 0; i < allAILifeforms.length; i++)
		{
			ai[i] = new AIContext(allAILifeforms[i]);
			if(!allAILifeforms[i].isAlien())
			{
				GUI.getInstance().getRanking().addLifeForm(allAILifeforms[i]);
			}
		}
		GUI.getInstance().getRanking().addLifeForm(e.getPlayer());
	}
	
	/**
	 * Method for updating time in the simulator, also updates the AI
	 * @param the current time
	 */
	@Override
	public void update(int time) 
	{
		System.out.println("TEST");
		for(int i = 0; i < allAILifeforms.length; i++)
		{
			ai[i].execute();
			if(ai[i].getLifeForm().hasWeapon() == true)
			{
				ai[i].getLifeForm().getWeapon().update(time);
			}
			if(ai[i].getLifeForm().isAlien() == true)
			{
				Alien alien = (Alien) ai[i].getLifeForm();
				alien.update(time);
			}
		}
	}
	
	public Lifeform getLifeform(int i) 
	{
		return allAILifeforms[i];
	}
	
	public AIContext getAI(int i) 
	{
		return ai[i];
	}
}