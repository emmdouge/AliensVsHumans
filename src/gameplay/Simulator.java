package gameplay;

import environment.Environment;
import exceptions.RecoveryRateException;
import recovery.*;
import state.AIContext;
import weapon.*;
import lifeform.*;
/**
 * Class for creating a simulator for AI
 * @author Joshua Bartle
 *
 */
public class Simulator implements TimerObserver{

	private int time;
	private LifeForm lifeform[];
	private AIContext AIControl[];
	private Environment e;
	private Weapon weapon[];
	
	public Simulator(int humans, int aliens) throws RecoveryRateException{
		e = Environment.getInstance();
		e.clearBoard();
		
		RecoveryBehavior recoverType = null;
		lifeform = new LifeForm[humans + aliens];
		AIControl = new AIContext[humans + aliens];
		weapon = new Weapon[humans + aliens];
		int x = 0, y = 0;
		int total = aliens + humans;
		addWeapons(humans, aliens);
		int i = 0;
		while(i < total){
			while(aliens > 0){
				// random recovery types are set for each alien
				double rand = Math.random() * 3;
				if(rand < 1)
					recoverType = new RecoveryNone();
				if(rand >= 1 && rand < 2)
					recoverType = new RecoveryLinear(3);
				if(rand >= 2)
					recoverType = new RecoveryFractional(.1);
				lifeform[i] = new Alien("alien", 100, recoverType);
				e.addLifeForm(lifeform[i], y, x);
				if(y >= e.getRows() -1){
					y = -1;
					x++;
				}
				y++;
				aliens--;
				i++;
			}
			x = e.getColumns() - 1; y = e.getRows() - 1;
			while( humans > 0){
				//each human has a random amount of armor
				lifeform[i] = new Human("human", 100, (int)(Math.random() * 10));
				e.addLifeForm(lifeform[i], y, x);
				humans--;
				if( y <= 0){
					y = e.getRows();
					x--;
				}
				y--;
				i++;
			}
		}
		// adds the lifeforms to the AIContext
		for(i = 0; i < lifeform.length; i++){
			AIControl[i] = new AIContext(lifeform[i]);
		}
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
	for(int i = 0; i < weapon.length; i++){
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
	public void updateTime(int time) {
		this.time = time;
		System.out.println("TEST");
		for(int i = 0; i < lifeform.length; i++)
		{
			AIControl[i].execute();
			if(AIControl[i].getLifeForm().hasWeapon() == true)
			{
				AIControl[i].getLifeForm().getWeapon().updateTime(time);
			}
			if(AIControl[i].getLifeForm().isAlien() == true)
			{
				Alien alien = (Alien) AIControl[i].getLifeForm();
				alien.updateTime(time);
			}
		}
	}
	public LifeForm getLifeform(int i) {
		return lifeform[i];
	}
	
	public AIContext getAI(int i) {
		return AIControl[i];
	}
}