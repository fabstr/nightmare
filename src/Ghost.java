import java.security.SecureRandom;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Ghost extends Character {

	private float xSpeed;
	private float ySpeed;
	
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

	public void move(Room room) {
		float newX = x + xSpeed;
		float newY = y + ySpeed;
		
		// if we are moving to the right, we want to count width the ghost's width.
		int width = (xSpeed > 0) ? 64 : 0;
		if (!room.canSetX(newX, width)) {
			// we hit a wall
			xSpeed = -xSpeed;
			return;
		}

		// if we are moving down, we want to count width the ghost's height.
		int height = (ySpeed > 0) ? 64 : 0;
		if (!room.canSetY(newY, height)) {
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
		System.out.println("Setting " + xSpeed + " " + ySpeed);
	}
}
