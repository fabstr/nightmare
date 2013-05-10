import org.newdawn.slick.tiled.GroupObject;


public class Carpet {
	/**
	 * the target of the door, the carpet that exits the level has the target 
	 * "EXIT", the other carpets have target for the room they point to.
	 * If the room "on the other side" is named ROOM1, the target will be "1".
	 */
	private String target;
	
	/**
	 * true if the door is locked
	 */
	private boolean locked;
	
	/**
	 * The GroupObject of this carpet
	 */
	private GroupObject carpetObject;
	
	/**
	 * The x position of the carpet
	 */
	private int x;
	
	/**
	 * The y position of the carpet
	 */
	private int y;
	
	/**
	 * Create a new carpet object.
	 * @param target The room this carpet points to
	 * @param lockedString Wheter the room is locked or not 
	 * ({@link Resources.IDSTRING_CARPET_LOCKED} and 
	 * {@link Resources.IDSTRING_CARPET_NOT_LOCKED}.
	 * @param x The x position of the carpet
	 * @param y The y position of the carpet
	 * @param carpetObject The groupobject of this carpet
	 */
	public Carpet(String target, String lockedString, int x, int y, GroupObject carpetObject) {
		this.target = target;
		if (lockedString.toUpperCase().equals(Resources.IDSTRING_CARPET_LOCKED)) {
			locked = true;
		} else {
			locked = false;
		}
		
		this.x = x;
		this.y = y;
		
		this.carpetObject = carpetObject;
	}
	
	/**
	 * Unlock the carpet.
	 */
	public void unlock() {
		locked = false;
		carpetObject.props.setProperty(Resources.PROPSSTRING_LOCKED, Resources.IDSTRING_CARPET_NOT_LOCKED);
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
	
	/**
	 * @return true if the carpet is an exit carpet (in which case the game is won)
	 */
	public boolean isExitCarpet() {
		return Resources.IDSTRING_EXIT.equals(target.toUpperCase());
	}
	
	/**
	 * @return The x position of the carpet
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return The y position of the carpet
	 */
	public int getY() {
		return y;
	}
}
