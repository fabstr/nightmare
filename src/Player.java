import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Player extends Character {
	/**
	 * The directions in which the player can move.
	 */
	public static enum directions {
		LEFT, RIGHT, UP, DOWN;
	}
	
	/**
	 * The current moving direction
	 */
	private directions direction;
	
	/**
	 * The animation for walking left
	 */
	private Animation walkingLeft;
	
	/**
	 * The animation for walking right
	 */
	private Animation walkingRight;

	/**
	 * The animation for walking up
	 */
	private Animation walkingUp;

	/**
	 * The animation for walking down
	 */
	private Animation walkingDown;
	
	/**
	 * The animation for standing and looking left
	 */
	private Animation standingLeft;

	/**
	 * The animation for standing and looking right
	 */
	private Animation standingRight;

	/**
	 * The animation for standing and looking up
	 */
	private Animation standingUp;

	/**
	 * The animation for standing and looking down
	 */
	private Animation standingDown;
	
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
		super(health, x, y, Resources.PLAYER_WIDTH, Resources.PLAYER_HEIGHT);
		
		Image img = new Image(Resources.GREEN_GUY_PATH);
		
		walkingLeft = Resources.getPlayerWalkingLeft(img);
		walkingRight = Resources.getPlayerWalkingRight(img);
		walkingUp = Resources.getPlayerWalkingUp(img);
		walkingDown = Resources.getPlayerWalkingDown(img);
		standingLeft = Resources.getPlayerStandingLeft(img);
		standingRight = Resources.getPlayerStandingRight(img);
		standingUp = Resources.getPlayerStandingUp(img);
		standingDown = Resources.getPlayerStandingDown(img);
		
		tl = new TimingLock(1000);
		
		currentAnimation = walkingDown;
		direction = directions.LEFT;
		
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
	 * Set the animation and direction to down
	 */
	public void walkDown() {
		currentAnimation = walkingDown;
		direction = directions.DOWN;
	}
	
	/**
	 * Set the animation and direction to up
	 */
	public void walkUp() {
		currentAnimation = walkingUp;
		direction = directions.UP;
	}
	
	/**
	 * Set the animation and direction to left
	 */
	public void walkLeft() {
		currentAnimation = walkingLeft;
		direction = directions.LEFT;
	}
	
	/**
	 * Set the animation and direction to right
	 */
	public void walkRight() {
		currentAnimation = walkingRight;
		direction = directions.RIGHT;
	}
	
	/**
	 * Change the animation to a stop-walking one.
	 */
	public void stopWalking() {
		switch (direction) {
		case LEFT:
			currentAnimation = standingLeft;
			break;
		case RIGHT:
			currentAnimation = standingRight;
			break;
		case UP:
			currentAnimation = standingUp;
			break;
		case DOWN:
			currentAnimation = standingDown;
			break;
		}
	}
	
	/**
	 * Move the player in the x direction
	 * @param amount
	 * @param room
	 * @param countImageWidth
	 * @param deltaTime
	 */
	public void moveX(int amount, Room room, boolean countImageWidth, int deltaTime) {
		float newX = this.x + amount*deltaTime*Resources.PLAYER_MOVEMENT_SPEED;
		
		if (countImageWidth) {
			newX += currentAnimation.getWidth();
		}
		
		if (!room.canSetXY(newX, y, (direction == directions.LEFT) ? -32 : 32, height, direction)) {
			return;
		}
		
		if (countImageWidth) {
			newX -= currentAnimation.getWidth();
		}
		
		setX(newX);
	}
	
	/**
	 * Move the player in the y direction
	 * @param amount
	 * @param room
	 * @param countImageWidth
	 * @param deltaTime
	 */
	public void moveY(int amount, Room room, boolean countImageWidth, int deltaTime) {
		float newY = this.y + amount*deltaTime*Resources.PLAYER_MOVEMENT_SPEED;
		
		if (countImageWidth) {
			newY += currentAnimation.getHeight();
		}
		
		if (!room.canSetXY(x, newY, width, (direction == directions.UP) ? -32 : 32, direction)) {
			return;
		}
		
		if (countImageWidth) {
			newY -= currentAnimation.getHeight();
		}
		
		setY(newY);
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
