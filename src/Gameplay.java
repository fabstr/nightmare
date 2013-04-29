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
	
	
	private UnicodeFont font;

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
		font.drawString(160, 2, timeLeft, Color.black);
		
		player.drawInventory();
		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// create a room for the nightmare
		currentRoom = new Room(640, 480, "resources/graphics/rooms/room0.png", 100, 70);
		currentRoom.addGhost();
		currentRoom.addGhost();
		currentRoom.addGhost();
		// create a nightmare
	//	Nightmare nm = new Nightmare(

		player = new Player(3, 200, 150, new Image("resources/graphics/sprites/characters.png"));

		window = new Image("resources/graphics/window.png");
		heart = new Image("resources/graphics/sprites/heart.png");

		font = new UnicodeFont("resources/fonts/acme.ttf", 20, false, false);
		font.addAsciiGlyphs();
		font.getEffects().add(new ColorEffect());
		font.loadGlyphs();
		
		timer = new Time(110000);
		timer.start();		
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		Input i = gc.getInput();
		
		if (i.isKeyDown(Input.KEY_DOWN)) {
			player.moveY(1, currentRoom, true);
			player.faceDown();
		} else if (i.isKeyDown(Input.KEY_UP)){
			player.moveY(-1, currentRoom, false);
			player.faceUp();
		} else if (i.isKeyDown(Input.KEY_LEFT)) {
			player.moveX(-1, currentRoom, false);
			player.faceLeft();
		} else if (i.isKeyDown(Input.KEY_RIGHT)) {
			player.moveX(1, currentRoom, true);
			player.faceRight();
		}
		
		currentRoom.moveGhosts();
		
		if (currentRoom.isPlayerOnAGhost(player.x, player.y, 26, 37)) {
			player.decreaseHealth(1);
		}
		
		if (currentRoom.isPlayerOnKey(player.x, player.y)) {
			System.out.println("on key");
			player.getInventory().addItem(currentRoom.removeKey());
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
