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
	private ArrayList<Lifeform> allLifeforms;
	
	public Ranking()
	{
		font = new Font("serif", Font.ITALIC, 24);
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
		
		drawer.setFont(font);
		drawer.drawString(rank,borderOffsetX, borderOffsetY);
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
		for(int iteration = 0; iteration < allLifeforms.size(); iteration++)
		{
			for(int currentIndex = 0; currentIndex < allLifeforms.size()-1; currentIndex++)
			{
				Lifeform currentLifeform = allLifeforms.get(currentIndex);
				Lifeform nextLifeform = allLifeforms.get(currentIndex+1);
				if(currentLifeform.getNumKills() < nextLifeform.getNumKills())
				{
					Lifeform temp = currentLifeform;
					allLifeforms.set(currentIndex, nextLifeform);
					nextLifeform = temp;
				}
			}
		}
		
		for(int i = 0; i < allLifeforms.size(); i++)
		{
			if(allLifeforms.get(i).isPlayer)
			{
				playersRank = i+1;
			}
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
