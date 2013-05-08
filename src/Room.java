import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.ObjectGroup;
import org.newdawn.slick.tiled.TiledMapPlus;

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

	//private Item key;
	
	/**
	 * The AbstractRoom of this room.
	 */
	private AbstractRoom abstractRoom;

	/**
	 * The image of this room.
	 */
//	private Image floorImage;

	private TiledMapPlus map;
	
	private Image ghostImage;
	
	private static Random r;
	
	// the objects as placed by the map
	private ObjectGroup roomObjects;
	
	// the images for the objects on the map
	private HashMap<String, Image> objectImageMap;

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
		map = new TiledMapPlus(roomFile, Resources.ROOM_TILESHEETS_FOLDER);
		ghostImage = Resources.getGhostImage();
		
		characters = new ArrayList<Ghost>();
		r = new Random();
		objectImageMap = new HashMap<String, Image>();
		//key = new Item("The key", Resources.getKeyImage(), 500, 300);
		
		roomObjects = map.getObjectGroup("objects");
		
		// add the needed images 
		for (GroupObject go : roomObjects.objects) {
			// we assume that every object with the same type string should have
			// the same image
			String type = go.type;
			Image toAdd = null;
			
			if (type.equals("key")) {
				toAdd = Resources.getKeyImage();
			} else if (type.equals("carpet")) {
				toAdd = Resources.getCarpetImage();
			}
			
			objectImageMap.put(type, toAdd);
		}
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

	public void draw() throws SlickException {
//		floorImage.draw(abstractRoom.getFloorX(), abstractRoom.getFloorY());
		map.render(abstractRoom.getFloorX(), abstractRoom.getFloorY());
		
		
//		if (key != null) {
//			key.draw();
//		}
		
		// draw the ghosts		
		for (Character c : characters) {
			c.draw();
		}
		
		// draw the objects
//		for (GroupObject go : roomObjects.objects) {
//			Image toRender = objectImageMap.get(go.type);
//			if (toRender != null) {
//				toRender.draw(go.x+abstractRoom.getFloorX(), 
//						go.y+abstractRoom.getFloorY());
//			}
//		}
		for (GroupObject go : roomObjects.objects) {
			// the x/y coordinates where to draw the image on screen
			Image toRender = go.getImage();
			if (toRender != null) {
				int x = go.x + abstractRoom.getFloorX();
				int y = go.y + abstractRoom.getFloorY();
				toRender.draw(x, y);
			}
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

	public boolean canSetY(float newY) {		
		return newY > abstractRoom.getFloorY() && newY < abstractRoom.height() + abstractRoom.getFloorY();
	}
	
	public boolean canSetX(float newX) {
		return newX > abstractRoom.getFloorX() && newX < abstractRoom.width() + abstractRoom.getFloorX();
	}

	public boolean canSetY(float newY, int height) {
		return canSetY(newY+height);
	}
	
	public boolean canSetX(float newX, int width) {
		return canSetX(newX+width);
	}
	
	/**
	 * Get the object the player is standing on.
	 * 
	 * {@link Resources.PLAYER_WIDTH} and {@link Resources.PLAYER_HEIGHT} are
	 * used to determine whether the player is on a object.
	 * 
	 * @param px The player's x position
	 * @param py The player's y position
	 * @return The object, or null if it does not exist.
	 */
	public GroupObject getTheObjectThePlayerIsStandingOn(int px, int py) {
		// since the objects' positions are relative the map and not the screen,
		// remove floorX and floorY from the player's position
		px -= abstractRoom.getFloorX();
		py -= abstractRoom.getFloorY();
		
		// construct the player's bounding box
		Rectangle playerBox = new Rectangle(px, py, Resources.PLAYER_WIDTH, Resources.PLAYER_HEIGHT);
		
		for (GroupObject go : roomObjects.objects) {
			Rectangle objShape = new Rectangle(go.x, go.y, go.width, go.height);
			// if the boxes intersects, the player stands on the object
			if (playerBox.intersects(objShape)) {
				return go;
			}
		}
		
		// the player was not on any object
		return null;
	}

	public boolean isPlayerOnAGhost(Rectangle playerBoundingBox) {
		
		for (Character c : characters) {
			Rectangle ghostBox = c.getBoundingBox();
			if (playerBoundingBox.intersects(ghostBox)) {
				return true;
			}
		}
		return false;
	}

	public GroupObject removeKey() {
		GroupObject toReturn = roomObjects.getObject(Resources.KEY_STRING_ID);
		roomObjects.removeObject(Resources.KEY_STRING_ID);
		return toReturn;
	}

	public boolean isPlayerOnKey(int x, int y) {
		GroupObject currentObject = getTheObjectThePlayerIsStandingOn(x, y);
		if (currentObject == null || !Resources.KEY_STRING_ID.equals(currentObject.name)) {
			return false;
		}
		
		return true;
	}

	public boolean isPlayerOnCarpet(int x, int y) {
		GroupObject currentObject = getTheObjectThePlayerIsStandingOn(x, y);
		if (currentObject == null || !Resources.CARPET_STRING_ID.equals(currentObject.name)) {
			return false;
		}
		
		return true;
	}
}
