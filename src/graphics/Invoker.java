package graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.KeyStroke;

import command.AcquireCommand;
import command.AttackCommand;
import command.Command;
import command.DropCommand;
import command.MoveCommand;
import command.ReloadCommand;
import command.TurnEastCommand;
import command.TurnNorthCommand;
import command.TurnSouthCommand;
import command.TurnWestCommand;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.ArrayList;

/**
 * A Panel that holds all of the buttons that a user will interact with
 * to invoke commands for playing the game.
 * @author Emmanuel Douge
 *
 */
@SuppressWarnings("serial")
public class Invoker extends JLabel
{
	/**
	 * Instance variables
	 */
	private JLabel btnMove;
	private JLabel btnReload;
	private JLabel btnAcquire;
	private JLabel btnDrop;
	private JLabel btnAttack;
	private JLabel btnTurnN;
	private JLabel btnTurnS;
	private JLabel btnTurnE;
	private JLabel btnTurnW;
	
	private ArrayList<JLabel> allButtons;
	
	//width and height of jcomponent will always be zero until rendered
	//so they have been added as instance variables
	public static final int HEIGHT = 100;
	public static final int WIDTH = 800;
	/**
	 * Create the panel.
	 */
	public Invoker() 
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		createButtons();
		
		//add buttons created		
		add(btnTurnS);
		add(btnTurnN);
		add(btnTurnE);
		add(btnTurnW);
		add(btnReload);
		add(btnAcquire);
		add(btnDrop);
		add(btnAttack);
		add(btnMove);
		
		
		allButtons = new ArrayList<JLabel>();
		allButtons.add(btnAcquire);
		allButtons.add(btnMove);
		allButtons.add(btnReload);
		allButtons.add(btnAttack);
		allButtons.add(btnDrop);
		allButtons.add(btnTurnN);
		allButtons.add(btnTurnS);
		allButtons.add(btnTurnW);
		allButtons.add(btnTurnE);
	}
	
	/**
	 * Creates each of the buttons present in the panel
	 * (Move, Turn, Reload, Acquire, Drop, & Attack)
	 */
	public void createButtons()
	{
		//Move Button
		btnMove = new JLabel("Move - Press Shift ");
		btnMove.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, 0, true), "move");
		btnMove.getActionMap().put("move", new MoveCommand(1));
		
		//Turn Button
		btnTurnS = new JLabel("Turn South - Press S ");
		btnTurnS.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "turnSouth");
		btnTurnS.getActionMap().put("turnSouth", new TurnSouthCommand());
		
		btnTurnN = new JLabel("Turn North - Press W ");
		btnTurnN.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "turnNorth");
		btnTurnN.getActionMap().put("turnNorth", new TurnNorthCommand());
		
		btnTurnE = new JLabel("Turn East - Press A ");
		btnTurnE.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "turnEast");
		btnTurnE.getActionMap().put("turnEast", new TurnEastCommand());
		
		btnTurnW = new JLabel("Turn West - Press D ");
		btnTurnW.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "turnWest");
		btnTurnW.getActionMap().put("turnWest", new TurnWestCommand());
		
		//Reload Button
		btnReload = new JLabel("Reload - Press R ");
		btnReload.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0, true), "reload");
		btnReload.getActionMap().put("reload", new ReloadCommand());
		
		//Acquire Button
		btnAcquire = new JLabel("Acquire - Press E ");
		btnAcquire.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, true), "acquire");
		btnAcquire.getActionMap().put("acquire", new AcquireCommand());
		
		//Drop Button
		btnDrop = new JLabel("Drop - Press Q ");
		btnDrop.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, true), "drop");
		btnMove.getActionMap().put("drop", new DropCommand());
		
		//Attack Button
		btnAttack = new JLabel("Attack - Press F ");
		btnMove.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0, true), "attack");
		btnMove.getActionMap().put("attack", new AttackCommand());
	}
	
	public void drawInstructions()
	{
		BufferedImage image = new BufferedImage(Invoker.WIDTH, Invoker.HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		Graphics drawer = image.getGraphics();
		
		drawer.setColor(Color.WHITE);
		drawer.fillRect(0, 0, Invoker.WIDTH, Invoker.HEIGHT);
		
		drawer.setColor(Color.BLACK);
		
		int borderOffsetX = 20;
		int borderOffsetY = 20;
		String instructions = "How to Play: \n";
		
		//starts at one to avoid newlining the first instruction because 0 % 3 = 0
		for(int i = 1; i < allButtons.size(); i++)
		{
			instructions += allButtons.get(i).getText();
			if(i % 3 == 0)
			{
				instructions += "\n";
			}
		}
		for(String instruction: instructions.split("\n"))
		{
			drawer.drawString(instruction, borderOffsetX, borderOffsetY += drawer.getFontMetrics().getHeight());
		}
		setIcon(new ImageIcon(image));
	}

}
