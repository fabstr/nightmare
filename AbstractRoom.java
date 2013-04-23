/**
 * The abstract room stores the image of the room (with floor/walls),
 * as well as information about the width and height (in pixels) of
 * the room.
 */
private static class AbstractRoom {
	/**
	 * The width and height (in pixels).
	 */
	private int w;
	private int h;

	private Image

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
	public AbstractRoom(int w, int h, int floorX, int floorY) {
		this.w = w;
		this.h = h;
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
		if (x >= 0 && x < w && y >= 0 && y < h) {
			return true;
		} 

		return false;
	}
}
