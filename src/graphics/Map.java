package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lifeform.Alien;
import lifeform.Human;
import lifeform.LifeForm;
import weapon.Pistol;
import weapon.PowerBooster;
import weapon.Scope;
import weapon.Stabilizer;
import weapon.Weapon;
import environment.Environment;
import exceptions.AttachmentException;
import gameplay.TimerObserver;
/**
 * @author Chris Kjeldgaard
 *
 */
public class Map implements TimerObserver
{
	private static final int mapWidth = 800, mapHieght = 500;
	private static final int legendWidth = 150, legendHieght = 500;
	private Environment worldMap;
	private int cellWidth, cellHieght;
	private double percentX, percentY;
	private JFrame frame;
	private JPanel mapAndLegend;
	private JPanel invoker;
	private JLabel map;
	private JLabel legend;
	
	private scoreBoard table;
	private JLabel points;
	private JPanel board;
	/**
	 * Gives values to the variables that do the calculations for how big a cell is and add the labels and panels to the apropriate places.
	 * @param theEnvironment
	 */
	public Map (Environment theEnvironment) 
	{
		board = new JPanel();
		table = new scoreBoard();
		points = table.getLabel();
		board.add(points);
		
		//gets the size of the environment and calculates the size of each cell so that the cells don't overlap and are all displayed
		worldMap = theEnvironment;
		percentY = 1.0 / worldMap.getRows();
		percentX = 1.0 / worldMap.getColumns();
		cellWidth = (int) (mapWidth * percentX);
		cellHieght = (int) (mapHieght * percentY);
		
		//created the frame and gave it a size.
		frame = new JFrame("Alien VS Human");
		frame.setSize(mapWidth + legendWidth + 150, 650);

		//made the map and invoker panels
		mapAndLegend = new JPanel();
		invoker = new InvokerPanel();
		
		mapAndLegend.setLayout(new FlowLayout());

		//created the map display
		map = new JLabel(drawMap());
		try {
			legend = new JLabel(drawLegend());//makes the legend.
			//The code requires this to be surrounded with a try catch because I declare weapons with various attachments
			//When makeing the legend
		} catch (AttachmentException e) {
			e.printStackTrace();
		}
		
		//finalized the map and legend by adding both to the panel.
		mapAndLegend.add(map);
		mapAndLegend.add(legend);

		frame.add(mapAndLegend);
		frame.add(board, BorderLayout.WEST);
		//add the invoker and map to the frame so people can see them.
		frame.add(invoker, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	/**
	 * When called redraws the entire map on the frame.
	 */
	public void redraw()
	{
		table.redraw();
		//when something changes on the map you can call this function it repaints the map so that the changes are displayed
		map.revalidate();
		
		map.setIcon(drawMap());
		
		map.repaint();
	}
	
	/**
	 * takes the size of the cell and creates a map that 
	 * @return
	 */
	private ImageIcon drawMap()
	{
		BufferedImage exampleImage = new BufferedImage(mapWidth,mapHieght,BufferedImage.TYPE_3BYTE_BGR);
		Graphics drawer = exampleImage.getGraphics();
		
		drawer.setColor(Color.BLACK);
		drawer.fillRect(0, 0, mapWidth, mapHieght);
		drawer.setColor(Color.CYAN);
		drawer.fillRect(mapWidth, 0, legendWidth, legendHieght);
		drawer.setColor(Color.GREEN);
		drawer.fillRect(0,mapHieght,mapWidth+legendWidth,100);
		
		for(int a = 0; a < worldMap.getRows(); a++)
		{
			for(int b = 0; b < worldMap.getColumns(); b++)
			{
				drawCell(drawer, a, b);
			}
		}
		
		return new ImageIcon(exampleImage);
	}
	/**
	 * Takes the row and col coordinate of the cell to be drawn.  Draws the for that cell and called the LifeForm and weapon
	 * that would inhabit that cell. 
	 * @param draw
	 * @param col
	 * @param row
	 */
	private void drawCell (Graphics draw, int col, int row)
	{
		//takes which cell is currently being drawn generates a backdrop
		draw.setColor(Color.LIGHT_GRAY);
		draw.fillRect(col*cellWidth, row*cellHieght, cellWidth, cellHieght);
		draw.setColor(Color.BLACK);
		draw.fillRect(col*cellWidth, row*cellHieght, cellWidth, 1);
		draw.fillRect(col*cellWidth, row*cellHieght, 1, cellHieght);
		draw.fillRect(col*cellWidth, row*cellHieght+cellHieght-1, cellWidth, 1);
		draw.fillRect(col*cellWidth+cellWidth-1, row*cellHieght, 1, cellHieght);
		
		draw.setColor(Color.BLACK);
		String coordinates = "(" + col + "," + row + ")";
		
		draw.drawChars(coordinates.toCharArray(), 0, coordinates.length(), (int)((col+.02)*cellWidth), (int)((row+.95)*cellHieght));
		//sends lifeForm and the location for the LifeForm to be drawn at.
		if(worldMap.getLifeForm(row, col) != null)
		{
			drawLifeForm(draw, col, row, worldMap.getLifeForm(row, col));
		}
		//passes the location for the weapon and the weapon to the method that draws weapons.
		if(worldMap.getWeapon(row, col, 1) != null)
		{
			drawWpn(draw, col*cellWidth, row*cellHieght, worldMap.getWeapon(row, col, 1));
		}
		if(worldMap.getWeapon(row, col, 2) != null)
		{
			drawWpn(draw, col*cellWidth+2*cellWidth/3, row*cellHieght, worldMap.getWeapon(row,col, 2));
		}
	}
	/**
	 * Takes a LifeForm its row and column then draws a Human or Alien at the square on the map.
	 * @param draw
	 * @param col
	 * @param row
	 * @param subject
	 */
	private void drawLifeForm(Graphics draw, int col, int row, LifeForm subject)
	{
		//gets the lifePoints of the LifeForm and prints them to the screen
		draw.setColor(Color.BLACK);
		String life = "hp " + subject.getCurrentLifePoints();
		char[] hp = life.toCharArray();
		draw.drawChars(hp, 0, hp.length, (int)((col+.40)*cellWidth), (int)((row+.25)*cellHieght));
		draw.setColor(Color.WHITE);
		//checks if the LifeForm is human
		if(subject instanceof Human)
  		{	
			//checks direction its facing
  			if(subject.getDirection() == "north")
			{
  				//draws Human facing north
				int shiftX = col * cellWidth;
				int shiftY = row * cellHieght;
				int nPoints = 3;
				int[] xPoints = new int[] {shiftX + (int)(cellWidth/2), shiftX + (int)(cellWidth*1/3), shiftX + (int)(cellWidth*2/3)};
				int[] yPoints = new int[] {shiftY + (int)(cellHieght/3), shiftY + (int)(cellHieght/2), shiftY + (int)(cellHieght/2)};
				draw.fillPolygon(xPoints, yPoints, nPoints);
				draw.fillRect(shiftX + cellWidth*3/8, shiftY+cellHieght/2, cellWidth/4, cellHieght/4);
				
				if(subject.hasWeapon())
				{
					//checks if the Human has a weapon and if it does passes it to be drawn.
					drawWpn(draw, col*cellWidth+cellWidth/2-cellWidth/6, row*cellHieght+cellHieght/2, subject.getWeapon());
				}
			}
			else if(subject.getDirection() == "south")
			{
				//draws human facing south
				int shiftX = col * cellWidth+cellWidth;
				int shiftY = row * cellHieght+cellHieght;
				int nPoints = 3;
				int[] xPoints = new int[] {shiftX - (int)(cellWidth/2), shiftX - (int)(cellWidth*1/3), shiftX - (int)(cellWidth*2/3)};
				int[] yPoints = new int[] {shiftY - (int)(cellHieght/3), shiftY - (int)(cellHieght/2), shiftY - (int)(cellHieght/2)};
				draw.fillPolygon(xPoints, yPoints, nPoints);
				draw.fillRect(shiftX - cellWidth*3/8-cellWidth/4, shiftY-cellHieght/2-cellHieght/4, cellWidth/4, cellHieght/4);
				
				if(subject.hasWeapon())
				{
					//checks for weapon and passes it to be drawn
					drawWpn(draw, col*cellWidth+cellWidth/2+cellWidth/6, row*cellHieght+cellHieght/2, subject.getWeapon());
				}
			}
			else if(subject.getDirection() == "east")
			{
				//draws human facing east
				int shiftX = col * cellWidth;
				int shiftY = row * cellHieght;
				int nPoints = 3;
				int[] xPoints = new int[] {shiftX + (int)(cellWidth*3/4), shiftX + (int)(cellWidth/2), shiftX + (int)(cellWidth/2)};
				int[] yPoints = new int[] {shiftY + (int)(cellHieght/2), shiftY + (int)(cellHieght/3), shiftY + (int)(cellHieght*2/3)};
				draw.fillPolygon(xPoints, yPoints, nPoints);
				draw.fillRect(shiftX + cellWidth/4, shiftY+cellHieght*3/8, cellWidth/4, cellHieght/4);
				
				if(subject.hasWeapon())
				{
					//checks for weapon and passes it to be drawn
					drawWpn(draw, col*cellWidth+cellWidth/2-cellWidth/4, row*cellHieght+cellHieght/2-cellHieght/6, subject.getWeapon());
				}
			}
			else if(subject.getDirection() == "west")
			{
				//draws human facing west
				int shiftX = col * cellWidth;
				int shiftY = row * cellHieght;
				int nPoints = 3;
				int[] xPoints = new int[] {shiftX + (int)(cellWidth/4), shiftX + (int)(cellWidth/2), shiftX + (int)(cellWidth/2)};
				int[] yPoints = new int[] {shiftY + (int)(cellHieght/2), shiftY + (int)(cellHieght/3), shiftY + (int)(cellHieght*2/3)};
				draw.fillPolygon(xPoints, yPoints, nPoints);
				draw.fillRect(shiftX + cellWidth/2, shiftY+cellHieght*3/8, cellWidth/4, cellHieght/4);
				
				if(subject.hasWeapon())
				{
					//checks for weapon then passes to be drawn
					drawWpn(draw, col*cellWidth+cellWidth/2-cellWidth/15, row*cellHieght+cellHieght/2-cellHieght/6, subject.getWeapon());
				}
			}
		}	
		//checks to see if the LifeForm passed is an alien to be drawn.
		else if(subject instanceof Alien)
  		{
  			//draws alien facing north
  			if(subject.getDirection() == "north")
			{
				int shiftX = col * cellWidth;
				int shiftY = row * cellHieght;
				int nPoints = 3;
				int[] xPoints = new int[] {shiftX + (int)(cellWidth/2), shiftX + (int)(cellWidth*1/3), shiftX + (int)(cellWidth*2/3)};
				int[] yPoints = new int[] {shiftY + (int)(cellHieght/3), shiftY + (int)(cellHieght*2/3), shiftY + (int)(cellHieght*2/3)};
				draw.fillPolygon(xPoints, yPoints, nPoints);
				
				if(subject.hasWeapon())
				{
					//checks for weapon and passes it to be drawn
					drawWpn(draw, col*cellWidth+cellWidth/2-cellWidth/6, row*cellHieght+cellHieght/2, subject.getWeapon());
				}
			}
			else if(subject.getDirection() == "south")
			{
				//draws alien facing south
				int shiftX = col * cellWidth;
				int shiftY = row * cellHieght;
				int nPoints = 3;
				int[] xPoints = new int[] {shiftX + (int)(cellWidth/2), shiftX + (int)(cellWidth*1/3), shiftX + (int)(cellWidth*2/3)};
				int[] yPoints = new int[] {shiftY + (int)(cellHieght*2/3), shiftY + (int)(cellHieght/3), shiftY + (int)(cellHieght/3)};
				draw.fillPolygon(xPoints, yPoints, nPoints);
				
				if(subject.hasWeapon())
				{
					//checks for weapon and passes it to be drawn
					drawWpn(draw, col*cellWidth+cellWidth/2+cellWidth/6, row*cellHieght+cellHieght/2, subject.getWeapon());
				}
			}
			else if(subject.getDirection() == "east")
			{
				//draws an Alien facing east
				int shiftX = col * cellWidth;
				int shiftY = row * cellHieght;
				int nPoints = 3;
				int[] xPoints = new int[] {shiftX + (int)(cellWidth*1/3), shiftX + (int)(cellWidth*1/3), shiftX + (int)(cellWidth*2/3)};
				int[] yPoints = new int[] {shiftY + (int)(cellHieght/3), shiftY + (int)(cellHieght*2/3), shiftY + (int)(cellHieght/2)};
				draw.fillPolygon(xPoints, yPoints, nPoints);
				
				if(subject.hasWeapon())
				{
					//checks for weapon and passes it to be drawn
					drawWpn(draw, col*cellWidth+cellWidth/2-cellWidth/4, row*cellHieght+cellHieght/2-cellHieght/6, subject.getWeapon());
				}
			}
			else if(subject.getDirection() == "west")
			{
				//draws alien facing west.
				int shiftX = col * cellWidth;
				int shiftY = row * cellHieght;
				int nPoints = 3;
				int[] xPoints = new int[] {shiftX + (int)(cellWidth*2/3), shiftX + (int)(cellWidth*2/3), shiftX + (int)(cellWidth/3)};
				int[] yPoints = new int[] {shiftY + (int)(cellHieght/3), shiftY + (int)(cellHieght*2/3), shiftY + (int)(cellHieght/2)};
				draw.fillPolygon(xPoints, yPoints, nPoints);
				
				if(subject.hasWeapon())
				{
					//checks for weapon and passes one to be drawn
					drawWpn(draw, col*cellWidth+cellWidth/2-cellWidth/15, row*cellHieght+cellHieght/2-cellHieght/6, subject.getWeapon());
				}
			}
		}	
	}
	
	/**
	 * Takes a weapon and a location on the x and y coordinate system.  It draws a Weapon at that location.
	 * The X and Y coordinates are determined by if it is stored in a cell, LifeForm or second weapon in a cell.
	 * @param draw
	 * @param x distance in the x direction from the origin.
	 * @param y distance in the y direction from the origin.
	 * @param wpn the weapon to be drawn.
	 */
	private void drawWpn(Graphics draw, int x, int y, Weapon wpn)
	{
		if(wpn != null)
		{
			//passes the weapon to another method to get the color the weapon needs to be drawn as.
			colorAttachments(draw, wpn);
			int gunWidth = cellWidth/3;
			int gunHieght = cellHieght/3;
			
			if(wpn.toString().contains("Pistol"))
			{
				//draws a pistol
				draw.fillRect(x+gunWidth/6, y+2*gunHieght/6, 4*gunWidth/6, 2*gunHieght/6);
			}
			else if (wpn.toString().contains("ChainGun"))
			{
				//draws a ChainGun
				draw.fillRect(x+2*gunWidth/6, y+gunHieght/6, 2*gunWidth/6, 4*gunHieght/6);
			}
			else if (wpn.toString().contains("PlasmaCannon"))
			{
				//draws a PlasmaCannon
				draw.fillRect(x+2*gunWidth/6, y+gunHieght/6, 2*gunWidth/6, 4*gunHieght/6);
				draw.fillRect(x+gunWidth/6, y+2*gunHieght/6, 4*gunWidth/6, 2*gunHieght/6);
			}
			
			//draws the ammo indicator for the weapon
			double ammo = 1.0-((double)wpn.getCurrentAmmo())/((double)wpn.getMaxAmmo());
			draw.setColor(Color.RED);
			draw.fillRect(x+gunWidth-gunWidth/10, y, gunWidth/10, (int) (ammo*gunHieght));
			draw.setColor(Color.GREEN);
			draw.fillRect(x+gunWidth-gunWidth/10, (int) (y+(ammo*gunHieght)), gunWidth/10, (int) (gunHieght-(ammo*gunHieght)));
		}
	}
	/**
	 * Takes a Weapon and finds what the color that weapon is supposed to be based on the attachments that exist.
	 * @param draw
	 * @param wpn The weapon with attachments to find the color of
	 */
	private void colorAttachments(Graphics draw, Weapon wpn)
	{
		int r=0,g=0,b=0;
		//takes a weapons and uses RGB to determine the color of the Weapon
		//A scope add 125 to the red coloring.
		//A stabilizer adds 125 to the green coloring
		//A powerbooster adds 125 to the blue coloring
		if(wpn != null)
		{
			if(wpn.toString().contains("Scope") == true)
			{
				r+=125;
			}
			if(wpn.toString().contains("Stabilizer") == true)
			{
				g+=125;
			}
			if(wpn.toString().contains("PowerBooster") == true)
			{
				b+=125;
			}
			
			if(r+b+g < 125*wpn.getNumAttachments())
			{//checks for double attachments.  If 2 scopes are applied the red needs to be doubled.
				//Since only 2 attachments can be applied to a weapon double all the colors if the numbers don't add up works.
				r = r * 2;
				g = g * 2;
				b = b * 2;
			}
			//transforms the RGB to HSV so that the color can be set in the Graphics
			float[] hsv = new float[3];
			hsv = Color.RGBtoHSB(r, g, b, hsv);
			draw.setColor(Color.getHSBColor(hsv[0],hsv[1], hsv[2]));
		}
	}
	
	/**
	 * populates the legend with information the player should know.
	 * @return
	 * @throws AttachmentException 
	 */
	public ImageIcon drawLegend() throws AttachmentException
	{
		BufferedImage example = new BufferedImage(legendWidth,legendHieght,BufferedImage.TYPE_3BYTE_BGR);
		Graphics draw = example.getGraphics();
		
		//Draws a Rect for the whole legend
		draw.setColor(Color.GREEN);
		draw.fillRect(0, 0, legendWidth, legendHieght);

		//Writes out the name of the Human and draws its shape
		draw.setColor(Color.BLACK);
		char[] hum = new char[]{'H', 'u', 'm', 'a', 'n'};
		draw.drawChars(hum, 0, 5, 5, 15);
		draw.setColor(Color.WHITE);
		int nPoints = 3;
		int[] xPoints = new int[] {5 + (int)(80/2), 5 + (int)(80*1/3), 5 + (int)(80*2/3)};
		int[] yPoints = new int[] {5 + (int)(80/3), 5 + (int)(80/2), 5 + (int)(80/2)};
		draw.fillPolygon(xPoints, yPoints, nPoints);
		draw.fillRect(5 + 80*3/8, 5+80/2, 80/4, 80/4);
		
		//Writes out the name of the Aliens and draws its shape
		draw.setColor(Color.BLACK);
		char[] all = new char[]{'A', 'l', 'i', 'e', 'n'};
		draw.drawChars(all, 0, 5, 5, 80);
		draw.setColor(Color.WHITE);
		int nPoints1 = 3;
		int[] xPoints1 = new int[] {10 + (int)(80/2), 10 + (int)(80*1/3), 10 + (int)(80*2/3)};
		int[] yPoints1 = new int[] {60 + (int)(80/3), 60 + (int)(80*2/3), 60 + (int)(80*2/3)};
		draw.fillPolygon(xPoints1, yPoints1, nPoints1);
		
		//Writes out Pistol and draws its shape
		draw.setColor(Color.BLACK);
		char[] pistol = new char[]{'P', 'i', 's', 't', 'o', 'l'};
		draw.drawChars(pistol, 0, 6, 5, 140);
		draw.fillRect(10+80/6, 115+2*80/6, 4*80/6, 2*80/6);
		
		//Writes out the ChainGun and draws its shape
		char[] chain = new char[]{'C', 'h', 'a', 'i', 'n', 'g', 'u', 'n'};
		draw.drawChars(chain, 0, 8, 5, 190);
		draw.fillRect(10+2*80/6, 180+80/6, 2*80/6, 4*80/6);
		
		//Writes out PlasmaGun and draws its shape
		char[] plasm = new char[]{'P', 'l', 'a', 's', 'm', 'a'};
		draw.drawChars(plasm, 0, 6, 5, 260);
		draw.fillRect(10+2*80/6, 250+80/6, 2*80/6, 4*80/6);
		draw.fillRect(10+80/6, 250+2*80/6, 4*80/6, 2*80/6);
		
		//draws a different color block so the WeaponAttachment colors are visible.
		draw.setColor(Color.WHITE);
		draw.fillRect(10, 330, legendWidth, 132);
		
		//Titles the block
		draw.setColor(Color.BLACK);
		String attachment = "Attachment Color";
		draw.drawChars(attachment.toCharArray(), 0, attachment.length(), 5, 330);
		
		//Writes out no Attachments and displays that color in the word.
		colorAttachments(draw, new Pistol());
		String none = "no Attachments";
		draw.drawChars(none.toCharArray(), 0, none.length(), 15, 340);
		
		//Colors the text as if the attachment and Writes it out to the screen
		colorAttachments(draw,new Scope(new Pistol()));
		String scope = "Scope";
		draw.drawChars(scope.toCharArray(), 0, scope.length(), 15, 350);

		//Colors the text as if the attachment and Writes it out to the screen
		colorAttachments(draw, new Stabilizer(new Pistol()));
		String stabilizer = "Stabilzer";
		draw.drawChars(stabilizer.toCharArray(), 0, stabilizer.length(), 15, 360);

		//Colors the text as if the attachment and Writes it out to the screen
		colorAttachments(draw, new PowerBooster(new Pistol()));
		String powerBooster = "PowerBooster";
		draw.drawChars(powerBooster.toCharArray(), 0, powerBooster.length(), 15, 370);

		//Colors the text as if the attachment and Writes it out to the screen
		colorAttachments(draw,new Scope( new Scope(new Pistol())));
		String scopeScope = "Scope + Scope";
		draw.drawChars(scopeScope.toCharArray(), 0, scopeScope.length(), 15, 380);

		//Colors the text as if the attachment and Writes it out to the screen
		colorAttachments(draw,new Scope(new Stabilizer(new Pistol())));
		String scopeStabilizer = "Scope + Stabilizer";
		draw.drawChars(scopeStabilizer.toCharArray(), 0, scopeStabilizer.length(), 15, 390);

		//Colors the text as if the attachment and Writes it out to the screen
		colorAttachments(draw,new Scope(new PowerBooster (new Pistol())));
		String scopePowerBooster = "Scope + PowerBooster";
		draw.drawChars(scopePowerBooster.toCharArray(), 0, scopePowerBooster.length(), 15, 400);

		//Colors the text as if the attachment and Writes it out to the screen
		colorAttachments(draw, new Stabilizer(new Scope (new Pistol())));
		String stabilizerScope = "Stabilzer + Scope";
		draw.drawChars(stabilizerScope.toCharArray(), 0, stabilizerScope.length(), 15, 410);

		//Colors the text as if the attachment and Writes it out to the screen
		colorAttachments(draw, new Stabilizer(new Stabilizer(new Pistol())));
		String stabilizerStabilzer = "Stabilzer + Stabilizer";
		draw.drawChars(stabilizerStabilzer.toCharArray(), 0, stabilizerStabilzer.length(), 15, 420);

		//Colors the text as if the attachment and Writes it out to the screen
		colorAttachments(draw, new Stabilizer(new PowerBooster(new Pistol())));
		String stabilizerPowerBooster = "Stabilzer + PowerBooster";
		draw.drawChars(stabilizerPowerBooster.toCharArray(), 0, stabilizerPowerBooster.length(), 15, 430);

		//Colors the text as if the attachment and Writes it out to the screen
		colorAttachments(draw, new PowerBooster(new Scope(new Pistol())));
		String powerBoosterScope = "PowerBooster + Scope";
		draw.drawChars(powerBoosterScope.toCharArray(), 0, powerBoosterScope.length(), 15, 440);

		//Colors the text as if the attachment and Writes it out to the screen
		colorAttachments(draw, new PowerBooster(new Stabilizer(new Pistol())));
		String powerBoosterStabilizer = "PowerBooster + Stabilizer";
		draw.drawChars(powerBoosterStabilizer.toCharArray(), 0, powerBoosterStabilizer.length(), 15, 450);

		//Colors the text as if the attachment and Writes it out to the screen
		colorAttachments(draw, new PowerBooster(new PowerBooster(new Pistol())));
		String powerBoosterPowerBooster = "PowerBooster + PowerBooster";
		draw.drawChars(powerBoosterPowerBooster.toCharArray(), 0, powerBoosterPowerBooster.length(), 15, 460);
		
		return new ImageIcon(example);
	}
	
	public scoreBoard getScoreBoard()
	{
		return table;
	}

	/**
	 * Makes map a TimerObserver so that it would be called to redraw the map periodically.
	 * This makes changes that don't force a map redraw will still be displayed eventually as it allows 
	 * Examples: Attacks, Firing guns, Recovery, anything that doesn't call the Environment Class
	 */
	@Override
	public void updateTime(int time) {
		redraw();
	}
}
