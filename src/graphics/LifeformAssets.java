package graphics;

import java.awt.Image;

import javax.swing.ImageIcon;

public class LifeformAssets 
{
	public static LifeformAssets instance = new LifeformAssets();
	
	//can only be accessed through instance because they are not static
	public HumanAssets human;
	public AlienAssets alien;
	public PlayerAssets player;
	
	public Image humanNorth;
	public Image humanEast;
	public Image humanSouth;
	public Image humanWest;

	public Image demonNorth;
	public Image demonSouth;
	public Image demonEast;
	public Image demonWest;
	
	public Image playerNorth;
	public Image playerSouth;
	public Image playerEast;
	public Image playerWest;
	
	public Image north;
	public Image south;
	public Image east;
	public Image west;


	
	private class HumanAssets extends LifeformAssets
	{
		public HumanAssets()
		{
			east = instance.humanEast;
			north = instance.humanNorth;
			west = instance.humanWest;
			south = instance.humanSouth;
		}
	}
	
	private class PlayerAssets extends LifeformAssets
	{
		public PlayerAssets()
		{
			east = instance.playerEast;
			north = instance.playerNorth;
			west = instance.playerWest;
			south = instance.playerSouth;
		}
	}
	
	private class AlienAssets extends LifeformAssets
	{
		public AlienAssets()
		{
			east = instance.demonEast;
			north = instance.demonNorth;
			west = instance.demonWest;
			south = instance.demonSouth;
		}
	}
	
	private LifeformAssets()
	{
		
	}
	
	public static void init()
	{
		instance.humanEast = new ImageIcon("assets/human/east.png").getImage();
		instance.humanNorth = new ImageIcon("assets/human/north.png").getImage();
		instance.humanWest = new ImageIcon("assets/human/west.png").getImage();
		instance.humanSouth = new ImageIcon("assets/human/south.png").getImage();
		
		instance.demonNorth = new ImageIcon("assets/demon/north.png").getImage();
		instance.demonSouth = new ImageIcon("assets/demon/south.png").getImage();
		instance.demonEast = new ImageIcon("assets/demon/east.png").getImage();
		instance.demonWest = new ImageIcon("assets/demon/west.png").getImage();

		instance.playerNorth = new ImageIcon("assets/player/north.png").getImage();
		instance.playerSouth = new ImageIcon("assets/player/south.png").getImage();
		instance.playerEast = new ImageIcon("assets/player/east.png").getImage();
		instance.playerWest = new ImageIcon("assets/player/west.png").getImage();
		
		instance.human = LifeformAssets.instance.new HumanAssets();
		instance.alien = LifeformAssets.instance.new AlienAssets();
		instance.player = LifeformAssets.instance.new PlayerAssets();
	}
}
