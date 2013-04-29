import java.util.ArrayList;
import org.newdawn.slick.*;

public class Room {
	/**
	 * The items in the room.
	 */
	private Inventory items;

	/**
	 * The moving characters (the ghosts) in the room.
	 */
	private ArrayList<Character> characters;

	/**
	 * The doors in the room.
	 */
//	private Door[] doors;

	/**
	 * The AbstractRoom of this room.
	 */
	private AbstractRoom abstractRoom;

	/**
	 * The image of this room.
	 */
	private Image floorImage;

	public Room(int width, int height, String imgPath, int floorX, 
			int floorY) throws SlickException {
		abstractRoom = new AbstractRoom(width, height, floorX, floorY);
		floorImage = new Image(imgPath);
	}

	/**
	 * Get (access to) the inventory of the room.
	 * @return The inventory
	 */
	public Inventory getInventory() {
		return items;
	}

	/**
	 * Return true if the (x, y) position is within the walkable
	 * area of the room (not on any walls or outside the walls).
	 *
	 * @TODO Fix the todo in AbstractRoom.
	 *
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return true if the (x,y) is within the walkable area, else
	 * false.
	 */
	public boolean isPositionOutOfRoom(int x, int y) {
		if (abstractRoom.positionIsInRoom(x, y) == false) {
			return true;
		}

		return false;
	}

	public void draw() {
		floorImage.draw(0.0f,0.0f);
		
	}
}
