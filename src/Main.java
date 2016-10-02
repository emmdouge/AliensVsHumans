import lifeform.Alien;
import lifeform.Human;
import environment.Environment;
import exceptions.RecoveryRateException;
import gameplay.Timer;
import gameplay.Simulator;
import graphics.LifeformAssets;


public class Main {

	public static void main(String[] args) throws RecoveryRateException 
	{
		LifeformAssets.init();
		
		int humans = 5;
		int aliens = 5;
		int rows = 10;
		int cols = 10;
		
		Environment e = Environment.getInstance();
		e.initBlocks(rows, cols, humans, aliens);
		e.setPlayer(new Human("BoyWonder", 1000, 0), 5, 5);
		e.initUI();
		Simulator sim = new Simulator(e.getNumHumans(), e.getNumAliens());

		
		
		
		Timer gameLoop = new Timer(250);
		gameLoop.addTimeObserver(sim);
		gameLoop.start();
	}

}
