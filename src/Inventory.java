import java.util.ArrayList;
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
 * Class Fields.	
 */
private ArrayList<Item> items;
private final int CAPACITY = 5;  // The inventory's capacity.
private int numItems = 0;  // the number of items stored in the inventory.
/**
 * Constructor.
 */
public Inventory() {
	this.items = new ArrayList<Item>();   // creates an empty list.
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
		if(item == theItem) {
			items.remove(item);
			this.numItems --;  //reduce number of items in inventory.
			return true;
		}
    }
	return false;

}
}
