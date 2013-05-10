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
	 * The AbstractRoom of this room.
	 */
	private AbstractRoom abstractRoom;
	
	public static enum CharacterTypes {
		ghost, gargoyle, chainsaw, scream, dracula
	}

	/**
	 * The map of the room
	 */
	private TiledMapPlus map;
	
	/**
	 * The random generator to place the characters
	 */
	private static Random r;
	
	/**
	 * true if there are walls in the room (ie there are a "walls" layer in the
	 * tmx map
	 */
	private boolean thereAreWalls;
	
	/**
	 * The layer of walls from the tmx map
	 */
	private Layer wallsLayer;

	/**
	 * the id of the room, used for carpet room-switching
	 */
	private String id;
	
	/**
	 * the objects as placed by the map
	 */
	private ObjectGroup roomObjects;
	
	/**
	 * the images for the objects on the map
	 */
	private HashMap<String, Image> objectImageMap;
	
	/**
	 * True if there are animated objects in this room
	 */
	private boolean thereAreAnimatedObjects;
	
	/**
	 * The animated objects of this room
	 */
	private AnimatedObject[] animatedObjects;

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
	public Room(String roomFile, String id) throws SlickException {
		this(Resources.ROOM_WIDTH, Resources.ROOM_HEIGHT, roomFile, 
				Resources.ROOM_X, Resources.ROOM_Y, Resources.WALL_WIDTH, id);
	}
	
	private Room(int width, int height, String roomFile, int floorX, 
			int floorY, int wallWidth, String id) throws SlickException {
		
		this.id = id;
		abstractRoom = new AbstractRoom(width, height, floorX, floorY, wallWidth);
		map = new TiledMapPlus(roomFile, Resources.ROOM_TILESHEETS_FOLDER);
		
		characters = new ArrayList<Character>();
		r = new Random();
		objectImageMap = new HashMap<String, Image>();
		
		roomObjects = map.getObjectGroup("objects");
		
		// add the needed images 
		for (GroupObject go : roomObjects.objects) {
			// we assume that every object with the same type string should have
			// the same image
			String type = go.type;
			Image toAdd = null;
			
			if (type.equals(Resources.IDSTRING_KEY)) {
				toAdd = Resources.getKeyImage();
			} else if (type.equals(Resources.IDSTRING_CARPET)) {
				toAdd = Resources.getCarpetImage();
			}
			
			objectImageMap.put(type, toAdd);
		}
		
		// check if there are a "walls" or "animated" layer
		for (Layer l : map.getLayers()) {
			if (l.name.toUpperCase().equals(Resources.IDSTRING_WALLS)) {
				// there is a walls layer
				thereAreWalls = true;
				wallsLayer = l;
				break;
			} 
		}
		
		// check if there is a "animated" object layer
		for (ObjectGroup ob : map.getObjectGroups()) {
			if (ob.name.toUpperCase().equals(Resources.IDSTRING_ANIMATED)) {
				thereAreAnimatedObjects = true;
				
				// since we only position the objects in the tmx map, add them
				// to the rooms array
				int len = ob.objects.size();
				animatedObjects = new AnimatedObject[len];
				int i = 0;
				for (GroupObject go : ob.objects) {
					animatedObjects[i] = new AnimatedObject(go);
					i++;
				}
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

	/**
	 * Draw the room.
	 * @throws SlickException
	 */
	public void draw() throws SlickException {
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
		
		if (thereAreAnimatedObjects) {
			for (AnimatedObject ao : animatedObjects) {
				ao.draw();
			}
		}
		
		// draw the ghosts		
		for (Character c : characters) {
			c.draw();
		}
	}
	
	/**
	 * @return the id of the room
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Move the characters in the room
	 * @param deltaTime
	 */
	public void moveCharaters(int deltaTime) {
		for (Character g : characters) {
			g.move(this, deltaTime);
		}
	}
	
	/**
	 * Add a character of the given type.
	 * @param ct
	 * @throws SlickException
	 */
	public void addCharacter(CharacterTypes ct) throws SlickException {
		int x = r.nextInt(abstractRoom.getWidth()) + abstractRoom.getFloorX();
		int y = r.nextInt(abstractRoom.getHeight()) + abstractRoom.getFloorY();

		while (!canSetXY(x, y, 64, 64)) {
			x = r.nextInt(abstractRoom.getWidth()) + abstractRoom.getFloorX();
			y = r.nextInt(abstractRoom.getHeight()) + abstractRoom.getFloorY();
		}
		
		Character c = null;
		switch (ct) {
		case ghost:
			c = new Ghost(x, y);
			break;
		case gargoyle:
			c = new Gargoyle(x, y);
			break;
		case chainsaw: 
			c = new HostileNPC(x, y, Resources.CHAINSAW_PATH);
			break;
		case scream:
			c = new HostileNPC(x, y, Resources.SCREAM_PATH);
			break;
		case dracula:
			c = new HostileNPC(x, y, Resources.DRACULA_PATH);
			break;
		}
		
		characters.add(c);
	}
	
	/**
	 * Add n characters of the given type
	 * @param ct
	 * @param n
	 * @throws SlickException
	 */
	public void addCharacter(CharacterTypes ct, int n) throws SlickException {
		for (int i=0; i<n; i++) {
			addCharacter(ct);
		}
	}

	/**
	 * Return true if the position is valid
	 * @param newX
	 * @param newY
	 * @param direction
	 * @return
	 */
	public boolean canSetXY(float newX, float newY, AnimationManager.directions direction) {
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
	
	/**
	 * Return true if the position is valid
	 * @param newX
	 * @param newY
	 * @return
	 */
	public boolean canSetXY(float newX, float newY) {
		return (newY > abstractRoom.getFloorY() && newY < abstractRoom.getHeight() + abstractRoom.getFloorY() &&
				newX > abstractRoom.getFloorX() && newX < abstractRoom.getWidth() + abstractRoom.getFloorX());
	}

	/**
	 * Return true if the position is valid
	 * @param newX
	 * @param newY
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean canSetXY(float newX, float newY, int width, int height) {
		return canSetXY(newX+width, newY+height);
	}

	/**
	 * Return true if the position is valid
	 * @param newX
	 * @param newY
	 * @param width
	 * @param height
	 * @param direction
	 * @return
	 */
	public boolean canSetXY(float newX, float newY, int width, int height, AnimationManager.directions direction) {
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

	/**
	 * Return true if the player is on a character
	 * @param playerBoundingBox
	 * @return
	 */
	public boolean isPlayerOnACharacter(Rectangle playerBoundingBox) {
		
		for (Character c : characters) {
			Rectangle characterBox = c.getBoundingBox();
			if (playerBoundingBox.intersects(characterBox)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove and return the key in the room.
	 * @return
	 */
	public GroupObject removeKey() {
		GroupObject toReturn = roomObjects.getObject(Resources.IDSTRING_KEY);
		roomObjects.removeObject(Resources.IDSTRING_KEY);
		return toReturn;
	}

	/**
	 * Return true if the player is on a key.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isPlayerOnKey(int x, int y) {
		GroupObject currentObject = getTheObjectThePlayerIsStandingOn(x, y);
		if (currentObject == null || !Resources.IDSTRING_KEY.equals(currentObject.name)) {
			return false;
		}
		
		return true;
	}

	/**
	 * Return true if the player is on a carpet
	 * @param x
	 * @param y
	 * @return
	 */
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

	/**
	 * Remove a groupObject from the room
	 * @param go
	 */
	public void removeGroupObject(GroupObject go) { 
				roomObjects.objects.remove(go);
	}
}
