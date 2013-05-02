import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

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

	private Item key;
	
	/**
	 * The AbstractRoom of this room.
	 */
	private AbstractRoom abstractRoom;

	/**
	 * The image of this room.
	 */
//	private Image floorImage;

	private TiledMap map;
	
	private Image ghostImage;
	
	private static Random r;

	/**
	 * Create a room.
	 * 
	 * The ghosts can climb on the walls, the player can not. This means the 
	 * player has to stay within getFloorX()+wallWidth and getFloorX()+width-wallWidth 
	 * (and the same for y).
	 * 
	 * @param width The width of the room
	 * @param height The height of the room
	 * @param roomFile The tmx file of the room
	 * @param getFloorX() Where to draw the room
	 * @param getFloorY() Where to draw the room
	 * @param wallWidth The width of the walls
	 * @throws SlickException
	 */
	public Room(int width, int height, String roomFile, int floorX, 
			int floorY, int wallWidth) throws SlickException {
		abstractRoom = new AbstractRoom(width, height, floorX, floorY, wallWidth);
		map = new TiledMap(roomFile, Resources.ROOM_TILESHEETS_FOLDER);
		ghostImage = Resources.getGhostImage();
		
		characters = new ArrayList<Ghost>();
		
		r = new Random();
		key = new Item("The key", Resources.getKeyImage(), 500, 300);
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
//		floorImage.draw(abstractRoom.getFloorX(), abstractRoom.getFloorY());
		map.render(abstractRoom.getFloorX(), abstractRoom.getFloorY());
		
		if (key != null) {
			key.draw();
		}
		
		for (Character c : characters) {
			c.draw();
		}
	}
	
	public void moveGhosts() {
		for (Ghost g : characters) {
			g.move(this);
		}
	}
	
	public void addGhost() throws SlickException {
		int x = r.nextInt(abstractRoom.width()) + abstractRoom.getFloorX();
		int y = r.nextInt(abstractRoom.height()) + abstractRoom.getFloorY();

		while (!canSetX(x, 64)) {
			x = r.nextInt(abstractRoom.width()) + abstractRoom.getFloorX();
		}
		
		while (!canSetY(y, 64)) {
			y = r.nextInt(abstractRoom.height()) + abstractRoom.getFloorY();
		}
		
		Ghost g = new Ghost(4, x, y, ghostImage);
		characters.add(g);
	}

	public boolean canSetY(int newY) {		
		return newY > abstractRoom.getFloorY() && newY < abstractRoom.height() + abstractRoom.getFloorY();
	}
	
	public boolean canSetX(int newX) {
		return newX > abstractRoom.getFloorX() && newX < abstractRoom.width() + abstractRoom.getFloorX();
	}

	public boolean canSetY(int newY, int height) {
		return canSetY(newY+height);
	}
	
	public boolean canSetX(int newX, int width) {
		return canSetX(newX+width);
	}
	
	public boolean isPlayerOnAGhost(int playerX, int playerY, int playerWidth, int playerHeight) {
		for (Character c : characters) {
			if (playerX > c.x && playerX + playerWidth < c.x + Resources.GHOST_WIDTH &&
				playerY > c.y && playerY + playerHeight < c.y + Resources.GHOST_HEIGHT) {
				return true;
			}
		}
		return false;
	}

	public Item removeKey() {
		Item toReturn = key;
		key = null;
		return toReturn;
	}

	public boolean isPlayerOnKey(int x, int y) {
		if (key == null) {
			// there is no key, the player can't be on it
			return false;
		}
		if (x > key.getX() && x < key.getX() + key.getImage().getWidth() &&
			y > key.getY() && y < key.getY() + key.getImage().getHeight()) {
			return true;
		}
		
		return false;
	}
}
