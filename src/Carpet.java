
public class Carpet {
	// the target of the door, the carpet that exits the level has the target 
	// "EXIT", the other carpets have target for the room they point to.
	// If the room "on the other side" is named ROOM1, the target will be "1".
	private String target;
	
	// true if the door is locked
	private boolean locked;
	
	private int x;
	private int y;
	
	public Carpet(String target, String lockedString, int x, int y) {
		this.target = target;
		if (lockedString.toUpperCase().equals("YES")) {
			locked = true;
		} else {
			locked = false;
		}
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Unlock the carpet.
	 */
	public void unlock() {
		System.out.println("unlocking");
		locked = false;
	}
	
	/**
	 * Get the target.
	 */
	public String getTarget() {
		return target;
	}
	
	/**
	 * Return true if the carpet is locked.
	 */
	public boolean isLocked() {
		return locked == true;
	}
	
	public boolean isExitCarpet() {
		return "EXIT".equals(target.toUpperCase());
	}
	
	public int x() {
		System.out.println("x is " + x);
		return x;
	}
	
	public int y() {
		System.out.println("y is " + y);
		return y;
	}
}
