import java.security.SecureRandom;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Ghost extends Character {

	private float xSpeed;
	private float ySpeed;
	
	// pixels per millisecond
	private static final float MOVEMENT_SPEED = 0.1f;
	
	private static SecureRandom r;
	
	public Ghost(int health, int x, int y, Image img) throws SlickException {
		super(health, x, y, Resources.GHOST_WIDTH, Resources.GHOST_HEIGHT);
		currentAnimation = Resources.getGhostsAnimation();
		
		if (r == null) {
			r = new SecureRandom();
		    byte bytes[] = new byte[20];
			r.nextBytes(bytes);
		}
		randomizeDirection();
	}

	public void move(Room room, int deltaTime) {
		float newX = x + xSpeed * deltaTime * MOVEMENT_SPEED;
		float newY = y + ySpeed * deltaTime * MOVEMENT_SPEED;
		
		// if we are moving to the right, we want to count width the ghost's width.
		int width = (xSpeed > 0) ? 64 : 0;
		if (!room.canSetXY(newX, y, width, height)) {
			// we hit a wall
			xSpeed = -xSpeed;
			return;
		}

		// if we are moving down, we want to count width the ghost's height.
		int height = (ySpeed > 0) ? 64 : 0;
		if (!room.canSetXY(x, newY, width, height)) {
			// we hit a wall
			ySpeed = -ySpeed;
			return;
		}
		
		setX(newX);
		setY(newY);
	}
	
	private void randomizeDirection() {
		while (xSpeed < 0.2 || xSpeed > 0.8) {
			xSpeed = r.nextFloat();	
		}
		
		ySpeed = (float) Math.sqrt(1 - xSpeed * xSpeed);
	}
}
