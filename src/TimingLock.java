

import java.util.Calendar;

public class TimingLock {
	/**
	 * The time to wait before releasing the lock
	 */
	private long waitTime;
	
	/**
	 * The time the lock was last locked
	 */
	private long lastLockTime;

	/**
	 * Create a new timing lock with the given wait time.
	 * @param waitTimeInMilliSeconds The time to wait before the lock is
	 * unlocked.
	 */
	public TimingLock(long waitTimeInMilliSeconds) {
		waitTime = waitTimeInMilliSeconds;
	}

	/**
	 * Lock the lock.
	 */
	public void lock() {
		lastLockTime = getCurrentTime();
	}

	/**
	 * Return true if the lock is locked, else false.
	 * @return True if the lock is locked.
	 */
	public boolean isLocked() {
		return getCurrentTime() < waitTime + lastLockTime;
	}

	/**
	 * Return the current time in milliseconds.
	 * @return The current time in milliseconds.
	 */
	private long getCurrentTime() {
		return Calendar.getInstance().getTimeInMillis();
	}
}
