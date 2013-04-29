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
	
	private int x;
	private int y;
	
	private static Random r;

	public Room(String imgPath, String ghostPath, int floorX, 
			int floorY) throws SlickException {
		ghostImage = new Image(ghostPath);
		floorImage = new Image(imgPath);
		
		int width = ghostImage.getWidth();
		int height = ghostImage.getHeight();
		
		abstractRoom = new AbstractRoom(width, height, floorX, floorY);
		
		characters = new ArrayList<Ghost>();
		
		r = new Random();
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
		if (abstractRoom.positionIsInRoom(this.x+x, this.y+y) == false) {
			return true;
		}

		return false;
	}

	public void draw() {
		floorImage.draw(x, y);
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
		int x = r.nextInt(abstractRoom.width()) + abstractRoom.floorX;
		int y = r.nextInt(abstractRoom.height()) + abstractRoom.floorY;

		while (!canSetX(x, 64)) {
			x = r.nextInt(abstractRoom.width()) + abstractRoom.floorX;
		}
		
		while (!canSetY(y, 64)) {
			y = r.nextInt(abstractRoom.height()) + abstractRoom.floorY;
		}
		
		Ghost g = new Ghost(4, x, y, ghostImage);
		characters.add(g);
	}

	public boolean canSetY(int newY) {
		newY += this.y;
		return newY > abstractRoom.floorY && newY < abstractRoom.height() + abstractRoom.floorY;
	}
	
	public boolean canSetX(int newX) {
		newX += this.x;
		return newX > abstractRoom.floorX && newX < abstractRoom.width() + abstractRoom.floorX;
	}

	public boolean canSetY(int newY, int height) {
		return canSetY(newY+height);
	}
	
	public boolean canSetX(int newX, int width) {
		return canSetX(newX+width);
	}
	
	public boolean isPlayerOnAGhost(int playerX, int playerY, int playerWidth, int playerHeight) {
		int ghostHeight = 64;
		int ghostWidth = 64;
		
		for (Character c : characters) {
			if (playerX > c.x && playerX + playerWidth < c.x + ghostWidth &&
				playerY > c.y && playerY + playerHeight < c.y + ghostHeight) {
				return true;
			}
		}
		return false;
	}
}
