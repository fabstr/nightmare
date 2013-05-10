import java.awt.Rectangle;
import java.security.SecureRandom;

import org.newdawn.slick.*;

public abstract class Character {
	/**
	 * The random number generator, to randomize the directions
	 */
	protected static SecureRandom r;
	
	/**
	 * The health of the character.
	 */
	protected int health;

	/**
	 * The current x coordinate of the character
	 */
	protected float x;
	
	/**
	 * The current y coordinate of the character
	 */
	protected float y;
	
	/**
	 * The width of the character
	 */
	protected int width;
	
	/**
	 * The height of the character
	 */
	protected int height;

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
	public Character(int health, int x, int y, int width, int height) throws
		SlickException {
		this.health = health;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * @return the character's current bounding box
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle((int) x, (int) y, width, height);
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
	public float getXPos() {
		return x;
	}

	/**
	 * Get the y position of the character.
	 * @return The y position.
	 */
	public float getYPos() {
		return y;
	}

	/**
	 * Set the x position.
	 * @param x The new x position.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Set the y position.
	 * @param y The new y position.
	 */
	public void setY(float y) {
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
	
	/**
	 * Ask the character to move itself. 
	 * Please implement this in a subclass.
	 * @param r The room the character is in
	 * @param deltaTime The time which has passed
	 */
	abstract public void move(Room r, int deltaTime);
	
	/**
	 * Move amount on the x axis
	 * @param amount
	 */
	public void moveX(int amount) {
		this.x += amount;
	}
	
	/**
	 * Move amount on the y axis
	 * @param amount
	 */
	public void moveY(int amount) {
		this.y += amount;
	}
}
