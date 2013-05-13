import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Popup {
	/**
	 * The object that does the drawing
	 */
	private UnicodeFont textDrawer;
	
	/**
	 * the message to draw
	 */
	private String msg;
	
	/**
	 * true if we are to draw a box
	 */
	private boolean drawBox;
	
	/**
	 * the image of the box
	 */
	private Image boxImage;
	
	/**
	 * the default image 
	 */
	private Image defaultImage;
	
	/**
	 * Create a new popup object
	 * @throws SlickException
	 */
	@SuppressWarnings("unchecked")
	public Popup() throws SlickException {
		// the text on the screen
		textDrawer = new UnicodeFont(Resources.ACME_FONT_PATH, 20, false, false);
		textDrawer.addAsciiGlyphs();
		textDrawer.getEffects().add(new ColorEffect());
		textDrawer.loadGlyphs();
		
		boxImage = new Image(Resources.POPUP_IMAGE);
		defaultImage = boxImage;
	}
	
	/**
	 * Display the string
	 * @param msg
	 */
	public void display(String msg) {
		this.msg = msg;
		System.out.println(msg);
		drawBox = false;
	}
	
	/**
	 * Display the string in a popup box
	 * @param msg
	 */
	public void displayInBox(String msg) {
		this.msg = msg;
		drawBox = true;
	}
	
	/**
	 * Display an image.
	 * @param path
	 * @throws SlickException
	 */
	public void displayImage(String path) throws SlickException {
		boxImage = new Image(path);
		msg = "";
		drawBox = true;
	}
	
	/**
	 * Stops drawing the popup window.
	 */
	public void stopImage() {
		msg = null;
	}
	
	/**
	 * Draw the current popup box with the current string
	 */
	public void draw() {
		if (msg == null) {
			return;
		}
		
		if (drawBox == true) {
			boxImage.draw(Resources.POPUP_X, Resources.POPUP_Y+20);
			textDrawer.drawString(140, 140, msg);
		} else {
			textDrawer.drawString(100, 100, msg);
		}
	}
}