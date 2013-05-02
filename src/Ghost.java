import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Ghost extends Character {

	private float xSpeed;
	private float ySpeed;
	
	private static Random r;
	
	public Ghost(int health, int x, int y, Image img) throws SlickException {
		super(health, x, y);
		currentAnimation = Resources.getGhostsAnimation();
		
		r = new Random();
		randomizeDirection();
	}

	public void move(Room room) {
		int newX = Math.round(x + xSpeed);
		int newY = Math.round(y + ySpeed);
		
		int width = (xSpeed > 0) ? 64 : 0;
		if (!room.canSetX(newX, width)) {
			xSpeed = -xSpeed;
			return;
		}

		int height = (ySpeed > 0) ? 64 : 0;
		if (!room.canSetY(newY, height)) {
			ySpeed = -ySpeed;
			return;
		}
		setX(newX);
		setY(newY);
	}
	
	private void randomizeDirection() {
		xSpeed = r.nextFloat();	
		ySpeed = (float) Math.sqrt(1 - xSpeed * xSpeed);
	}
}
