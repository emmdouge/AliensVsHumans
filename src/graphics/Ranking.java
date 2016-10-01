package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lifeform.Lifeform;
/**
 * 
 * @author Emmanuel Douge
 *
 */
public class Ranking extends JLabel
{

	public static final int WIDTH = 800;
	public static final int HEIGHT = 75;
	private Font font;
	private ArrayList<Lifeform> allLifeforms;
	
	public Ranking()
	{
		font = new Font("bauhaus", Font.BOLD, 24);
		allLifeforms = new ArrayList<Lifeform>();
		drawFirstPlace();
	}
	
	public void clearAllLifeforms()
	{
		allLifeforms.clear();
	}
	
	/**
	 * call that will redraw the label
	 */
	public void drawRanking ()
	{
		invalidate();
		drawFirstPlace();
		revalidate();
	}
	/**
	 * draws the scoreboard as a JLabel and returns it
	 * @return
	 */
	public void drawFirstPlace()
	{
		BufferedImage exampleImage = new BufferedImage(Ranking.WIDTH, Ranking.HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		Graphics drawer = exampleImage.getGraphics();
		
		drawer.setColor(Color.BLACK);
		drawer.fillRect(0, 0, Ranking.WIDTH, Ranking.HEIGHT);
		
		drawer.setColor(Color.WHITE);
		
		int highestNumKills = 0;
		for(int x = 0; x < allLifeforms.size(); x++)
		{
			if(allLifeforms.get(x).getNumKills() > highestNumKills)
			{
				highestNumKills = allLifeforms.get(x).getNumKills();
			}
		}
		
		int borderOffsetX = 15;
		int borderOffsetY = 50;
		String rank = "1st Place with " + highestNumKills + " kills : ";
		if(highestNumKills == 0)
		{
			rank += "Unranked";
		}
		else
		{
			for(int i = 0; i < allLifeforms.size(); i++)
			{
				if(highestNumKills == allLifeforms.get(i).getNumKills())
				{
					rank = rank + allLifeforms.get(i).getName() + " ";
				}
			}
		}
		
		drawer.setFont(font);
		drawer.drawChars(rank.toCharArray(), 0, rank.length(), borderOffsetX, borderOffsetY);
		
		setIcon(new ImageIcon(exampleImage));
	}
	
	/**
	 * stores lifeforms for the score board
	 */
	public void addLifeForm(Lifeform lifeform)
	{
		//checks to see if lifeform is in the list already
		boolean lifeformIsInList = false;
		for(int x = 0; x < allLifeforms.size(); x++)
		{
			if(lifeform.equals(allLifeforms.get(x)))
			{
				lifeformIsInList = true;
			}
		}
		//if not in the list adds it to the list
		if(!lifeformIsInList)
		{
			allLifeforms.add(lifeform);
		}
		drawRanking();
	}

	/**
	 * when called increase the kills for the lifeform
	 * @param killer
	 */
	public void killConfirm (Lifeform killer)
	{
		
		for(int x = 0; x < allLifeforms.size(); x++)
		{
			boolean killerFound = killer.equals(allLifeforms.get(x));
			if(killerFound)
			{
				allLifeforms.get(x).incrementKillCount();
			}
		}
		drawRanking();
	}

}
