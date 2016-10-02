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

import environment.Environment;
import exceptions.RecoveryRateException;
import lifeform.Alien;
import lifeform.Lifeform;
/**
 * 
 * @author Emmanuel Douge
 *
 */
public class Ranking extends JLabel
{

	public static final int WIDTH = 800;
	public static final int HEIGHT = 100;
	private Font font;
	private Lifeform[] allLifeforms;
	private int addedLifeforms = 0;
	public Ranking()
	{
		//for(int i = 0; i < Environment.getInstance().getNumAliens()+Environment.getInstance().getNumHumans()+1)
		
		allLifeforms = new Lifeform[Environment.getInstance().getNumAliens()+Environment.getInstance().getNumHumans()+1];
		for(int i = 0; i < allLifeforms.length; i++)
		{
			try {
				allLifeforms[i] = new Alien("placeholder", 1);
			} catch (RecoveryRateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		font = new Font("serif", Font.ITALIC, 24);
		//drawFirstPlace();
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
		drawer.setFont(font);
		
		int highestNumKills = -1;
		for(int x = 0; x < allLifeforms.length; x++)
		{
			if(allLifeforms[x].getNumKills() > highestNumKills)
			{
				highestNumKills = allLifeforms[x].getNumKills();
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
			for(int i = 0; i < allLifeforms.length; i++)
			{
				if(allLifeforms[i].getNumKills() == highestNumKills)
				{
					rank += allLifeforms[i].getName();
					System.out.println(rank);
				}
			}
		}
	
		drawer.drawChars(rank.toCharArray(), 0, rank.length(),borderOffsetX, borderOffsetY);
		
		System.out.println(highestNumKills);
		String playersPlace = "You are ";
		int place = getPlayersRank();
		String suffix = "";
		if(place == 0)
		{
			suffix = "last place";
			playersPlace += suffix;
		}
		else
		{
			if(place == 1)
			{
				suffix = "st";
			}
			else if(place == 2)
			{
				suffix = "nd";
			}
			else if(place == 3)
			{
				suffix = "rd";
			}
			else
			{
				suffix = "th";
			}
			playersPlace += place + suffix;
		}
		playersPlace += " with " + Environment.getInstance().getPlayer().getNumKills() + " kills";
		

		drawer.drawString(playersPlace, borderOffsetX, borderOffsetY + drawer.getFontMetrics().getHeight());
		setIcon(new ImageIcon(exampleImage));
	}
	
	/**
	 * sorts lifeforms to get players rank
	 * @return
	 */
	private int getPlayersRank() 
	{
		int playersRank = 0;
		//sorts in descending number of kills
		for(int iteration = 0; iteration < allLifeforms.length; iteration++)
		{
			for(int currentIndex = 0; currentIndex < allLifeforms.length-1; currentIndex++)
			{
				Lifeform currentLifeform = allLifeforms[currentIndex];
				Lifeform nextLifeform = allLifeforms[currentIndex+1];
				if(currentLifeform.getNumKills() < nextLifeform.getNumKills())
				{
					Lifeform temp = currentLifeform;
					allLifeforms[currentIndex] = nextLifeform;
					allLifeforms[currentIndex+1] = temp;
				}
			}
		}
		
		for(int i = 0; i < allLifeforms.length; i++)
		{
			if(allLifeforms[i].isPlayer)
			{
				playersRank = i+1;
			}
			System.out.println(i + " " + allLifeforms[i].getName()+ " " + allLifeforms[i].getNumKills());
		}
		if(Environment.getInstance().getPlayer().getNumKills() == 0)
		{
			playersRank = 0;
		}
		return playersRank;
	}

	/**
	 * stores lifeforms for the score board
	 */
	public void addLifeForm(Lifeform lifeform)
	{
		//checks to see if lifeform is in the list already
		boolean lifeformIsInList = false;
		for(int x = 0; x < allLifeforms.length; x++)
		{
			if(lifeform.equals(allLifeforms[x]))
			{
				lifeformIsInList = true;
			}
		}
		//if not in the list adds it to the list
		if(!lifeformIsInList)
		{
			allLifeforms[addedLifeforms] = lifeform;
			addedLifeforms++;
		}
		//drawRanking();
	}
}
