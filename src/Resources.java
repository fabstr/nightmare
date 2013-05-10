import java.net.URL;

import org.newdawn.slick.Color;
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
	public static final String LEVELS_FOLDER = "resources/levels/";
	public static final String ROOM0_PATH = ROOMS_FOLDER + "room0.tmx";
	public static final String ROOM1_PATH = ROOMS_FOLDER + "room1.tmx";
	public static final String ROOM2_PATH = ROOMS_FOLDER + "room2.tmx";
	public static final String ROOM3_PATH = ROOMS_FOLDER + "room3.tmx";
	public static final String ROOM4_PATH = ROOMS_FOLDER + "room4.tmx";
	public static final String ROOM5_PATH = ROOMS_FOLDER + "room5.tmx";
	public static final String GREEN_GUY_PATH = SPRITES_FOLDER + "green_guy.png";
	public static final String BROWN_GUY_PATH = SPRITES_FOLDER + "brown_guy.png";
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
	public static final int POPUP_X = 100;
	public static final int POPUP_Y = 100;
	public static final int POPUP_WIDTH = 300;
	public static final int POPUP_HEIGHT = 200;
	public static final String POPUP_IMAGE = SPRITES_FOLDER + "box.png";
	public static final String INSTRUCTIONS_IMAGE = SPRITES_FOLDER + "instructions.png";
	public static final int INSTRUCTIONS_X = 480;
	public static final int INSTRUCTIONS_Y = 38;
	public static final String GARGOYLE_SPRITESHEET = SPRITES_FOLDER + "movingGargoyle.png";
	public static final int DEBUGGING_LIVES = 30;
	public static final int PLAYER_LIVES = 3;
	public static final int PLAYER_START_X = 150;
	public static final int PLAYER_START_Y = 200;
	public static final int DEBUGGING_TIME_LIMIT = 1500000;
	public static final int PLAYING_TIME_LIMIT = 120000;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 518;
	public static final int FRAMERATE = 60;
	public static final int HEART_WIDTH = 42;
	public static final int HEART_SPACING = 10;
	public static final int HEART_Y = 2;
	public static final int TIME_LEFT_STRING_X = 160;
	public static final int TIME_LEFT_STRING_Y = 2;
	public static final Color TIME_LEFT_COLOUR = Color.black;
	public static final String MSG_WON_PATH = SPRITES_FOLDER + "winningBox.png";
	public static final String MSG_LOST_PATH = SPRITES_FOLDER + "losingBox.png";
	public static final String TIME_LEFT_STRING = "Time left: %3d seconds.";
	public static final String ROOM_TO_START_IN_ID = "0";
	public static final int TEXT_SIZE = 20;
	public static final String IDSTRING_KEY = "key";
	public static final Object IDSTRING_BED = "BED";
	public static final Object IDSTRING_HEART = "HEART";
	public static final Object IDSTRING_CARPET_LOCKED = "YES";
	public static final String IDSTRING_CARPET = "CARPET";
	public static final String IDSTRING_WALLS = "WALLS";
	public static final String IDSTRING_CARPET_NOT_LOCKED = "NO";
	public static final String IDSTRING_EXIT = "EXIT";
	public static final String IDSTRING_ANIMATED = "ANIMATED";
	public static final String PROPSSTRING_LOCKED = "LOCKED";
	public static final int GARGOYLE_WIDTH = 64;
	public static final int GARGOYLE_HEIGHT = 64;
	public static final float GHOST_MOVEMENT_SPEED = 0.3f;
	public static final float PLAYER_MOVEMENT_SPEED = 0.2f;
	public static final int INVENTORY_X = 430;
	public static final int INVENTORY_Y = 2;
	public static final int INVENTORY_SPACING = 10;
	public static final int ROOM_WIDTH = 480;
	public static final int ROOM_HEIGHT = 480;
	public static final int ROOM_X = 0;
	public static final int ROOM_Y= 38;
	public static final int ANIMATED_OBJECT_WIDTH = 32;
	public static final int ANIMATED_OBJECT_HEIGHT = 32;
	public static final String IDSTRING_TORCH = "TORCH";
	public static final String IDSTRING_CANDLESTICK = "CANDLESTICK";
	public static final String TORCH_PATH = SPRITES_FOLDER + "movingTorch.png";
	public static final String CANDLESTICK_PATH = SPRITES_FOLDER + "movingCandle.png";
	public static final int ANIMATED_OBJECT_ANIMATION_SPEED = 333;
	public static final String CHAINSAW_PATH = SPRITES_FOLDER + "movingChainsaw.png";
	public static final String SCREAM_PATH = SPRITES_FOLDER + "movingScream.png";
	public static final String DRACULA_PATH = SPRITES_FOLDER + "movingDracula.png";
	public static final String GRUDGE_PATH = SPRITES_FOLDER + "movingGrudge.png";
	public static final String CLOWN_PATH = SPRITES_FOLDER + "movingSaw.png";
	public static final String WINNING_ANIMATION_PATH = SPRITES_FOLDER + "movingWinning.png";
	public static final int WINNING_ANIMATION_X = 330;
	public static final int WINNING_ANIMATION_Y = 250;
	
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
	
	public static Animation getGargoyleAnimation() throws SlickException {
		SpriteSheet garg = new SpriteSheet(GARGOYLE_SPRITESHEET, 64, 64);
		return new Animation(garg, 100);
	}
	
	public static Image getInstructionsImage() throws SlickException {
		return new Image(INSTRUCTIONS_IMAGE);
	}

	public static Animation getWinningAnimation() throws SlickException {
		SpriteSheet anim = new SpriteSheet(WINNING_ANIMATION_PATH, 26, 41);
		return new Animation(anim, 100);
	}
}
