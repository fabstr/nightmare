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

/**
 * Constructor.
 * Sets the timelimit for this Time instance.
 * @param limit
 */
public Time(int limit) {
	this.timeLimit = limit;
}

/**
 * Starts the timer.
 */
public void start() {
	this.startTime = System.currentTimeMillis();
}

/**
 * Returns true if there is time left.
 * Returns false if the time has run out.
 * Prints to the System.out if start() is not called before.
 * @return
 */
public boolean timeLeft() {
	if(startTime == -1) {   //  Alert when writing code.
		System.out.println("Error, start() must be called before timeLeft().");
	}
	if(System.currentTimeMillis() - startTime < timeLimit) {
		return true;
	}
	else {
		return false;
	}
}

}
