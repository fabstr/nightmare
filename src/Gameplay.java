import org.newdawn.slick.*;

public class Gameplay extends BasicGame {
	private static int windowWidth = 800;
	private static int windowHeight = 640;
	
	private Player player;
	private Room currentRoom;
	private Time timer;

	public Gameplay() {
		super("Nightmare");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		currentRoom.draw();
		player.draw();
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// create a room for the nightmare
		currentRoom = new Room(400, 300, "/Users/fabianstrom/uv/nightmare/resources/graphics/rooms/room0.png", 0, 0);
		currentRoom.addGhost();
		currentRoom.addGhost();
		currentRoom.addGhost();
		// create a nightmare
	//	Nightmare nm = new Nightmare(

		player = new Player(3, 200, 150, new Image("/Users/fabianstrom/uv/nightmare/resources/graphics/sprites/characters.png"));
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		Input i = gc.getInput();
		
		if (i.isKeyDown(Input.KEY_DOWN)) {
			player.moveY(1);
			player.faceDown();
		} else if (i.isKeyDown(Input.KEY_UP)){
			player.moveY(-1);
			player.faceUp();
		} else if (i.isKeyDown(Input.KEY_LEFT)) {
			player.moveX(-1);
			player.faceLeft();
		} else if (i.isKeyDown(Input.KEY_RIGHT)) {
			player.moveX(1);
			player.faceRight();
		}
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Gameplay());
		app.setDisplayMode(windowWidth, windowHeight, false);
		app.start();
	}
}
