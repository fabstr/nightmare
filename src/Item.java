/**
 * Class Item represents an item in the game and stores
 * a string description of the item.
 * @author Anna Lindelöf
 * @version 1
 */

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.GroupObject;

public class Item {

	/**
	 * The description of the item
	 */
	private String description;
	
	/**
	 * The image of the item.
	 */
	private Image img;
	
	/**
	 * The groupObject of the item
	 */
	public GroupObject go;
	
	/**
	 * The x position of the item
	 */
	private int x;
	
	/**
	 * The y position of the item
	 */
	private int y;
	
	/**
	 * The types an item can have
	 */
	public static enum ItemTypes {
		HEART
	}

	/**
	 * Create a new item
	 * @param description
	 * @param img
	 * @param x
	 * @param y
	 */
	public Item (String description, Image img, int x, int y) {
		this.description = description;
		this.img = img;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Create an item from a GroupObject
	 * @param go
	 * @throws SlickException
	 */
	public Item (GroupObject go) throws SlickException {
		this.description = go.name;
		this.img = go.getImage();
		this.x = go.x;
		this.y = go.y;
		this.go = go;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Get the image of the item
	 * @return
	 */
	public Image getImage() {
		return img;
	}

	/**
	 * Returns a description of the item.
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Draw the item at its x/y position
	 */
	public void draw() {
		img.draw(x, y);
	}

}
