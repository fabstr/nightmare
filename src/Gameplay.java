import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;

public class Gameplay extends BasicGame {
	private static int windowWidth = 800;
	private static int windowHeight = 600;
	
	private Player player;
	private Room currentRoom;
	private Time timer;
	
	private Image window;
	private Image heart;
	
	private enum STATES {
		playing, paused, lost
	}
	
	private STATES state;
	
	private UnicodeFont text;

	public Gameplay() {
		super("Nightmare");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		window.draw();
		currentRoom.draw();
		player.draw();
		drawHearts();
		
		String timeLeft = String.format("Time left: %3d seconds.", timer.getSecondsLeft());
		text.drawString(160, 2, timeLeft, Color.black);
		
		player.drawInventory();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer arg0) throws SlickException {
		// create a room for the nightmare
		currentRoom = new Room(480, 480, Resources.ROOM0_PATH, 100, 70, Resources.WALL_WIDTH);
		currentRoom.addGhost();
		currentRoom.addGhost();
		currentRoom.addGhost();
		// create a nightmare
	//	Nightmare nm = new Nightmare(

		player = new Player(3, 200, 150);

		window = Resources.getWindowImage();
		heart = Resources.getHeartImage();

		// the text on the scresen
		text = new UnicodeFont(Resources.ACME_FONT_PATH, 20, false, false);
		text.addAsciiGlyphs();
		text.getEffects().add(new ColorEffect());
		text.loadGlyphs();
		
		timer = new Time(110000);
		timer.start();		
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		Input i = gc.getInput();
		
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
		if (currentRoom.isPlayerOnAGhost(player.x, player.y, Resources.PLAYER_WIDTH, Resources.PLAYER_HEIGHT)) {
			player.decreaseHealth(1);
		}
		
		// e is the action key, check if we are on an object and do something
		if (i.isKeyPressed(Input.KEY_E)) {
			if (currentRoom.isPlayerOnKey(player.x, player.y)) {
				System.out.println("on key");
				player.getInventory().addItem(currentRoom.removeKey());
			} else if (currentRoom.isPlayerOnCarpet(player.x, player.y)) {
				
				System.out.println("on carpet");
			} else {
				System.out.println("nope");
			}
		}
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Gameplay());
		app.setDisplayMode(windowWidth, windowHeight, false);
		app.start();
	}
	
	private void drawHearts() {
		int health = player.getHealth();
		for (int i=0; i<health; i++) {
			heart.draw(i*42+10, 2);
		}
	}
}
