import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.GroupObject;
/**
 * Class Inventory represents a players inventory that can store 
 * picked up items (of the Items class).
 * Items can be added and removed.
 * Inventory can be asked if it contains a certain item.
 * @author Anna Lindelöf
 * @version 1
 */
public class Inventory {

	/**
	 * The items of this inventory
	 */
	private ArrayList<Item> items;
	
	/**
	 * The maximum capacity of the inventory
	 */
	private final int CAPACITY = 5;  

	/**
	 * The current number of items in the inventory
	 */
	private int numItems = 0;  
	
	/**
	 * The number of keys in the inventory
	 */
	private int numberOfKeys;
	
	/**
	 * Create a new inventory
	 */
	public Inventory() {
		this.items = new ArrayList<Item>(); 
	}

	/**
	 * Adds an item to the inventory if the inventory is not full.
	 * Returns true if the item was added, otherwise false.
	 * @param theItem  (the item to be added)
	 * @return boolean (true if added, false if not added)
	 */
	public boolean addItem(Item theItem) {
		if(numItems < CAPACITY) {
			items.add(theItem);
			this.numItems ++;   // increase number of items in inventory by 1.
			return true;   // the item was added.
		}
		else {
			return false;   //inventory full, item was NOT added.
		}
	}
	
	/**
	 * Add a GroupObject item.
	 * @param go
	 * @return True if the object was added.
	 * @throws SlickException
	 */
	public boolean addItem(GroupObject go) throws SlickException {
		return addItem(new Item(go));
	}

	/**
	 * Returns true if the exact object entered exists in the inventory.
	 * Otherwise, returns false.
	 * @param theItem
	 * @return
	 */
	public boolean contains(Item theItem) {
		for (Item item : items) {
			if(item == theItem) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes the specified item from the inventory.
	 * Returns true if the item was successfully removed, 
	 * otherwise returns false.
	 * @param theItem
	 * @return
	 */
	public boolean remove(Item theItem) {
		for (Item item : items) {
			if(item.equals(theItem)) {
				items.remove(item);
				this.numItems --;  //reduce number of items in inventory.
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Draws all the objects in the inventory in the status bar.
	 */
	public void drawInventory() {
		int pos = Resources.INVENTORY_X;
		for (Item i : items) {
			if (i != null) {
				Image img = i.getImage();
				int width = img.getWidth();
				pos += width + Resources.INVENTORY_SPACING;
				img.draw(pos, Resources.INVENTORY_Y);
			}
		}
	}
	
	/**
	 * Add one key to the inventory
	 * @throws SlickException
	 */
	public void addAKey() throws SlickException {
		addItem(new Item("A key", Resources.getKeyImage(), 0, 0));
		numberOfKeys++;
	}
	
	/**
	 * True if there is at least one key
	 * @return
	 */
	public boolean hasAKey() {
		return numberOfKeys > 0;
	}
	
	/**
	 * Remove a key from the inventory
	 */
	public void removeAKey() {
		numberOfKeys--;
		// find a key object
		for (Item i : items) {
			if (i.getDescription().equals("A key")) {
				remove(i);
				return;
			}
		}
	}
}
