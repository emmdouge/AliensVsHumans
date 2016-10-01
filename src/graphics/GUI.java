package graphics;

import java.awt.BorderLayout;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import environment.Environment;

/**
 * @author Emmanuel Douge
 *
 */
public class GUI
{
	private static GUI instance = null;
	
	private JFrame screen;
	private JPanel content;
	private Invoker invoker;
	private Grid grid;
	
	private Ranking rank;
	
	/**
	 * Gives values to the variables that do the calculations for how big a cell is and add the labels and panels to the apropriate places.
	 * @param theEnvironment
	 */
	
	private GUI () 
	{
		
	}
	/**
	 * When called redraws the entire map on the frame.
	 */
	public void draw()
	{
		rank.drawRanking();
		
		//when something changes on the map you can call this function it repaints the map so that the changes are displayed
		grid.revalidate();
		grid.drawMap();
		grid.repaint();
		
		invoker.drawInstructions();
	}
	
	public Ranking getRanking()
	{
		return rank;
	}

	public static synchronized GUI getInstance()
	{
		if(instance == null)
		{
			instance = new GUI();
		}
		return instance;
	}
	
	public void init(Environment env) 
	{
		//created panel
		instance.content = new JPanel();
		
		//created labels
		instance.invoker = new Invoker();
		instance.grid = new Grid(Environment.getInstance());
		instance.rank = new Ranking();
		
		//set panel layout
		instance.content.setLayout(new BorderLayout());
		
		//added labels to panel
		instance.content.add(instance.rank, BorderLayout.NORTH);
		instance.content.add(instance.grid, BorderLayout.CENTER);		
		instance.content.add(instance.invoker, BorderLayout.SOUTH);
		
		//create screen
		instance.screen = new JFrame("Demons vs Humans");
		
		//set screen size
		int borderOffset = 20;
		instance.screen.setSize(Grid.WIDTH + borderOffset, Grid.HEIGHT + Invoker.HEIGHT + Ranking.HEIGHT + borderOffset);
		
		//added panel to screen
		instance.screen.add(instance.content);
		
		instance.screen.setLocationRelativeTo(null);
		instance.screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		instance.screen.setVisible(true);
	}
}
