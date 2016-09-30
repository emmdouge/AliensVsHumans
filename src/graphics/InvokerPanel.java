package graphics;

import javax.swing.JComponent;
import javax.swing.JDialog;
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
import java.awt.event.KeyEvent;
import java.awt.Color;

/**
 * A Panel that holds all of the buttons that a user will interact with
 * to invoke commands for playing the game.
 * @author Cassia Hulshizer
 *
 */
@SuppressWarnings("serial")
public class InvokerPanel extends JPanel
{
	/**
	 * Instance variables
	 */
	protected static JButton btnMove;
	protected static JButton btnReload;
	protected static JButton btnAcquire;
	protected static JButton btnDrop;
	protected static JButton btnAttack;
	protected static JButton btnTurnN;
	protected static JButton btnTurnS;
	protected static JButton btnTurnE;
	protected static JButton btnTurnW;
	/**
	 * Create the panel.
	 */
	public InvokerPanel() 
	{
		setBackground(new Color(25, 25, 112));
		setPreferredSize(new Dimension(900, 100));
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
	}
	
	/**
	 * Creates each of the buttons present in the panel
	 * (Move, Turn, Reload, Acquire, Drop, & Attack)
	 */
	public void createButtons()
	{
		//Move Button
		btnMove = new JButton("Move");
		btnMove.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, 0, true), "move");
		btnMove.getActionMap().put("move", new MoveCommand(1));
		
		//Turn Button
		btnTurnS = new JButton("Turn S");
		btnTurnS.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "turnSouth");
		btnTurnS.getActionMap().put("turnSouth", new TurnSouthCommand());
		
		btnTurnN = new JButton("Turn N");
		btnTurnN.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "turnNorth");
		btnTurnN.getActionMap().put("turnNorth", new TurnNorthCommand());
		
		btnTurnE = new JButton("Turn E");
		btnTurnE.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "turnEast");
		btnTurnE.getActionMap().put("turnEast", new TurnEastCommand());
		
		btnTurnW = new JButton("Turn W");
		btnTurnW.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "turnWest");
		btnTurnW.getActionMap().put("turnWest", new TurnWestCommand());
		
		//Reload Button
		btnReload = new JButton("Reload");
		btnReload.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0, true), "reload");
		btnReload.getActionMap().put("reload", new ReloadCommand());
		
		//Acquire Button
		btnAcquire = new JButton("Acquire");
		btnAcquire.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, true), "acquire");
		btnAcquire.getActionMap().put("acquire", new AcquireCommand());
		
		//Drop Button
		btnDrop = new JButton("Drop");
		btnDrop.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, true), "drop");
		btnMove.getActionMap().put("drop", new DropCommand());
		
		//Attack Button
		btnAttack = new JButton("Attack");
		btnMove.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0, true), "attack");
		btnMove.getActionMap().put("attack", new AttackCommand());
	}

}
