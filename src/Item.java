/**
 * Class Item represents an item in the game and stores
 * a string description of the item.
 * @author Anna Lindelöf
 * @version 1
 */
public class Item {

/**
 * Class Fields
 */
private String description;


/**
 * Constructor
 * @param description
 */
public Item (String description) {
	this.description = description;
}

/**
 * Returns a description of the item.
 * @return
 */
public String getDescription() {
	return description;
}

}
