package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lifeform.Lifeform;
/**
 * 
 * @author Chris Kjeldgaard
 *
 */
public class scoreBoard 
{
	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<Lifeform> identity = new ArrayList<Lifeform>();
	private ArrayList<Integer> kills = new ArrayList<Integer>();
	private JLabel board;
	
	public JLabel getLabel()
	{
		redraw();
		return board;
	}
	
	public scoreBoard()
	{
		board = new JLabel(scoreCount());
	}
	
	public void clear()
	{
		names.clear();
		kills.clear();
		identity.clear();
	}
	
	/**
	 * call that will redraw the label
	 */
	public void redraw ()
	{
		sort();
		board.invalidate();
		board.setIcon(scoreCount());
		board.revalidate();
	}
	/**
	 * draws the scoreboard as a JLabel and returns it
	 * @return
	 */
	private ImageIcon scoreCount ()
	{
		BufferedImage exampleImage = new BufferedImage(100,800,BufferedImage.TYPE_3BYTE_BGR);
		Graphics drawer = exampleImage.getGraphics();
		
		drawer.setColor(Color.YELLOW);
		drawer.fillRect(0, 0, 100, 800);
		
		drawer.setColor(Color.BLACK);
		String ScoreTitle = "Kills  Title";
		drawer.drawChars(ScoreTitle.toCharArray(), 0, ScoreTitle.length(), 15, 20);
		
		String row;
		for(int x=0; x<kills.size(); x++)
		{
			row = kills.get(x) + "";
			while(row.length() < 5)
			{
				row = "0" + row;
			}
			row = row + "  " + names.get(x);
			drawer.drawChars(row.toCharArray(), 0, row.length(), 15, 30+10*x);
		}
		
		return new ImageIcon(exampleImage);
	}
	
	/**
	 * stores lifeforms for the score board
	 */
	public void addLifeForm(Lifeform killer)
	{
		boolean flag = false;
		//checks to see if lifeform is in the list already
		for(int x = 0; x < identity.size(); x++)
		{
			if(killer.equals(identity.get(x)))
			{
				flag = true;
			}
		}
		//if not in the list adds it to the list
		if(flag == false)
		{
			names.add(killer.getName());
			identity.add(killer);
			kills.add(0);
		}
		redraw();
	}
	/**
	 * sorts the lifeforms by kills highest to lowest
	 */
	private void sort()
	{
		Lifeform tempLifeForm;
		String tempName;
		int tempKills;
		//organizes the list
		for(int x = 0; x < kills.size(); x++)
		{
			for(int y = 0; y < kills.size()-1; y++)
			{
				if(kills.get(y)<kills.get(y+1))
				{
					tempLifeForm = identity.get(y);
					tempName = names.get(y);
					tempKills = kills.get(y);
					
					identity.set(y, identity.get(y+1));
					names.set(y, names.get(y+1));
					kills.set(y, kills.get(y+1));

					identity.set(y+1, tempLifeForm);
					names.set(y+1, tempName);
					kills.set(y+1, tempKills);
				}
			}
		}
	}
	/**
	 * when called increase the kills for the lifeform
	 * @param killer
	 */
	public void killConfirm (Lifeform killer)
	{
		boolean flag = false;
		for(int x = 0; x < identity.size(); x++)
		{
			if(killer.equals(identity.get(x)))
			{
				flag = true;
				kills.set(x, kills.get(x)+1);
			}
		}
		//if the killer is not in the list adds it then gives it the first kill
		if(flag == false)
		{
			addLifeForm(killer);
			killConfirm(killer);
		}
		redraw();
	}

}
