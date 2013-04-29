import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Ghost extends Character {

	public Ghost(int health, int x, int y, Image img) throws SlickException {
		super(health, x, y, img);
		currentAnimation = new Animation(new SpriteSheet(img, 64, 64), 75);
	}
	
}
