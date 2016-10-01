package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import weapon.Weapon;
import lifeform.Direction;
import lifeform.Lifeform;
import environment.Environment;

public class Grid extends JLabel
{
	private Environment env;
	private Image background;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	private static int blockWidth;
	private static int blockHeight;
	public Grid(Environment env)
	{
		this.env = env;
		blockWidth = WIDTH/env.getRows();
		blockHeight = HEIGHT/env.getColumns();
		background = new ImageIcon("assets/background/background.png").getImage();
		drawMap();
	}
	
	//dont get girdWidth and gridHeight mixed up with blockwidth and blockheight
	/**
	 * takes the size of the cell and creates a map that 
	 * @return
	 */
	public void drawMap()
	{
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		Graphics drawer = image.getGraphics();

		drawer.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
		for(int a = 0; a < Environment.getInstance().getRows(); a++)
		{
			for(int b = 0; b < Environment.getInstance().getColumns(); b++)
			{
				drawBlock(drawer, a, b);
			}
		}
		
		setIcon(new ImageIcon(image));
	}
	
	/**
	 * Takes the row and col coordinate of the cell to be drawn.  Draws the for that cell and called the LifeForm and weapon
	 * that would inhabit that cell. 
	 * @param drawer
	 * @param col
	 * @param row
	 */
	private void drawBlock (Graphics drawer, int col, int row)
	{
		//takes which cell is currently being drawn generates a backdrop

		drawer.setColor(Color.BLACK);
		drawer.fillRect(col*blockWidth, row*blockHeight, blockWidth, 1);
		drawer.fillRect(col*blockWidth, row*blockHeight, 1, blockHeight);
		drawer.fillRect(col*blockWidth, row*blockHeight+blockHeight-1, blockWidth, 1);
		drawer.fillRect(col*blockWidth+blockWidth-1, row*blockHeight, 1, blockHeight);
		
		drawer.setColor(Color.BLACK);
		String coordinates = "(" + col + "," + row + ")";
		
		drawer.drawChars(coordinates.toCharArray(), 0, coordinates.length(), (int)((col+.02)*blockWidth), (int)((row+.95)*blockHeight));
		//sends lifeForm and the location for the LifeForm to be drawn at.
		if(env.getLifeForm(row, col) != null)
		{
			drawLifeForm(drawer, col, row, env.getLifeForm(row, col));
		}
		//passes the location for the weapon and the weapon to the method that draws weapons.
		if(env.getWeapon(row, col, 1) != null)
		{
			drawWeapon(drawer, col*blockWidth, row*blockHeight, env.getWeapon(row, col, 1));
		}
		if(env.getWeapon(row, col, 2) != null)
		{
			drawWeapon(drawer, col*blockWidth+2*blockWidth/3, row*blockHeight, env.getWeapon(row,col, 2));
		}
	}
	
	private void drawWeapon
	(Graphics draw, int i, int j, Weapon weapon) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Takes a LifeForm its row and column then draws a Human or Alien at the square on the map.
	 * @param draw
	 * @param col
	 * @param row
	 * @param lifeform
	 */
	private void drawLifeForm(Graphics draw, int col, int row, Lifeform lifeform)
	{
		//gets the lifePoints of the LifeForm and prints them to the screen
		draw.setColor(Color.BLACK);
		
		String life = "hp " + lifeform.getCurrentLifePoints();
		char[] hp = life.toCharArray();
		draw.drawChars(hp, 0, hp.length, (int)((col+.40)*blockWidth), (int)((row+.25)*blockHeight));
		
		draw.setColor(Color.WHITE);
		
		int shiftX = col * blockWidth;
		int shiftY = row * blockHeight;
		
		LifeformAssets lifeformAssets;
		
		if(lifeform.isAlien())
		{
			lifeformAssets = LifeformAssets.instance.alien;
		}
		else if(lifeform.isPlayer)
		{
			lifeformAssets = LifeformAssets.instance.player;
		}
		else
		{
			lifeformAssets = LifeformAssets.instance.human;
		}
		
		//checks direction its facing
  		if(lifeform.getDirection() == Direction.NORTH)
		{
  			draw.drawImage(lifeformAssets.north, shiftX, shiftY, blockWidth, blockHeight, null);
			if(lifeform.hasWeapon())
			{
				//checks if the Human has a weapon and if it does passes it to be drawn.
				drawWeapon(draw, col*blockWidth+blockWidth/2-blockWidth/6, row*blockHeight+blockHeight/2, lifeform.getWeapon());
			}
		}
		else if(lifeform.getDirection() == Direction.SOUTH)
		{
			draw.drawImage(lifeformAssets.south, shiftX, shiftY, blockWidth, blockHeight, null);
			
			if(lifeform.hasWeapon())
			{
				//checks for weapon and passes it to be drawn
				drawWeapon(draw, col*blockWidth+blockWidth/2+blockWidth/6, row*blockHeight+blockHeight/2, lifeform.getWeapon());
			}
		}
		else if(lifeform.getDirection() == Direction.EAST)
		{
			draw.drawImage(lifeformAssets.east, shiftX, shiftY, blockWidth, blockHeight, null);			
			if(lifeform.hasWeapon())
			{
				//checks for weapon and passes it to be drawn
				drawWeapon(draw, col*blockWidth+blockWidth/2-blockWidth/4, row*blockHeight+blockHeight/2-blockHeight/6, lifeform.getWeapon());
			}	
		}
		else if(lifeform.getDirection() == Direction.WEST)
		{
			draw.drawImage(lifeformAssets.west, shiftX, shiftY, blockWidth, blockHeight, null);
			
			if(lifeform.hasWeapon())
			{
				//checks for weapon then passes to be drawn
				drawWeapon(draw, col*blockWidth+blockWidth/2-blockWidth/15, row*blockHeight+blockHeight/2-blockHeight/6, lifeform.getWeapon());
			}
		}		
	}
}
