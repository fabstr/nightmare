import org.newdawn.slick.SlickException;


public abstract class NPC extends Character {
	private AnimationManager am;
	protected AnimationManager.directions direction;

	/**
	 * The direction in which we previous tried to move
	 */
	private AnimationManager.directions prevDirection;
	
	/**
	 * The result of the previous (attempt) to move
	 */
	private int moveResult;
	
	public NPC(int health, int x, int y, int width, int height, String spriteSheetPath)
			throws SlickException {
		super(health, x, y, width, height);
		am = new AnimationManager(spriteSheetPath);
		currentAnimation = am.getAnimation(AnimationManager.directions.DOWN);
		direction = AnimationManager.directions.LEFT;
	}
	
	public NPC(int x, int y, Room.CharacterTypes ct) throws SlickException {
		super(0, x, y, Resources.PLAYER_WIDTH, Resources.PLAYER_HEIGHT);
	}
	
	public NPC(int x, int y, String spriteSheetPath) throws SlickException {
		super(0, x, y, Resources.PLAYER_WIDTH, Resources.PLAYER_HEIGHT);
		am = new AnimationManager(spriteSheetPath);
		currentAnimation = am.getAnimation(AnimationManager.directions.DOWN);
		direction = AnimationManager.directions.LEFT;
	}

	@Override
	public void move(Room r, int deltaTime) {
	}
	
	/**
	 * Set the current animation to the right, from the current direction.
	 */
	protected void updateAnimationDirection() {
		currentAnimation = am.getAnimation(direction);
	}
	
	/**
	 * Set the animation and direction to down
	 */
	public void walkDown() {
		direction = AnimationManager.directions.DOWN;
		updateAnimationDirection();
	}
	
	/**
	 * Set the animation and direction to up
	 */
	public void walkUp() {
		direction = AnimationManager.directions.UP;
		updateAnimationDirection();
	}
	
	/**
	 * Set the animation and direction to left
	 */
	public void walkLeft() {
		direction = AnimationManager.directions.LEFT;
		updateAnimationDirection();
	}
	
	/**
	 * Set the animation and direction to right
	 */
	public void walkRight() {
		direction = AnimationManager.directions.RIGHT;
		updateAnimationDirection();
	}
	
	/**
	 * Change the animation to a stop-walking one.
	 */
	public void stopWalking() {
		switch (direction) {
		case LEFT:
			direction = AnimationManager.directions.STANDING_LEFT;
			break;
		case RIGHT:
			direction = AnimationManager.directions.STANDING_RIGHT;
			break;
		case UP:
			direction = AnimationManager.directions.STANDING_UP;
			break;
		case DOWN:
			direction = AnimationManager.directions.STANDING_DOWN;
			break;
		default: 
			break;
		}
		updateAnimationDirection();
	}
	
	public AnimationManager.directions getDirection() {
		return direction;
	}
	
	/**
	 * Move the player in the x direction.
	 * @param amount
	 * @param room
	 * @param countImageWidth
	 * @param deltaTime
	 * @return -1 if it was impossible to move, else 0.
	 */
	public int moveX(int amount, Room room, boolean countImageWidth, int deltaTime) {
		if (direction == prevDirection && moveResult == -1) {
			// the previous attempt to move this way failed, it's not possible now either
			return -1;
		}
		
		float newX = this.x + amount*deltaTime*Resources.PLAYER_MOVEMENT_SPEED;
		if (countImageWidth) {
			newX += currentAnimation.getWidth();
		}
		
		if (!room.canSetXY(newX, y, (getDirection() == AnimationManager.directions.LEFT) ? -32 : 32, height, direction)) {
			moveResult = -1;
			prevDirection = direction;
			return -1;
		}
		
		if (countImageWidth) {
			newX -= currentAnimation.getWidth();
		}
		moveResult = 0;
		prevDirection = direction;
		setX(newX);
		return 0;
	}
	
	/**
	 * Move the player in the y direction
	 * @param amount
	 * @param room
	 * @param countImageWidth
	 * @param deltaTime
	 * @return -1 if it was impossible to move, else 0.
	 */
	public int moveY(int amount, Room room, boolean countImageWidth, int deltaTime) {
		float newY = this.y + amount*deltaTime*Resources.PLAYER_MOVEMENT_SPEED;
		
		if (countImageWidth) {
			newY += currentAnimation.getHeight();
		}
		
		if (!room.canSetXY(x, newY, width, (getDirection() == AnimationManager.directions.UP) ? -32 : 32, direction)) {
			moveResult = -1;
			prevDirection = direction;
			return -1;
		}
		
		if (countImageWidth) {
			newY -= currentAnimation.getHeight();
		}
		
		setY(newY);
		return 0;
	}
	
	public void setDirection(AnimationManager.directions newDir) {
		direction = newDir;
	}
}
