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
 * @author Joshua Bartle
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
		
		addWeapons(humans, aliens);
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
			
				allAILifeforms[currentIndex] = new Alien("alien", 100, recoveryBehavior);
			
				e.addLifeForm(allAILifeforms[currentIndex], alienPosition.x, alienPosition.y);
				
				currentIndex++;
				aliens--;
			}
			
			//go down column
			alienPosition.y++;
			
			//once bottom is reached, go to right column
			if(alienPosition.y >= e.getColumns()-1)
			{
				alienPosition.x++;
			}	
		}
			
		
		//starts at bottom right block
		Point humanPosition = new Point(e.getRows()-1, e.getColumns()-1);
			
		while(humans > 0)
		{
			//each human has a random amount of armor
			allAILifeforms[currentIndex] = new Human("human", 100, (int)(Math.random() * 10));
			e.addLifeForm(allAILifeforms[currentIndex], humanPosition.x, humanPosition.y);
			
			//go up
			humanPosition.y--;
			
			//once top is reached, go to left column
			if(humanPosition.y <= 0)
			{
				humanPosition.x--;
			}
			
			currentIndex++;
			humans--;
		}
		
		// adds the lifeforms to the AIContext
		for(int i = 0; i < allAILifeforms.length; i++)
		{
			ai[i] = new AIContext(allAILifeforms[i]);
			GUI.getInstance().getRanking().addLifeForm(allAILifeforms[i]);
		}
		GUI.getInstance().getRanking().addLifeForm(e.getPlayer());
	}
	
	
	/**
	 * Method for placing random weapons on the map
	 * @param humans
	 * @param aliens
	 */
	public void addWeapons(int humans, int aliens){
		int mid = e.getColumns()/2;
		int count = 0;
		int r = 0;
		int rand = 0;
		
		for(int i = 0; i < weapon.length; i++)
		{
			//weapons are of a random type
			rand = (int)((Math.random()) * 3);
			if(rand == 0)
				weapon[count] = new Pistol();
			if(rand == 1)
				weapon[count] = new PlasmaCannon();
			if(rand == 2)
				weapon[count] = new ChainGun();
			
			e.addWeapon(weapon[count], r, mid);
			
			if(r >= e.getRows() - 1)
				r = -1;
			r++;
			count++;
		}
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
	public Lifeform getLifeform(int i) {
		return allAILifeforms[i];
	}
	
	public AIContext getAI(int i) {
		return ai[i];
	}
}