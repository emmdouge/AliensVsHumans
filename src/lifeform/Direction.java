package lifeform;


public enum Direction 
{
	NORTH,
	SOUTH,
	EAST,
	WEST;
	
	public static Direction randomDirection() 
	{
		int direction = (int) (Math.random()*4);
		switch(direction)
		{
			case 0:
				return Direction.NORTH;
			case 1:
				return Direction.SOUTH;
			case 2:
				return Direction.EAST;
			case 3:
				return Direction.WEST;
		}
		return null;
	}
}
