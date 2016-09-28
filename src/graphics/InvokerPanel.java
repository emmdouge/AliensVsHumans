package graphics;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

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
public class InvokerPanel extends JPanel implements ActionListener, KeyListener
{
	/**
	 * Instance variables
	 */
	protected static JButton btnMove;
	protected static JButton btnTurn;
	protected static JButton btnReload;
	protected static JButton btnAcquire;
	protected static JButton btnDrop;
	protected static JButton btnAttack;
	private JDialog dlgTurn;
	private Command command;
	
	/**
	 * Create the panel.
	 */
	public InvokerPanel() 
	{
		setBackground(new Color(25, 25, 112));
		setPreferredSize(new Dimension(900, 100));
		createButtons();
		//add buttons created
		add(btnMove);		
		add(btnTurn);		
		add(btnReload);
		add(btnAcquire);
		add(btnDrop);
		add(btnAttack);
	}
	
	/**
	 * Creates each of the buttons present in the panel
	 * (Move, Turn, Reload, Acquire, Drop, & Attack)
	 */
	public void createButtons()
	{
		//Move Button
		btnMove = new JButton("Move");
		btnMove.setBackground(new Color(255, 255, 51));
		btnMove.setPreferredSize(new Dimension(135, 90));
		btnMove.addActionListener(this);
		
		//Turn Button
		btnTurn = new JButton("Turn");
		btnTurn.setBackground(new Color(255, 102, 0));
		btnTurn.setPreferredSize(new Dimension(135, 90));
		btnTurn.addActionListener(this);
		
		//Reload Button
		btnReload = new JButton("Reload");
		btnReload.setBackground(new Color(51, 255, 0));
		btnReload.setPreferredSize(new Dimension(135, 90));
		btnReload.addActionListener(this);
		
		//Acquire Button
		btnAcquire = new JButton("Acquire");
		btnAcquire.setBackground(new Color(204, 0, 255));
		btnAcquire.setPreferredSize(new Dimension(135, 90));
		btnAcquire.addActionListener(this);
		
		//Drop Button
		btnDrop = new JButton("Drop");
		btnDrop.setBackground(new Color(0, 153, 255));
		btnDrop.setPreferredSize(new Dimension(135, 90));
		btnDrop.addActionListener(this);
		
		//Attack Button
		btnAttack = new JButton("Attack");
		btnAttack.setBackground(new Color(255, 51, 51));
		btnAttack.setPreferredSize(new Dimension(135, 90));
		btnAttack.addActionListener(this);
	}

	/**
	 * Handles all the ButtonPushed events
	 */
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if (event.getSource() == btnMove)
		{
			int distance = getDistance();
			
			command = new MoveCommand(distance);
			command.execute();
		}
		else if (event.getSource() == btnTurn)
		{
			dlgTurn = new TurnDirections();
			dlgTurn.setVisible(true);
		}
		else if (event.getSource() == btnReload)
		{
			command = new ReloadCommand();
			command.execute();
		}
		else if (event.getSource() == btnAcquire)
		{
			command = new AcquireCommand();
			command.execute();
		}
		else if (event.getSource() == btnDrop)
		{
			command = new DropCommand();
			command.execute();
		}
		else if (event.getSource() == btnAttack)
		{
			command = new AttackCommand();
			command.execute();
		}
		
	}
	
	/**
	 * Shows an input dialog for the user to input the distance they would like to move
	 * @return
	 */
	public int getDistance()
	{
		int distance = Integer.parseInt(JOptionPane.showInputDialog(null, 
				"Please input the distance you would like to move (1, 2, or 3): ", 
				"How far?", JOptionPane.INFORMATION_MESSAGE));
		
		return distance;
	}
	
	/**
	 * Handles all the KeyTyped events.
	 */
	@Override
	public void keyTyped(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
			//Turn West
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				command = new TurnWestCommand();
				break;
			//Turn North
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				command = new TurnNorthCommand();
				break;
			//Turn East
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				command = new TurnEastCommand();
				break;
			//Turn South
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				command = new TurnSouthCommand();
				break;
			//Reload
			case KeyEvent.VK_R:
				command = new ReloadCommand();
				break;
			//Acquire
			case KeyEvent.VK_E:
				command = new AcquireCommand();
				break;
			//Drop
			case KeyEvent.VK_F:
				command = new DropCommand();
				break;
			//Move
			case KeyEvent.VK_1:
				command = new MoveCommand(1);
				break;
			case KeyEvent.VK_2:
				command = new MoveCommand(2);
				break;
			case KeyEvent.VK_3:
				command = new MoveCommand(3);
				break;
			default:
				break;
		}
		command.execute();
	}

	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		//
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		//
	}
}
