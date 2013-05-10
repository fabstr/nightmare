import java.util.HashMap;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.tiled.GroupObject;

public class Gameplay extends BasicGame {
	/**
	 * True if we want debugging. 
	 * If true, the player can press 0,1,2,3,4,5 to go to the respective room.
	 * Also, press P or L to increase/decrease the player's lives.
	 */
	private static final boolean DEBUGGING = true;
	
	/**
	 * The player object
	 */
	private Player player;
	
	/**
	 * The object for the current room
	 */
	private Room currentRoom;
	
	/**
	 * To count down, then the time is 0 the player has lost
	 */
	private Time timer;
	
	/**
	 * The window image draws lines to hold the lives/inventory
	 */
	private Image window;
	
	/**
	 * The heart image (to show the player's health)
	 */
	private Image heart;
	
	/**
	 * The instructions of how to play
	 */
	private Image instructions;
	
	/**
	 * The popup class displays a message
	 */
	private Popup popup;
	
	/**
	 * We need this to restart the game (it is restarted by calling init()).
	 */
	private AppGameContainer app;
	
	/**
	 * To keep track of the rooms
	 */
	private HashMap<String, Room> rooms;
	
	/**
	 * The states the game can be in
	 */
	private enum STATES {
		PLAYING, PAUSED, LOST, WON
	}
	
	/**
	 * The current state of the game. We begin with PLAYING
	 */
	private STATES state = STATES.PLAYING;
	
	/**
	 * The text object draws the time-left message
	 */
	private UnicodeFont text;
	
	/**
	 * Set the title of the window to Nightmare
	 * (and initialize the rooms hash map)
	 */
	public Gameplay() {
		super("Nightmare");
		rooms = new HashMap<String, Room>();
	}
	
	/**
	 * Set the AppGameContainer.
	 * @param app The AppGameContainer
	 */
	public void setGameContainer(AppGameContainer app) {
		this.app = app;
	}

	/**
	 * Initializes the rooms (and adds characters) and adds the rooms to the 
	 * rooms hash map.
	 * @throws SlickException
	 */
	private void initRooms() throws SlickException {
		// create the rooms
		Room room0 = new Room(Resources.ROOM0_PATH, "0");
		Room room1 = new Room(Resources.ROOM1_PATH, "1");
		Room room2 = new Room(Resources.ROOM2_PATH, "2");
		Room room3 = new Room(Resources.ROOM3_PATH, "3");
		Room room4 = new Room(Resources.ROOM4_PATH, "4");
		Room room5 = new Room(Resources.ROOM5_PATH, "5");
		
		// add the characters to the rooms
		// the first room (room0) has no characters
		room1.addCharacter(Room.CharacterTypes.ghost, 1);
		room2.addCharacter(Room.CharacterTypes.ghost, 2);
		room3.addCharacter(Room.CharacterTypes.gargoyle, 2);
		room3.addCharacter(Room.CharacterTypes.ghost, 2);
		room3.addCharacter(Room.CharacterTypes.dracula, 1);
		room4.addCharacter(Room.CharacterTypes.ghost, 4);
		room5.addCharacter(Room.CharacterTypes.scream, 1);
		room5.addCharacter(Room.CharacterTypes.chainsaw, 1);

		// add the rooms to the hash map
		rooms.put("0", room0);
		rooms.put("1", room1);
		rooms.put("2", room2);
		rooms.put("3", room3);
		rooms.put("4", room4);
		rooms.put("5", room5);
	}
	
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		switch (state) {
		case WON:
			popup.displayInBox(Resources.MSG_WON);
			break;
		case LOST:
			popup.displayInBox(Resources.MSG_LOST);
			break;
		case PLAYING: case PAUSED:
			break;
		}

		window.draw();
		currentRoom.draw();
		player.draw();
		drawHearts();

