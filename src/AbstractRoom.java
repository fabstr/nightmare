/**
 * The abstract room stores the image of the room (with floor/walls),
 * as well as information about the width and height (in pixels) of
 * the room.
 */

public class AbstractRoom {
	/**
	 * The width and height (in pixels).
	 */
	private int w;
	private int h;
	
	private int floorX;
	private int floorY;
	
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

	private int wallWidth;

		/**
		 * Return the width of the room.
		 */
		public int width() {
			return w;
		}

	/**
	 * Return the height of the room.
	 */
	public int height() {
		return h;
	}
	

	/**
	 * Create a new AbstractRoom with the given width and height.
	 * @param w The width.
	 * @param h The height.
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
