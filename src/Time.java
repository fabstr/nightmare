/**
 * Class Time represents a timer and can be used to tell if
 * a specified time has run out.
 * @author Anna
 *
 */
public class Time {

	/**
	 * Class Fields.
	 */
	private long startTime = -1;
	private int timeLimit;
	private long currTime;
	
	// true if the timer is stopped
	boolean isStopped;

	/**
	 * Constructor.
	 * Sets the timelimit for this Time instance.
	 * @param limit in milliseconds
	 */
	public Time(int limit) {
		this.timeLimit = limit;
	}

	/**
	 * Starts the timer.
	 */
	public void start() {
		this.startTime = System.currentTimeMillis();
		isStopped = false;
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
		if(System.currentTimeMillis() - startTime < timeLimit) {
			return true;
		}
		else {
			return false;
		}
	}

	public long getSecondsLeft() {
		if (isStopped == false) {
			currTime = System.currentTimeMillis() - startTime;
		}
		
		return (timeLimit - currTime) / 1000;
	}
	
	public void stop() {
		isStopped = true;
	}
}
