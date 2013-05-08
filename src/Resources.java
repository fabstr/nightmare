import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Animation;

public class Resources {
	public static final String ROOMS_FOLDER = "resources/graphics/rooms/";
	public static final String SPRITES_FOLDER = "resources/graphics/sprites/";
	public static final String FONTS_FOLDER = "resources/fonts/";
	public static final String ROOM_TILESHEETS_FOLDER = SPRITES_FOLDER;
	
	public static final String WINDOW_PATH = "resources/graphics/window.png";
	
	public static final String ROOM0_PATH = ROOMS_FOLDER + "room0.tmx";
	
	public static final String GREEN_GUY_PATH = SPRITES_FOLDER + "green_guy.png";
	public static final String BROWN_GYU_PATH = SPRITES_FOLDER + "brown_guy.png";
	public static final String GHOST_SPRITESHEET = SPRITES_FOLDER + "ghost.png";
	public static final String OBJECTS_SPRITESHEET = SPRITES_FOLDER + "objects.png";
	public static final String HEART_PATH = SPRITES_FOLDER + "heart.png";
	
	public static final String ACME_FONT_PATH = FONTS_FOLDER + "acme.TTF";
	
	public static final int PLAYER_WIDTH = 26;
	public static final int PLAYER_HEIGHT = 37;
	public static final int GHOST_WIDTH = 64;
	public static final int GHOST_HEIGHT = 50;
	
	public static final int OBJECTS_TILES_WIDTH = 32;
	public static final int OBJECTS_TILES_HEIGHT = 32;
	
	public static final int PLAYER_ANIMATION_SPEED = 333;
	public static final int GHOST_ANIMATION_SPEED = 100;
	
	public static final int WALL_WIDTH = 32;

	public static final String KEY_STRING_ID = "key";
	public static final String CARPET_STRING_ID = "carpet";
	
	/**
	 * Return the key image.
	 * @return
	 * @throws SlickException
	 */
	public static Image getKeyImage() throws SlickException {
		SpriteSheet objects = new SpriteSheet(OBJECTS_SPRITESHEET, OBJECTS_TILES_WIDTH, OBJECTS_TILES_HEIGHT);
		return objects.getSubImage(0*OBJECTS_TILES_WIDTH, 0*OBJECTS_TILES_HEIGHT);
	}
	
	/**
	 * Return a horizontal carpet.
	 * @return
	 * @throws SlickException
	 */
	public static Image getHorizontalCarpet () throws SlickException {
		SpriteSheet objects = new SpriteSheet(OBJECTS_SPRITESHEET, OBJECTS_TILES_WIDTH, OBJECTS_TILES_HEIGHT);
		// we want the image at row 1 column 0
		return objects.getSubImage(0*OBJECTS_TILES_WIDTH, 1*OBJECTS_TILES_HEIGHT);
	}
	
	/**
	 * Return a vertical carpet.
	 * @return
	 * @throws SlickException
	 */
	public static Image getVerticalCarpet () throws SlickException {
		SpriteSheet objects = new SpriteSheet(OBJECTS_SPRITESHEET, OBJECTS_TILES_WIDTH, OBJECTS_TILES_HEIGHT);
		// we want the image at row 0 column 1
		return objects.getSubImage(1*OBJECTS_TILES_WIDTH, 0*OBJECTS_TILES_HEIGHT);
	}
	
	/**
	 * Return the walking animation (image 0 and 2) from the given row (starting
	 * at 0).
	 * @param row The row where the player is faced left/right/up/down.
	 * @param playerSheet An image with of the player.
	 * @return The walking animation with the speed of PLAYER_ANIMATION_SPEED.
	 */
	private static Animation getWalkingAnimation(int row, Image playerSheet) {
		// the left face player is on the second row,
		Image imageRow = playerSheet.getSubImage(0*PLAYER_WIDTH, row*PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
		
		// image 0 and 2 makes up the animation
		Image left = imageRow.getSubImage(0, 0, PLAYER_WIDTH, PLAYER_HEIGHT);
		Image right = imageRow.getSubImage(2*PLAYER_WIDTH, 0, PLAYER_WIDTH, PLAYER_HEIGHT);
		return new Animation(new Image[]{left, right}, PLAYER_ANIMATION_SPEED);
	}
	
	private static Animation getPlayerStanding(int row, Image playerSheet) {
		// the left face player is on the second row,
		Image imageRow = playerSheet.getSubImage(0*PLAYER_WIDTH, row*PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
		
		// image 0 and 2 makes up the animation
		Image img = imageRow.getSubImage(1*PLAYER_WIDTH, 0, PLAYER_WIDTH, PLAYER_HEIGHT);
		return new Animation(new Image[]{img}, PLAYER_ANIMATION_SPEED);
	}
	
	/**
	 * Return the animation where the player is walking (ie faced) to the left.
	 * @param playerSheet
	 * @return
	 */
	public static Animation getPlayerWalkingLeft(Image playerSheet) {
		return getWalkingAnimation(1, playerSheet);
	}
	
	/**
	 * Return the animation where the player is walking (ie faced) to the right.
	 * @param playerSheet
	 * @return
	 */
	public static Animation getPlayerWalkingRight(Image playerSheet) {
		return getWalkingAnimation(0, playerSheet);
	}
	
	/**
	 * Return the animation where the player is walking (ie faced) to the up.
	 * @param playerSheet
	 * @return
	 */
	public static Animation getPlayerWalkingUp(Image playerSheet) {
		return getWalkingAnimation(2, playerSheet);
	}
	
	/**
	 * Return the animation where the player is walking (ie faced) to the down.
	 * @param playerSheet
	 * @return
	 */
	public static Animation getPlayerWalkingDown(Image playerSheet) {
		return getWalkingAnimation(3, playerSheet);
	}
	
	public static Animation getGhostsAnimation() throws SlickException {
		SpriteSheet ghost = new SpriteSheet(GHOST_SPRITESHEET, GHOST_WIDTH, GHOST_HEIGHT);
		return new Animation(ghost, GHOST_ANIMATION_SPEED);
	}
	
	public static Image getHeartImage() throws SlickException {
		return new Image(HEART_PATH);
	}
	
	public static Image getWindowImage() throws SlickException {
		return new Image(WINDOW_PATH);
	}
	
	public static Image getGhostImage() throws SlickException {
		return new Image(GHOST_SPRITESHEET);
	}

	public static Animation getPlayerStandingLeft(Image img) {
		return getPlayerStanding(1, img);
	}

	public static Animation getPlayerStandingRight(Image img) {
		return getPlayerStanding(0, img);
	}

	public static Animation getPlayerStandingUp(Image img) {
		return getPlayerStanding(2, img);
	}

	public static Animation getPlayerStandingDown(Image img) {
		return getPlayerStanding(3, img);
	}

	public static Image getCarpetImage() throws SlickException {
		SpriteSheet objects = new SpriteSheet(OBJECTS_SPRITESHEET, OBJECTS_TILES_WIDTH, OBJECTS_TILES_HEIGHT);
		//return objects.getSubImage(1*OBJECTS_TILES_WIDTH, 0*OBJECTS_TILES_HEIGHT);
		return objects.getSprite(1, 0);
	}
}
