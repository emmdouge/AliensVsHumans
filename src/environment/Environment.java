package environment;
import graphics.GUI;
import graphics.Ranking;

import java.awt.Point;

import weapon.Weapon;
import lifeform.Direction;
import lifeform.Lifeform;

/**
 * @author Emmanuel Douge
 */
public class Environment 
{
	//this is private instead of public for synchronization reasons
    private static Environment instance = null;
    
	private Block[][] blocks; //the 2D array of cells in the environment
	
    protected int rows;
    protected int columns;

    private Lifeform player;

	private int numHumans;
	private int numAliens;
    
    private static GUI ui = null;
    
    
    public Environment() 
    {
		//nothing initialized
	}

	/**
     * This method is synchronized to the class because it is static.
     * only one thread can call this method from
     * all instances of this class (there is only 1 since this is a singleton)
     * at a time from whichever thread the it is being referenced from
     * @return return the active instance of the Environment.
     */
    public static synchronized Environment getInstance()
    {
    	if(instance == null)
    	{
    		instance = new Environment();
    	}
    	return instance;
    }
    
    /**
     * initializes singleton through lazy initialization
     * @param aliens 
     * @param humans 
     * @return
     */
    public void initBlocks(int r, int c, int humans, int aliens)
    {
    	instance.player = null;

        instance.rows = r;
        instance.columns = c;
        instance.numHumans = humans;
        instance.numAliens = aliens;
        
        
        //allocates memory for 2d array of cells
        instance.blocks = new Block[rows][columns];
        
        //initializes all the cells in the environment
        for (int j = 0; j < instance.columns; j++)
        {
            for (int i = 0; i < instance.rows; i++)
            {
                instance.blocks[j][i] = new Block();
            }
        }
        

    }
    
    public void initUI()
    {
        ui = GUI.getInstance();
        ui.init(instance);	
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
		blocks[row][col].setWeapon(weapon); 
		ui.draw();
	}
	
	/**
	 * removes a weapon from the environment
	 * @param row
	 * @param col
	 */
	public void removeWeapon(int row, int col)
	{
		blocks[row][col].setWeapon(null); 
		ui.draw();
	}
	/**
	 * method for adding the player to be controlled
	 * @param player
	 * @param row
	 * @param column
	 */
	public void setPlayer(Lifeform player, int row, int column)
	{
		addLifeForm(player, row, column);
		this.player = player;
		this.player.isPlayer = true;

	}
	/**
	 * returns a weapon
	 * @param row the row the weapon is in
	 * @param col the column the weapon is in
	 * @return
	 */
	public Weapon getWeapon(int row, int col)
	{
		return blocks[row][col].getWeapon(); 
	}
	
	/**
	 * returns a specific weapon
	 * @author Cassia Hulshizer
	 * @param row
	 * @param col
	 * @param weapon
	 * @return
	 */
	public Weapon getWeapon(int weaponNum, int row, int col)
	{
		if (weaponNum == 1)
		{
			return blocks[row][col].getWeaponOne();
		}
		else if (weaponNum == 2)
		{
			return blocks[row][col].getWeaponTwo();
		}
		else
		{
			return null;
		}
	}
	
    /**
     * If there is space it places the weapon in the weaponTwo slot.
     * @param weapon
     */
    public void setWeaponTwo(int row, int col, Weapon weapon)
    {
            blocks[row][col].setWeaponTwo(weapon);
    }
	
	/**
	 * Finds the distance between two lifeforms
	 * @param life1
	 * @param life2
	 * @return
	 */
    public int computeRange(Lifeform life1, Lifeform life2)
    {
        Point p1 = findLifeform(life1);
        Point p2 = findLifeform(life2);
        
        //find the distance of between two points using a^2 + b^2 = c^2
        //where c is the distance between the points
        int distanceModifier = 100;
        int distance = (int)Math.round(Math.sqrt(Math.pow(p1.x-p2.x, 2)*distanceModifier + Math.pow(p1.y-p2.y, 2)*distanceModifier));
        return distance;
    }
    
    /**
     * Finds a LifeForm in the environment
     * @param life
     * @return
     */
    public Point findLifeform(Lifeform lifeform)
    {
        for (int x = 0; x < rows; x++)
        {
            for (int y = 0; y < columns; y++)
            {
                if (blocks[x][y].getLifeForm() == lifeform)
                {
                    return new Point(x,y);
                }
            }
        }
        return null;
    }
    
	/**
	 * returns a specified cell in the Environment
	 * @param row
	 * @param col
	 */
	public Lifeform getLifeForm(int row, int col)
	{
		return blocks[row][col].getLifeForm(); 
	}

