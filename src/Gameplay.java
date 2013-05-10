import java.util.HashMap;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.tiled.GroupObject;

public class Gameplay extends BasicGame {
	private static int windowWidth = 800;
	private static int windowHeight = 518;
	
	private Player player;
	private Room currentRoom;
	private Time timer;
	
	private Image window;
	private Image heart;
	private Image instructions;
	
	private Popup popup;
	
	private AppGameContainer app;
	
	private HashMap<String, Room> rooms;
	
	private enum STATES {
		playing, paused, lost, won
	}
	
	//State of the game
	private STATES state = STATES.playing;
	
	private UnicodeFont text;
	
	private static final int FRAMERATE = 10;

	// title of the window
	public Gameplay() {
		super("Nightmare");
		rooms = new HashMap<String, Room>();
	}
	
	public void setGameContainer(AppGameContainer app) {
		this.app = app;
	}

	private void initRooms() throws SlickException {
		Room room0 = new Room(480, 480, Resources.ROOM0_PATH, 0, 38, Resources.WALL_WIDTH, "0");
		Room room1 = new Room(480, 480, Resources.ROOM1_PATH, 0, 38, Resources.WALL_WIDTH, "1");
		Room room2 = new Room(480, 480, Resources.ROOM2_PATH, 0, 38, Resources.WALL_WIDTH, "2");
		Room room3 = new Room(480, 480, Resources.ROOM3_PATH, 0, 38, Resources.WALL_WIDTH, "3");
		Room room4 = new Room(480, 480, Resources.ROOM4_PATH, 0, 38, Resources.WALL_WIDTH, "4");
		Room room5 = new Room(480, 480, Resources.ROOM5_PATH, 0, 38, Resources.WALL_WIDTH, "5");
		
		// the first room (room0) has no ghosts

		room1.addCharacter(Room.CharacterTypes.ghost);
		room1.addCharacter(Room.CharacterTypes.ghost);

		room2.addCharacter(Room.CharacterTypes.ghost);
		room2.addCharacter(Room.CharacterTypes.ghost);

		room3.addCharacter(Room.CharacterTypes.gargoyle);
		room3.addCharacter(Room.CharacterTypes.gargoyle);
		room3.addCharacter(Room.CharacterTypes.ghost);
		room3.addCharacter(Room.CharacterTypes.ghost);
		room3.addCharacter(Room.CharacterTypes.ghost);

		room4.addCharacter(Room.CharacterTypes.ghost);
		room4.addCharacter(Room.CharacterTypes.ghost);
		room4.addCharacter(Room.CharacterTypes.ghost);
		room4.addCharacter(Room.CharacterTypes.ghost);

		room5.addCharacter(Room.CharacterTypes.ghost);
		room5.addCharacter(Room.CharacterTypes.ghost);
		room5.addCharacter(Room.CharacterTypes.ghost);
		room5.addCharacter(Room.CharacterTypes.ghost);
		room5.addCharacter(Room.CharacterTypes.ghost);

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
		case won:
			popup.displayInBox("You have won!\n\nPress SPACE to start a new game.");
			break;
		case lost:
			popup.displayInBox("You have lost!\n\nPress SPACE to start a new game.");
			break;
		case playing: case paused:
			break;
		}

		window.draw();
		currentRoom.draw();
		player.draw();
		drawHearts();

		String timeLeft = String.format("Time left: %3d seconds.", timer.getSecondsLeft());
		text.drawString(160, 2, timeLeft, Color.black);

		player.drawInventory();

		instructions.draw(Resources.INSTRUCTIONS_X, Resources.INSTRUCTIONS_Y);
		
		popup.draw();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer arg0) throws SlickException {
		initRooms();
		currentRoom = rooms.get("0");
		
		player = new Player(30, 200, 150);

		window = Resources.getWindowImage();
		heart = Resources.getHeartImage();

		// the text on the screen
		text = new UnicodeFont(Resources.ACME_FONT_PATH, 20, false, false);
		text.addAsciiGlyphs();
		text.getEffects().add(new ColorEffect());
		text.loadGlyphs();
		
		timer = new Time(1500000);
		timer.start();		
		
		popup = new Popup();
		
		instructions = new Image(Resources.INSTRUCTIONS_IMAGE);
	}

	public void restartGame() throws SlickException {
		init(app);
		state = STATES.playing;
	}
	
	@Override
	public void update(GameContainer gc, int deltaTime) throws SlickException {
		Input i = gc.getInput();
		if (i.isKeyPressed(Input.KEY_P)){
			if (state == STATES.paused) {
				state = STATES.playing;
				timer.start();
			} else {
				state = STATES.paused;
				timer.stop();
			}
		}
		
		if ((state == STATES.lost || state == STATES.won) && i.isKeyPressed(Input.KEY_SPACE)) {
			// we want to start a new game
			restartGame();
		}
		
		if (state == STATES.lost) {
			return;
		} else if (state == STATES.paused) {
			return;
		} else if (state == STATES.won) {
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
		
		// move the ghosts
		currentRoom.moveCharaters(deltaTime);
		
		// if we are on a ghost, decrease the health
		if (currentRoom.isPlayerOnAGhost(player.getBoundingBox())) {
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
							state = STATES.won;
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
						player.moveTo(carpetOnTheOtherSide.x(), carpetOnTheOtherSide.y());
					}
				}
			} else {
				// we are on another object
				
				// get the object
				GroupObject go = currentRoom.getTheObjectThePlayerIsStandingOn((int) player.x, (int) player.y);
				if (go != null) {
					if (go.name.toUpperCase().equals("BED")) {
						// it is the bed, we have won
						state = STATES.won;
						timer.stop();
					} else if (go.name.toUpperCase().equals("HEART")) {
						// is is a heart, increase the player's health
						player.increaseHealth(1);
						currentRoom.removeGroupObject(go);
					}
				}
			}
		}
		
		if (timer.timeLeft() == false) {
			// the time's up, we have lost
			state = STATES.lost;
			timer.stop();
		}
	}
	
	//Main
	public static void main(String[] args) throws SlickException {
		Gameplay g = new Gameplay();
		AppGameContainer app = new AppGameContainer(g);
		app.setDisplayMode(windowWidth, windowHeight, false);
		g.setGameContainer(app);
		app.setTargetFrameRate(FRAMERATE);
		app.setVSync(true);
		//app.setShowFPS(false);
		app.start();
	}
	
	//Draws the number of heart (lives) of the player up in the status bar.
	private void drawHearts() {
		int health = player.getHealth();
		
		if (health == 0) {
			state = STATES.lost;
			timer.stop();
			return;
		}
		
		for (int i=0; i<health; i++) {
			heart.draw(i*42+10, 2);
		}
	}
}
