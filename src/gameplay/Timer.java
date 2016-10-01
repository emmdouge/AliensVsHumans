/**
 * 
 */
package gameplay;

import graphics.GUI;

import java.util.ArrayList;

/**
 * @author Emmanuel Douge
 *
 */
public class Timer extends Thread 
{

	private int currentUpdate; //the current round
	private ArrayList<TimerObserver> observers; //the list of observers
	private int timeBetweenUpdates; 
	/**
	 * sets us a SimpleTimer to start at Round 0 with no observers
	 */
	public Timer()
	{
		//refactored to redirect to more complex constructor
		//round = 0; 
		//observers = new ArrayList<TimerObserver>(); 
		this(0); 
	}
	
	public Timer(int time)
	{
		currentUpdate = 0; 
		timeBetweenUpdates = time;
		observers = new ArrayList<TimerObserver>();
	}

	/**
	 * updates the round at a fixed interval determined by the number
	 * of milliseconds used in instantiation
	 */
	public void run()
	{
		try 
		{
			//update the round, then sleep for an interval
			while(true)
			{
				Thread.sleep(timeBetweenUpdates);
				update();
				GUI.getInstance().draw();
			}		 
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void update() 
	{
		currentUpdate++; 
		System.out.println("update #: " + currentUpdate); 
	
		//update all observers
		for(TimerObserver observer: observers)
		{
			observer.update(currentUpdate);
		}
	}
	
	/**
	 * @return the number of observes this Timer has
	 */
	public int getNumObservers() {
		return observers.size();
	}
	
	/**
	 * 
	 * @return the current round number
	 */
	public int getCurrentUpdate()
	{
		return currentUpdate; 
	}
	

	/* (non-Javadoc)
	 * @see gameplay.Timer#addTimeObserver(gameplay.TimerObserver)
	 */
	public void addTimeObserver(TimerObserver observer) {
		observers.add(observer); 
	}

	/* (non-Javadoc)
	 * @see gameplay.Timer#removeTimeObserver(gameplay.TimerObserver)
	 */
	public void removeTimeObserver(TimerObserver observer) {
		
		//this will work as long as we are passing the actual observer, and not an equivalent
		//since TimerObserver does not include any indication of an ID, this should be OK
		observers.remove(observer); 
	}

}