	/**
	 * adds a LifeForm to a Cell in the Environment
	 * coordinates wrap
	 * @param lifeform
	 * @param row
	 * @param col
	 * @return true is LifeForm was added
	 */
	public void addLifeForm(Lifeform lifeform, int row, int col)
	{
		//enables wrapping
		if(col < 0)
		{
			col = columns-1;
		}
		if(col >= columns)
		{
			col = 0;
		}
		if(row < 0)
		{
			row = rows-1;
		}
		if(row >= rows)
		{
			row = 0;
		}
		
		//adds lifeforms to the map
		blocks[row][col].addLifeForm(lifeform);
	}
	/**
	 * method for removing lifeform
	 * @param row
	 * @param col
	 */
	public void removeLifeForm(int row, int col)
	{
		blocks[row][col].removeLifeForm(); 
		ui.draw();
	}
	/**
	 * @return the current player
	 */
	public Lifeform getPlayer()
	{
		return player;
	}
	
	/**
	 * Redraw the UI when called.
	 * @author Josh Bartle
	 */
	public void reDraw()
	{
		ui.draw();
	}

    public Ranking getScoreBoard()
    {
    	return ui.getRanking();
    }
    
    public int getNumHumans()
    {
    	return numHumans;
    }
    
    public int getNumAliens()
    {
    	return numAliens;
    }
    
	public Lifeform getLifeformInFrontOf(Lifeform lifeform, Direction direction) 
	{
		
		if(direction == Direction.NORTH)
		{
			int i = (int) findLifeform(lifeform).getX()-1;
			int j = (int) findLifeform(lifeform).getY();
			
			//topNotReached
			if(i > 0)
			{
				if(getLifeForm(i, j) != null)
				{
					return getLifeForm(i, j);
				}	
			}
			
		}
		
		else if(direction == Direction.SOUTH)
		{
			int i = (int) findLifeform(lifeform).getX()+1;
			int j = (int) findLifeform(lifeform).getY();
			
			
			//bottomNotReached
			if(i < rows)
			{
				if(getLifeForm(i, j) != null)
				{
					return getLifeForm(i, j);
				}	
			}			
			
		}
		
		else if(direction == Direction.EAST)
		{
			int i = (int) findLifeform(lifeform).getX();
			int j = (int) findLifeform(lifeform).getY()+1;
			
			//rightmostEdgeNotReached
			if(j < columns)
			{
				if(getLifeForm(i, j) != null)
				{
					return getLifeForm(i, j);
				}
			}
			
		}	
		
		else if(direction == Direction.WEST)
		{
			int i = (int) findLifeform(lifeform).getX();
			int j = (int) findLifeform(lifeform).getY()-1;
			
			//leftmostEdgeNotReached
			if(j > 0)
			{
				if(getLifeForm(i, j) != null)
				{
					return getLifeForm(i, j);
				}	
			}
		}
		return null;
	}
	
	public Lifeform getLifeformClosestTo(Lifeform lifeform, Direction direction) {

		if(direction == Direction.NORTH)
		{
			int i = (int) findLifeform(lifeform).getX()-1;
			int j = (int) findLifeform(lifeform).getY();
			
			//topNotReached
			while(i > 0)
			{
				if(getLifeForm(i, j) != null)
				{
					return getLifeForm(i, j);
				}	
				//go up
				i--;
			}
			
		}
		
		else if(direction == Direction.SOUTH)
		{
			int i = (int) findLifeform(lifeform).getX()+1;
			int j = (int) findLifeform(lifeform).getY();
			
			//bottomNotReached
			while(i < rows)
			{
				if(getLifeForm(i, j) != null)
				{
					return getLifeForm(i, j);
				}	
				//go down
				i++;
			}			
			
		}
		
		else if(direction == Direction.EAST)
		{
			int i = (int) findLifeform(lifeform).getX();
			int j = (int) findLifeform(lifeform).getY()+1;
			
			//rightmostEdgeNotReached
			while(j < columns)
			{
				if(getLifeForm(i, j) != null)
				{
					return getLifeForm(i, j);
				}
				//go right
				j++;
			}
			
		}	
		
		else if(direction == Direction.WEST)
		{
			int i = (int) findLifeform(lifeform).getX();
			int j = (int) findLifeform(lifeform).getY()-1;
			
			//leftmostEdgeNotReached
			while(j > 0)
			{
				if(getLifeForm(i, j) != null)
				{
					return getLifeForm(i, j);
				}
				//go left
				j--;
			}
		}
		
		return null;
	}
}
