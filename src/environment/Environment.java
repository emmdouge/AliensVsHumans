package environment;
import graphics.Map;
import graphics.scoreBoard;

import java.awt.Point;

import weapon.Weapon;
import lifeform.LifeForm;

/**
 * @author Dr. Alice Armstrong
 * @author Brandon Shawver - Command Lab & Move (North & East)
 * @author Josh Bartle - Command Lab & Move (South & West)
 */
public class Environment 
{
	
	private Cell[][] cells; //the 2D array of cells in the environment
    protected int rows;
    protected int columns;
    private static Environment map = null;
    private LifeForm player;
    private static Map UI;
    
    
    public scoreBoard getScoreBoard()
    {
    	return UI.getScoreBoard();
    }
    /**
     * Constructs an environment for the LifeFroms
     * @param r Number of rows.
     * @param c Number of columns.
     */
    private Environment(int r, int c)
    {
        rows = r;
        columns = c;
        cells = new Cell[rows][columns];
        
        for (int x=0;x<rows;x++)
        {
            for (int y=0;y<columns;y++)
            {
                cells[x][y] = new Cell();
            }
        }
    }
    
    /**
     * Returns the active instance of the Environment. Checks and synchronizes
     * the instance of environment so there can only be one.
     * @return
     */
    public static Environment getInstance(int r, int c)
    {
    	if(map == null)
    	{
    		synchronized(Environment.class)
    		{
    			if(map == null)
    			{
    				map = new Environment(r, c);
    				UI = new Map(map);
    				return map;
    			}
    		}
    	}
    	return map;
    }
    
    /**
     * Returns the active instance of Environment.
     * @return
     */
    public static Environment getInstance()
    {
    	return map;
    }

    /**
     * Returns how many rows are in the Environment.
     * @return
     */
    public int getRows()
    {
        return rows;
    }

    /**
     * Returns how many columns are in the Environment.
     * @return
     */
    public int getColumns()
    {
        return columns;
    }
    
    /**
     * Adds a weapon to the environment
     * @param weapon
     * @param row
     * @param col
     * @return
     */
	public void addWeapon(Weapon weapon, int row, int col)
	{
		cells[row][col].setWeapon(weapon); 
		UI.redraw();
	}
	
	/**
	 * removes a weapon from the environment
	 * @param row
	 * @param col
	 */
	public void removeWeapon(int row, int col)
	{
		cells[row][col].setWeapon(null); 
		UI.redraw();
	}
	/**
	 * method for adding the player to be controlled
	 * @param player
	 * @param row
	 * @param column
	 */
	public void addPlayer(LifeForm player, int row, int column){
		if(this.player == null){
			addLifeForm(player, row, column);
			this.player = player;
		}
		UI.redraw();
	}
	/**
	 * returns a weapon
	 * @param row
	 * @param col
	 * @return
	 */
	public Weapon getWeapon(int row, int col)
	{
		return cells[row][col].getWeapon(); 
	}
	
	/**
	 * returns a specific weapon
	 * @author Cassia Hulshizer
	 * @param row
	 * @param col
	 * @param weapon
	 * @return
	 */
	public Weapon getWeapon(int row, int col, int weapon)
	{
		if (weapon == 1)
			return cells[row][col].getWeaponOne();
		else if (weapon == 2)
			return cells[row][col].getWeaponTwo();
		else
			return null;
	}
	
    /**
     * If there is space it places the weapon in the weaponTwo slot.
     * @param weapon
     */
    public void setWeaponTwo(int row, int col, Weapon weapon)
    {
            cells[row][col].setWeaponTwo(weapon);
    }
	
