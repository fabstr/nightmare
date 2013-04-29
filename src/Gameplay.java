import org.newdawn.slick.*;

public class Gameplay extends BasicGame {
	private static int windowWidth = 800;
	private static int windowHeight = 600;
	
	private Player player;
	private Room currentRoom;
	private Time timer;
	
	private Image window;

	public Gameplay() {
		super("Nightmare");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		window.draw();
		currentRoom.draw();
		player.draw();
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// create a room for the nightmare
		currentRoom = new Room(640, 480, "/Users/fabianstrom/uv/nightmare/resources/graphics/rooms/room0.png", 100, 70);
		currentRoom.addGhost();
		currentRoom.addGhost();
		currentRoom.addGhost();
		// create a nightmare
	//	Nightmare nm = new Nightmare(

		player = new Player(3, 200, 150, new Image("/Users/fabianstrom/uv/nightmare/resources/graphics/sprites/characters.png"));
		
		window = new Image("/Users/fabianstrom/uv/nightmare/resources/graphics/window.png");
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
			System.out.println("THe player is on a ghost.");
		}
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Gameplay());
		app.setDisplayMode(windowWidth, windowHeight, false);
		app.start();
	}
}
