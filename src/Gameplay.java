import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;

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
	
	private enum STATES {
		playing, paused, lost, won
	}
	
	//State of the game
	private STATES state = STATES.playing;
	
	private UnicodeFont text;

	// title of the window
	public Gameplay() {
		super("Nightmare");
	}
	
	public void setGameContainer(AppGameContainer app) {
		this.app = app;
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
		// create a room for the nightmare
		currentRoom = new Room(480, 480, Resources.ROOM0_PATH, 0, 38, Resources.WALL_WIDTH);
		currentRoom.addGhost();
		currentRoom.addGhost();
		currentRoom.addGhost();
		currentRoom.addGhost();

		player = new Player(3, 200, 150);

		window = Resources.getWindowImage();
		heart = Resources.getHeartImage();

		// the text on the screen
		text = new UnicodeFont(Resources.ACME_FONT_PATH, 20, false, false);
		text.addAsciiGlyphs();
		text.getEffects().add(new ColorEffect());
		text.loadGlyphs();
		
		timer = new Time(15000);
		timer.start();		
		
		popup = new Popup();
		
		instructions = new Image(Resources.INSTRUCTIONS_IMAGE);
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
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
			init(app);
			state = STATES.playing;
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
			player.moveY(1, currentRoom, true);
			player.walkDown();
		} else if (i.isKeyDown(Input.KEY_UP)){
			player.moveY(-1, currentRoom, false);
			player.walkUp();
		} else if (i.isKeyDown(Input.KEY_LEFT)) {
			player.moveX(-1, currentRoom, false);
			player.walkLeft();
		} else if (i.isKeyDown(Input.KEY_RIGHT)) {
			player.moveX(1, currentRoom, true);
			player.walkRight();
		} else {
			// stop walking, we are not moving
			player.stopWalking();
		}
		
		// move the ghosts
		currentRoom.moveGhosts();
		
		// if we are on a ghost, decrease the health
		if (currentRoom.isPlayerOnAGhost(player.getBoundingBox())) {
			player.decreaseHealth(1);
		}
		
		// e is the action key, check if we are on an object and do something
		if (i.isKeyPressed(Input.KEY_E)) {
			if (currentRoom.isPlayerOnKey((int) player.x, (int) player.y)) {
				System.out.println("on key");
				player.getInventory().addItem(currentRoom.removeKey());
				player.hasKey(true);
			} else if (currentRoom.isPlayerOnCarpet((int) player.x, (int) player.y)) {
				if (player.hasKey() == true) {
						System.out.println("won");
						state = STATES.won;
						timer.stop();
				} else {
					popup.display("The door won't open without the key.");
				}
			} else {
				System.out.println("nope");
			}
		}
		
		if (timer.timeLeft() == false) {
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
