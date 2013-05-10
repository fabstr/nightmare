import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.GroupObject;

public class AnimatedObject {
	private Animation img;
	private int x;
	private int y;
	
	
	public AnimatedObject(GroupObject go) throws SlickException {
		String imagePath;
		if (go.type.toUpperCase().equals(Resources.IDSTRING_TORCH)) {
			imagePath = Resources.TORCH_PATH;
		} else if (go.type.toUpperCase().equals(Resources.IDSTRING_CANDLESTICK)) {
			imagePath = Resources.CANDLESTICK_PATH;
		} else {
			throw new IllegalArgumentException("Object type " + go.type + " is unknown.");
		}
		
		SpriteSheet spritesheet = new SpriteSheet(imagePath, Resources.ANIMATED_OBJECT_WIDTH, Resources.ANIMATED_OBJECT_HEIGHT);
		this.img = new Animation(spritesheet, Resources.ANIMATED_OBJECT_ANIMATION_SPEED);
		this.x = go.x;
		this.y = go.y;
	}
	
	public void draw() {
		img.draw(x, y);
	}
}
