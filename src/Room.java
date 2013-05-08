import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.Layer;
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
	private ArrayList<Character> characters;

	/**
	 * The doors in the room.
	 */
//	private Door[] doors;

	//private Item key;
	
	/**
	 * The AbstractRoom of this room.
	 */
	private AbstractRoom abstractRoom;
	
	public static enum CharacterTypes {
		ghost, gargoyle
	}

	/**
	 * The image of this room.
	 */
//	private Image floorImage;

	private TiledMapPlus map;
	
	private Image ghostImage;
	
	private static Random r;
	
	// true if there are walls in the room (ie there are a "walls" layer in the
	// tmx map
	private boolean thereAreWalls;
	private Layer wallsLayer;

	// the id of the room, used for carpet room-switching
	private String id;
	
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
			int floorY, int wallWidth, String id) throws SlickException {
		this.id = id;
		abstractRoom = new AbstractRoom(width, height, floorX, floorY, wallWidth);
		map = new TiledMapPlus(roomFile, Resources.ROOM_TILESHEETS_FOLDER);
		ghostImage = Resources.getGhostImage();
		
		characters = new ArrayList<Character>();
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
		
		// check if there are a "walls" layer
		for (Layer l : map.getLayers()) {
			if (l.name.toUpperCase().equals("WALLS")) {
				// there is a walls layer
				thereAreWalls = true;
				wallsLayer = l;
				break;
			}
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
		
		// draw the objects
		for (GroupObject go : roomObjects.objects) {
			// the x/y coordinates where to draw the image on screen
			Image toRender = go.getImage();
			if (toRender != null) {
				int x = go.x + abstractRoom.getFloorX();
				int y = go.y + abstractRoom.getFloorY();
				toRender.draw(x, y);
			}
		}
		
		// draw the ghosts		
		for (Character c : characters) {
			c.draw();
		}
	}
	
	public String getId() {
		return id;
	}
	
	public void moveCharaters() {
		for (Character g : characters) {
			g.move(this);
		}
	}
	
	public void addCharacter(CharacterTypes ct) throws SlickException {
		int x = r.nextInt(abstractRoom.width()) + abstractRoom.getFloorX();
		int y = r.nextInt(abstractRoom.height()) + abstractRoom.getFloorY();

		while (!canSetXY(x, y, 64, 64)) {
			x = r.nextInt(abstractRoom.width()) + abstractRoom.getFloorX();
			y = r.nextInt(abstractRoom.height()) + abstractRoom.getFloorY();
		}
		
		Character c = null;
		switch (ct) {
		case ghost:
			c = new Ghost(4, x, y, ghostImage);
			break;
		case gargoyle:
			c = new Gargoyle(x, y);
			break;
		}
		
		characters.add(c);
	}

	public boolean canSetXY(float newX, float newY, Player.directions direction) {
		if (thereAreWalls == true) {
			int x = (int) (newX-abstractRoom.getFloorX())/Resources.WALL_WIDTH;
			int y = (int) (newY-abstractRoom.getFloorY())/Resources.WALL_WIDTH;
			
			switch (direction) {
			case LEFT:
				x++;
				break;
			case RIGHT:
				x--;
				break;
			case UP:
				y++;
				break;
			case DOWN:
				y--;
				break;
			}
			
			if (wallsLayer.getTileID(x, y) != 0) {
				// there is something at the position
				return false;
			}
		}
		
		return canSetXY(newX, newY);
	}
	
	public boolean canSetXY(float newX, float newY) {
		return (newY > abstractRoom.getFloorY() && newY < abstractRoom.height() + abstractRoom.getFloorY() &&
				newX > abstractRoom.getFloorX() && newX < abstractRoom.width() + abstractRoom.getFloorX());
	}

	public boolean canSetXY(float newX, float newY, int width, int height) {
		return canSetXY(newX+width, newY+height);
	}

	public boolean canSetXY(float newX, float newY, int width, int height, Player.directions direction) {
		return canSetXY(newX+width, newY+height, direction);
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

	public boolean isPlayerOnACarpet(int x, int y) {
		return getTheCarpetThePlayerIsStandingOn(x, y) != null;
	}
	
	/**
	 * Return the carpet the player stands on, or null if the player is not
	 * standing on a carpet.
	 * @return
	 */
	public Carpet getTheCarpetThePlayerIsStandingOn(int x, int y) {
		GroupObject currentObject = getTheObjectThePlayerIsStandingOn(x, y);
		if (currentObject == null || !Resources.CARPET_STRING_ID.equals(currentObject.name)) {
			return null;
		}
		return new Carpet(currentObject.props.getProperty("target"),
				currentObject.props.getProperty("locked", "false"),
				currentObject.x, currentObject.y, currentObject);
	}
	
	/**
	 * Return the carpet whose target is the given target. If there is no such
	 * carpet, return null.
	 * @param target
	 * @return
	 */
	public Carpet getTheCarpetWithTheTarget(String target) {
		for (GroupObject go : roomObjects.objects) {
			if (go.name.toUpperCase().equals("CARPET")) {
				if (go.props.getProperty("target").equals(target)) {
					return new Carpet(target, go.props.getProperty("locked", "NO"), go.x, go.y, go);
				}
			}
		}
		
		return null;
	}

	public void removeGroupObject(GroupObject go) { 
				roomObjects.objects.remove(go);
	}
}
