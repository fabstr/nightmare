import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Player extends Character {
	public static enum directions {
		LEFT, RIGHT, UP, DOWN;
	}
	
	private directions direction;
	
	private Animation walkingLeft;
	private Animation walkingRight;
	private Animation walkingUp;
	private Animation walkingDown;
	private Animation standingLeft;
	private Animation standingRight;
	private Animation standingUp;
	private Animation standingDown;
	
	// to avoid the player getting hurt twice or more by a ghost within a time period
	private TimingLock tl;
	
	private Inventory inventory;
	
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
	 * Decrease the health.
	 * @param amount The amount to Decrease the health with.
	 */
	public void decreaseHealth(int amount) {
		if (tl.isLocked()) {
			return;
		}
		
		tl.lock();
		health -= amount;
	}
	
	public void walkDown() {
		currentAnimation = walkingDown;
		direction = directions.DOWN;
	}
	
	public void walkUp() {
		currentAnimation = walkingUp;
		direction = directions.UP;
	}
	
	public void walkLeft() {
		currentAnimation = walkingLeft;
		direction = directions.LEFT;
	}
	
	public void walkRight() {
		currentAnimation = walkingRight;
		direction = directions.RIGHT;
	}
	
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
	
	public void moveX(int amount, Room room, boolean countImageWidth, int deltaTime) {
		int newX = (int) this.x + amount;
		
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
	
	public void moveY(int amount, Room room, boolean countImageWidth, int deltaTime) {
		int newY = (int) this.y + amount;
		
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


	public Inventory getInventory() {
		return inventory;
	}



	public void drawInventory() {
		inventory.drawInventory();
	}

	public void addKey() throws SlickException {
		inventory.addAKey();
	}

	public boolean hasKey() {
		return inventory.hasAKey();
	}
	
	public void removeAKey() {
		inventory.removeAKey();
	}
}
