package graphics;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import command.Command;
import command.TurnEastCommand;
import command.TurnNorthCommand;
import command.TurnSouthCommand;
import command.TurnWestCommand;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

/**
 * A dialog that will pop up allowing the user to choose in which
 * direction he/she would like to turn.
 * This pops up when InvokerPanel.btnTurn is clicked. 
 * @author Cassia Hulshizer
 *
 */
@SuppressWarnings("serial")
public class TurnDirections extends JDialog implements ActionListener
{
	/**
	 * Instance variables
	 */
	private final JPanel contentPanel = new JPanel();
	protected static JButton btnNorth;
	protected static JButton btnEast;
	protected static JButton btnSouth;
	protected static JButton btnWest;
	protected static Command command;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		try 
		{
			TurnDirections dialog = new TurnDirections();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TurnDirections() 
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(TurnDirections.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
		setBounds(100, 100, 350, 100);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 153, 0));
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblPrompt = new JLabel("Please choose the direction to turn below:");
		contentPanel.add(lblPrompt);
				
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(255, 153, 0));
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		createButtons();
		//add created buttons to buttonPane
		buttonPane.add(btnNorth);
		buttonPane.add(btnEast);
		buttonPane.add(btnSouth);
		buttonPane.add(btnWest);
	}
	
	/**
	 * Creates each of the buttons for Turning
	 * (North, East, South, & West)
	 */
	public void createButtons()
	{
		//North Button
		btnNorth = new JButton("North");
		btnNorth.setBackground(new Color(255, 204, 153));
		btnNorth.setPreferredSize(new Dimension(70, 23));
		btnNorth.addActionListener(this);
		
		//East Button
		btnEast = new JButton("East");
		btnEast.setBackground(new Color(255, 204, 153));
		btnEast.setPreferredSize(new Dimension(70, 23));
		btnEast.addActionListener(this);
		
		//South Button
		btnSouth = new JButton("South");
		btnSouth.setBackground(new Color(255, 204, 153));
		btnSouth.setPreferredSize(new Dimension(70, 23));
		btnSouth.addActionListener(this);
		
		//West Button
		btnWest = new JButton("West");
		btnWest.setBackground(new Color(255, 204, 153));
		btnWest.setPreferredSize(new Dimension(70, 23));
		btnWest.addActionListener(this);
	}

	/**
	 * Handles the ButtonPushed events
	 */
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if (event.getSource() == btnNorth)
		{
			command = new TurnNorthCommand();
		}
		else if (event.getSource() == btnEast)
		{
			command = new TurnEastCommand();
		}
		else if (event.getSource() == btnSouth)
		{
			command = new TurnSouthCommand();
		}
		else if (event.getSource() == btnWest)
		{
			command = new TurnWestCommand();
		}
		command.execute();
		this.setVisible(false);
	}
}