		String timeLeft = String.format(Resources.TIME_LEFT_STRING, timer.getSecondsLeft());
		text.drawString(Resources.TIME_LEFT_STRING_X, Resources.TIME_LEFT_STRING_Y, timeLeft, Resources.TIME_LEFT_COLOUR);

		player.drawInventory();

		instructions.draw(Resources.INSTRUCTIONS_X, Resources.INSTRUCTIONS_Y);
		
		popup.draw();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer arg0) throws SlickException {
		// init the rooms and set the current room
		initRooms();
		currentRoom = rooms.get(Resources.ROOM_TO_START_IN_ID);
		
		// create the player with the correct amount of lives
		int lives = (DEBUGGING) ? Resources.DEBUGGING_LIVES : Resources.PLAYER_LIVES;
		player = new Player(lives, Resources.PLAYER_START_X, Resources.PLAYER_START_Y);
		
		// set the window, heart and instruction images
		window = Resources.getWindowImage();
		heart = Resources.getHeartImage();
		instructions = Resources.getInstructionsImage();
		
		// create the text drawing object
		text = new UnicodeFont(Resources.ACME_FONT_PATH, Resources.TEXT_SIZE, false, false);
		text.addAsciiGlyphs();
		text.getEffects().add(new ColorEffect());
		text.loadGlyphs();
		
		// set the timer 
		int time = (DEBUGGING) ? Resources.DEBUGGING_TIME_LIMIT : Resources.PLAYING_TIME_LIMIT;
		timer = new Time(time);
		timer.start();		
		
		// create the popup object
		popup = new Popup();
		
	}

	/**
	 * Restart the game.
	 * We do this by calling init() and changing the state to PLAYING.
	 * @throws SlickException
	 */
	public void restartGame() throws SlickException {
		init(app);
		state = STATES.PLAYING;
	}
	
	@Override
	public void update(GameContainer gc, int deltaTime) throws SlickException {
		Input i = gc.getInput();
		if (i.isKeyPressed(Input.KEY_P)){
			if (state == STATES.PAUSED) {
				state = STATES.PLAYING;
				timer.start();
			} else {
				state = STATES.PAUSED;
				timer.stop();
			}
		}
		
		if ((state == STATES.LOST || state == STATES.WON) && 
			 i.isKeyPressed(Input.KEY_SPACE)) {
			// we want to start a new game
			restartGame();
		}
		
		if (state == STATES.LOST || state == STATES.PAUSED || state == STATES.WON) {
			return;
		}
		
		// move the player and set the right animation
		if (i.isKeyDown(Input.KEY_DOWN)) {
			player.moveY(1, currentRoom, true, deltaTime);
			player.walkDown();
		} else if (i.isKeyDown(Input.KEY_UP)){
			player.moveY(-1, currentRoom, false, deltaTime);
			player.walkUp();
		} else if (i.isKeyDown(Input.KEY_LEFT)) {
			player.moveX(-1, currentRoom, false, deltaTime);
			player.walkLeft();
		} else if (i.isKeyDown(Input.KEY_RIGHT)) {
			player.moveX(1, currentRoom, true, deltaTime);
			player.walkRight();
		} else {
			// stop walking, we are not moving
			player.stopWalking();
		}
		
		if (DEBUGGING) {
			// some debugging actions
			if (i.isKeyDown(Input.KEY_0)) {
				currentRoom = rooms.get("" + 0);
			} else if (i.isKeyDown(Input.KEY_1)) {
				currentRoom = rooms.get("" + 1);
			} else if (i.isKeyDown(Input.KEY_2)) {
				currentRoom = rooms.get("" + 2);
			} else if (i.isKeyDown(Input.KEY_3)) {
				currentRoom = rooms.get("" + 3);
			} else if (i.isKeyDown(Input.KEY_4)) {
				currentRoom = rooms.get("" + 4);
			} else if (i.isKeyDown(Input.KEY_5)) {
				currentRoom = rooms.get("" + 5);
			} else if (i.isKeyDown(Input.KEY_P)) {
				player.increaseHealth(1);
			} else if (i.isKeyDown(Input.KEY_L)) {
				player.increaseHealth(-1);
			}
		}
		
		// move the ghosts
		currentRoom.moveCharaters(deltaTime);
		
		// if we are on a ghost, decrease the health
		if (currentRoom.isPlayerOnACharacter(player.getBoundingBox())) {
			player.decreaseHealth(1);
		}

		// e is the action key, check if we are on an object and do something
		if (i.isKeyPressed(Input.KEY_E)) {
			// the action key is pressed
			if (currentRoom.isPlayerOnKey((int) player.x, (int) player.y)) {
				// we are on a key, pick it up
				currentRoom.removeKey();
				player.addKey();
			} else if (currentRoom.isPlayerOnACarpet((int) player.x, (int) player.y)) {
				// we are on a carpet, move to the other room (if we have the key or the carpet isn't locked)
				Carpet carpet = currentRoom.getTheCarpetThePlayerIsStandingOn((int) player.x, (int) player.y);
				if (carpet != null) {
					if (carpet.isLocked()) {
						if (player.hasKey()) {
							// we have the key, unlock the carpet
							carpet.unlock();
							player.removeAKey();
						}
					}

					if (carpet.isExitCarpet()) {
						// we are on an exit carpet, we have won
						if (carpet.isLocked() == false) {
							state = STATES.WON;
							timer.stop();
						}
					} else if (carpet.isLocked() == false) {
						// the carpet isn't locked, we can move to the other room
						
						// we want the carpet in the other room that points to the current room
						String currentId = currentRoom.getId();
						
						// the room we are about to switch to
						Room otherRoom = rooms.get(carpet.getTarget());
						
						// get the carpet on the other side
						Carpet carpetOnTheOtherSide = otherRoom.getTheCarpetWithTheTarget(currentId);
						
						// switch to the other room
						currentRoom = otherRoom;
						
						// move the player to the carpet in the room we switched to
						player.moveTo(carpetOnTheOtherSide.getX(), carpetOnTheOtherSide.getY());
					}
				}
			} else {
				// we are on another object
				
				// get the object
				GroupObject go = currentRoom.getTheObjectThePlayerIsStandingOn((int) player.x, (int) player.y);
				if (go != null) {
					if (go.name.toUpperCase().equals(Resources.IDSTRING_BED)) {
						// it is the bed, we have won
						state = STATES.WON;
						timer.stop();
					} else if (go.name.toUpperCase().equals(Resources.IDSTRING_HEART)) {
						// is is a heart, increase the player's health
						player.increaseHealth(1);
						currentRoom.removeGroupObject(go);
					}
				}
			}
		}
		
		if (timer.timeLeft() == false || player.getHealth() == 0) {
			// the time's up, we have lost
			state = STATES.LOST;
			timer.stop();
		}
	}
	
	//Main
	public static void main(String[] args) throws SlickException {
		Gameplay g = new Gameplay();
		AppGameContainer app = new AppGameContainer(g);
		app.setDisplayMode(Resources.WINDOW_WIDTH, Resources.WINDOW_HEIGHT, false);
		g.setGameContainer(app);
		app.setTargetFrameRate(Resources.FRAMERATE);
		app.setVSync(true);
		
		if (DEBUGGING) {
			app.setShowFPS(true);
			app.setVerbose(true);
		} else {
			app.setShowFPS(false);
			app.setVerbose(false);
		}
		
		app.start();
	}
	
	/**
	 * Draws the number of hearts (lives of the player) in the status bar.
	 */
	private void drawHearts() {
		for (int i=0; i<player.getHealth(); i++) {
			heart.draw(i*Resources.HEART_WIDTH + Resources.HEART_SPACING, Resources.HEART_Y);
		}
	}
}
