import org.newdawn.slick.*;

public abstract class Character implements Drawable {
	/**
	 * The health of the character.
	 */
	private int health;

	/**
	 * The current x and y coordinates of the character.
	 */
	protected int x;
	protected int y;

	/**
	 * The image of the character.
	 */
	protected Animation currentAnimation;


	/**
	 * Create a new character with the given health, the coordinates and
	 * the given image.
	 * @param health The (initial) health of the character.
	 * @param x The x coordinate to start at
	 * @param y The y coordinate to start at
	 * @param img The image to draw (as a spritesheet of the animations)
	 * @throws SlickException If there was an error creating the Image 
	 * 			  object.
	 */
	public Character(int health, int x, int y, Image img) throws
		SlickException {
		this.health = health;
		this.x = x;
		this.y = y;
		
		
	}

	/**
	 * Draw the character at the (x,y) position.
	 * @param x The x coordinate.
	 * @param y The y coordinage.
	 */
	public void draw(float x, float y) {
		currentAnimation.draw(x, y);
	}

	/**
	 * Draw the character at its current position.
	 */
	public void draw() {
		currentAnimation.draw(x, y);
	}

	/**
	 * Get the health of the character.
	 * @return The health.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Increase the health.
	 * @param amount The amount to increase the health with.
	 */
	public void increaseHealth(int amount) {
		health += amount;
	}

	/**
	 * Decrease the health.
	 * @param amount The amount to Decrease the health with.
	 */
	public void decreaseHealth(int amount) {
		health -= amount;
	}

	/**
	 * Set the health.
	 * @param value The value to set the health to.
	 */
	public void setHealth(int value) {
		health = value;
	}

	/**
	 * Get the x position of the character.
	 * @return The x position.
	 */
	public int getXPos() {
		return x;
	}

	/**
	 * Get the y position of the character.
	 * @return The y position.
	 */
	public int getYPos() {
		return y;
	}

	/**
	 * Set the x position.
	 * @param x The new x position.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Set the y position.
	 * @param y The new y position.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Move the character, ie set both the x and the y position.
	 * @param x The new x position
	 * @param y The new y position
	 */
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	
	public void moveX(int amount) {
		this.x += amount;
	}
	
	public void moveY(int amount) {
		this.y += amount;
	}
}
