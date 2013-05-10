/**
 * The abstract room stores the image of the room (with floor/walls),
 * as well as information about the width and height (in pixels) of
 * the room.
 */

public class AbstractRoom {
	/**
	 * The width (in pixels) of the room
	 */
	private int w;
	
	/**
	 * The height (in pixels) of the room
	 */
	private int h;
	
	/**
	 * Were the walkable x area starts
	 */
	private int floorX;
	
	/**
	 * Were the walkable y area starts
	 */
	private int floorY;

	/**
	 * The width (and height) of the walls
	 */
	private int wallWidth;
	
	/**
	 * @return the floorX
	 */
	public int getFloorX() {
		return floorX;
	}
	
	/**
	 * @return the floorY
	 */
	public int getFloorY() {
		return floorY;
	}

	/**
	 * @return the wallWidth
	 */
	public int getWallWidth() {
		return wallWidth;
	}

	/**
	 * Return the width of the room.
	 */
	public int getWidth() {
		return w;
	}

	/**
	 * Return the height of the room.
	 */
	public int getHeight() {
		return h;
	}
	
	/**
	 * Create an abstract room with the given properties.
	 * @param w The width of the room
	 * @param h The height of the room
	 * @param floorX The walkable area x 
	 * @param floorY The walkable area y
	 * @param wallWidth The width/height of the walls
	 */
	public AbstractRoom(int w, int h, int floorX, int floorY, int wallWidth) {
		this.w = w;
		this.h = h;
		this.floorX = floorX;
		this.floorY = floorY;
		this.wallWidth = wallWidth;
	}

	/**
	 * Return true if the (x, y) position is within the walkable
	 * area of the room (not on any walls or outside the walls).
	 *
	 * @TODO Make the limits better.
	 *
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return true if the (x,y) is within the walkable area, else
	 * false.
	 */
	public boolean positionIsInRoom(int x, int y) {
		if (x > wallWidth && x < w-wallWidth && y > wallWidth && y < h-wallWidth) {
			return true;
		} 

		return false;
	}
}
