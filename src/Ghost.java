import java.security.SecureRandom;
import org.newdawn.slick.SlickException;


public class Ghost extends Character {
	/**
	 * The xSpeed of the ghost
	 */
	private float xSpeed;
	
	/**
	 * The ySpeed of the ghost
	 */
	private float ySpeed;
	
	/**
	 * The random number generator, to randomize the ghosts directions
	 */
	private static SecureRandom r;
	
	/**
	 * Create a ghost at the given position
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Ghost(int x, int y) throws SlickException {
		super(0, x, y, Resources.GHOST_WIDTH, Resources.GHOST_HEIGHT);
		currentAnimation = Resources.getGhostsAnimation();
		
		if (r == null) {
			r = new SecureRandom();
		    byte bytes[] = new byte[20];
			r.nextBytes(bytes);
		}
		randomizeDirection();
	}

	/**
	 * Move the ghost.
	 * @param room The room the ghost is in
	 * @param deltaTime The time which has passed
	 */
	public void move(Room room, int deltaTime) {
		float newX = x + xSpeed * deltaTime * Resources.GHOST_MOVEMENT_SPEED;
		float newY = y + ySpeed * deltaTime * Resources.GHOST_MOVEMENT_SPEED;
		
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
	
	/**
	 * Randomize the ghosts direction
	 */
	private void randomizeDirection() {
		while (xSpeed < 0.2 || xSpeed > 0.8) {
			xSpeed = r.nextFloat();	
		}
		
		ySpeed = (float) Math.sqrt(1 - xSpeed * xSpeed);
	}
}
