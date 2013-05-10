/**
 * Class Time represents a timer and can be used to tell if
 * a specified time has run out.
 * @author Anna
 *
 */
public class Time {

	/**
	 * The time the timer started
	 */
	private long startTime = -1;
	
	/**
	 * The time limit
	 */
	private int timeLimit;
	
	/**
	 * The current time
	 */
	private long currTime;
	
	/**
	 * The time the timer was paused
	 */
	private long paused;
	
	/**
	 *  true if the timer is stopped
	 */
	boolean isStopped;

	/**
	 * Sets the time limit for this Timer.
	 * @param limit in milliseconds
	 */
	public Time(int limit) {
		this.timeLimit = limit;
	}

	/**
	 * Start the timer.
	 */
	public void start() {
		if (isStopped){
			//if the game was paused, subtracts the paused time
			this.startTime = startTime + System.currentTimeMillis() - paused;
			isStopped = false;
		}
		else {
		this.startTime = System.currentTimeMillis();
		}
	}

	/**
	 * Returns true if there is time left.
	 * Returns false if the time has run out.
	 * Prints to the System.out if start() is not called before.
	 * @return
	 */
	public boolean timeLeft() {
		if(startTime == -1) {   //  Alert when writing code.
			System.err.println("Error, start() must be called before timeLeft().");
		}
		if (System.currentTimeMillis() - startTime < timeLimit) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * @return the time left, in seconds
	 */
	public long getSecondsLeft() {
		if (isStopped == false) {
			currTime = System.currentTimeMillis() - startTime;
		}
		
		return (timeLimit - currTime) / 1000;
	}
	
	/**
	 * Stop the timer
	 */
	public void stop() {
		isStopped = true;
	    paused = System.currentTimeMillis();
	}
}