	/**
	 * Finds the distance between two lifeforms
	 * @param life1
	 * @param life2
	 * @return
	 */
    public int computeRange(LifeForm life1, LifeForm life2)
    {
        Point p1 = findLifeForm(life1);
        Point p2 = findLifeForm(life2);
        
        int distance = (int)Math.round(Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)*100 + (p1.y-p2.y)*(p1.y-p2.y)*100));
        return distance;
    }
    
    /**
     * Finds a LifeForm in the environment
     * @param life
     * @return
     */
    public Point findLifeForm(LifeForm life)
    {
        for (int x=0;x<rows;x++)
        {
            for (int y=0;y<columns;y++)
            {
                if (cells[x][y].getLifeForm() == life)
                    return new Point(x,y);
            }
        }
        return null;
    }
    
	/**
	 * returns a specified cell in the Environment
	 * @param row
	 * @param col
	 */
	public LifeForm getLifeForm(int row, int col)
	{
		return cells[row][col].getLifeForm(); 
	}

	/**
	 * adds a LifeForm to a Cell in the Environment
	 * @param entity
	 * @param row
	 * @param col
	 * @return true is LifeForm was added
	 */
	public boolean addLifeForm(LifeForm entity, int row, int col)
	{
		//send lifeform added to the map to the scoreboard to add if needed
		UI.getScoreBoard().addLifeForm(entity);
		//adds lifeforms to the map
		if(cells[row][col].addLifeForm(entity))
		{
			UI.redraw();
			return true;
		}
		return false; 
	}
	/**
	 * method for removing lifeform
	 * @param row
	 * @param col
	 */
	public void removeLifeForm(int row, int col)
	{
		cells[row][col].removeLifeForm(); 
		UI.redraw();
	}
	/**
	 * @return the current player
	 */
	public LifeForm getPlayer(){
		return player;
	}
	
	/**
	 * Method for moving lifeforms around the environment
	 * @param entity that needs to move
	 */
	public void Move(LifeForm entity, int speed)
	{
		int r = getRows();
		int c = getColumns();
		String direction = entity.getDirection();
		
		
		if(speed > entity.getMaxSpeed())
		{
			entity.setCurrentSpeed(entity.getCurrentSpeed());
		}
		else
		{
			entity.setCurrentSpeed(speed);
		}
		
		if(direction == "north")
		{
			for(int i = 0; i < r; i++)
			{
				for(int j = 0; j < c; j++)
				{
					if(entity == getLifeForm(i, j) && i - entity.getCurrentSpeed() >= 0 && getLifeForm(i - entity.getCurrentSpeed(), j) == null)
					{
							removeLifeForm(i, j);
							System.out.println(i + ", " + j);
							addLifeForm(entity, i - entity.getCurrentSpeed(), j);
					}
				}
			}
		}
		
		if(direction == "south")
		{
			for(int i = r - 1; i >= 0; i--)
			{
				for(int j = c - 1; j >= 0; j--)
				{
					if(entity == getLifeForm(i, j) && i + entity.getCurrentSpeed() < r && getLifeForm(i + entity.getCurrentSpeed(), j) == null)
					{
							removeLifeForm(i, j);
							System.out.println(i + ", " + j);
							addLifeForm(entity, i + entity.getCurrentSpeed(), j);
					}
				}
			}
		}
		
		if(direction == "east")
		{
			for(int i = 0; i < r; i++)
			{
				for(int j = c - 1; j >= 0; j--)
				{
					if(entity == getLifeForm(i, j) && j + entity.getCurrentSpeed() < r && getLifeForm(i, j + entity.getCurrentSpeed()) == null)
					{
							removeLifeForm(i, j);
							System.out.println(i + ", " + j);
							addLifeForm(entity, i, j  + entity.getCurrentSpeed());
					}
				}
			}
		}
		
		if(direction == "west")
		{
			for(int i = 0; i < r; i++)
			{
				for(int j = 0; j < c; j++)
				{
					if(entity == getLifeForm(i, j) && j - entity.getCurrentSpeed() >= 0 && getLifeForm(i, j - entity.getCurrentSpeed()) == null)
					{
							removeLifeForm(i, j);
							System.out.println(i + ", " + j);
							addLifeForm(entity, i, j  - entity.getCurrentSpeed());
					}
				}
			}
		}

		UI.redraw();
	}
	
	/**
	 * Redraw the UI when called.
	 * @author Josh Bartle
	 */
	public void reDraw(){
		UI.redraw();
	}
	
	/**
	 * Clears the environment
	 * @author Josh Bartle
	 */
	public void clearBoard()
	{
		for(int i = 0; i < getRows(); i++)
		{
			for(int j = 0; j < getColumns(); j++)
			{
				cells[i][j] = new Cell();
			}
		}
		player = null;
	}
	
}
