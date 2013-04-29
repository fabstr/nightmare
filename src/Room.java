import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.*;

public class Room {
	/**
	 * The items in the room.
	 */
	private Inventory items;

	/**
	 * The moving characters (the ghosts) in the room.
	 */
	private ArrayList<Ghost> characters;

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

	private Image ghostImage;

	public Room(int width, int height, String imgPath, int floorX, 
			int floorY) throws SlickException {
		abstractRoom = new AbstractRoom(width, height, floorX, floorY);
		floorImage = new Image(imgPath);
		
		ghostImage = new Image("/Users/fabianstrom/uv/nightmare/resources/graphics/sprites/ghost.png");
		
		characters = new ArrayList<Ghost>();
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
		for (Character c : characters) {
			c.draw();
		}
	}
	
	public void moveGhosts() {
		
	}
	
	public void addGhost() throws SlickException {
		Random r = new Random();
		int x = Math.abs(r.nextInt(abstractRoom.width()) + abstractRoom.floorX - 64);
		int y = Math.abs(r.nextInt(abstractRoom.height()) + abstractRoom.floorY - 64);
		Ghost g = new Ghost(4, x, y, ghostImage);
		characters.add(g);
	}
}
