import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Ghost extends Character {

	private float xSpeed;
	private float ySpeed;
	
	public Ghost(int health, int x, int y, Image img) throws SlickException {
		super(health, x, y, img);
		currentAnimation = new Animation(new SpriteSheet(img, 64, 64), 75);
		
		Random r = new Random();
		
		xSpeed = r.nextFloat();
		
		if (xSpeed < 0.2 || xSpeed > 0.8) {
			xSpeed = r.nextFloat();
		}
		
		ySpeed = (float) Math.sqrt(1 - xSpeed * xSpeed);
	}

	public void move(Room room) {
		int newX = Math.round(x + xSpeed);
		int newY = Math.round(y + ySpeed);
		
		if (!room.canSetX(newX)) {
			xSpeed = -xSpeed;
			return;
		}
		
		if (!room.canSetY(newY)) {
			ySpeed = -ySpeed;
			return;
		}
		setX(newX);
		setY(newY);
	}
}
