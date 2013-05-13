import org.newdawn.slick.SlickException;

public class Player extends NPC {
	/**
	 * to avoid the player getting hurt twice or more by a ghost within a time 
	 * period a TimingLock is used.
	 */
	private TimingLock tl;
	
	/**
	 * The player's inventory
	 */
	private Inventory inventory;
	
	/**
	 * Create a player
	 * @param health
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Player(int health, int x, int y) throws SlickException {
		super(health, x, y, Resources.PLAYER_WIDTH, Resources.PLAYER_HEIGHT, Resources.GREEN_GUY_PATH);
		
		tl = new TimingLock(2000);
		
		inventory = new Inventory();
	}

	/**
	 * Decrease the health. The health will not decrease is the timing lock is
	 * locked.
	 * @param amount The amount to Decrease the health with.
	 */
	public void decreaseHealth(int amount) {
		if (tl.isLocked()) {
			return;
		}
		
		tl.lock();
		health -= amount;
	}
	
	/**
	 * @return The players inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * Draw the players inventory
	 */
	public void drawInventory() {
		inventory.drawInventory();
	}

	/**
	 * Add one key to the inventory
	 * @throws SlickException
	 */
	public void addKey() throws SlickException {
		inventory.addAKey();
	}

	/**
	 * Check if the player has a key.
	 * @return True if the player has at least one key.
	 */
	public boolean hasKey() {
		return inventory.hasAKey();
	}
	
	/**
	 * Remove a key from the player.
	 */
	public void removeAKey() {
		inventory.removeAKey();
	}
	
	/**
	 * Ask the player to move itself.
	 */
	public void move(Room r, int deltaTime) {
	}
}
