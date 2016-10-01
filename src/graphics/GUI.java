package graphics;

import java.awt.BorderLayout;

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
	
	private ScoreBoard scoreBoard;
	
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
	public void redraw()
	{
		scoreBoard.redraw();
		
		//when something changes on the map you can call this function it repaints the map so that the changes are displayed
		grid.revalidate();
		
		grid.drawMap();
		
		grid.repaint();
	}
	
	public ScoreBoard getScoreBoard()
	{
		return scoreBoard;
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
		instance.scoreBoard = new ScoreBoard();
		
		//made the map and invoker panels
		instance.content = new JPanel();
		instance.invoker = new Invoker();
		instance.grid = new Grid(Environment.getInstance());
		
		instance.content.add(instance.grid, BorderLayout.CENTER);		
		instance.content.add(instance.invoker, BorderLayout.PAGE_END);
		
		instance.screen = new JFrame("Demons vs Humans");
		
		int borderOffset = 20;
		instance.screen.setSize(Grid.gridWidth + borderOffset, Grid.gridHeight + Invoker.invokerHeight + borderOffset);
		
		instance.screen.add(instance.content);
		instance.screen.setLocationRelativeTo(null);
		instance.screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		instance.screen.setVisible(true);
	}
}
