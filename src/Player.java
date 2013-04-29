import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Player extends Character {
	
	private Animation left;
	private Animation right;
	private Animation up;
	private Animation down;
	
	
	public Player(int health, int x, int y, Image img) throws SlickException {
		super(health, x, y, img);
		
		Image rightImg = img.getSubImage(0, 0, 78, 37);
		Image leftImg = img.getSubImage(0, 37, 78, 37);
		Image upImg = img.getSubImage(0, 74, 78, 37);
		Image downImg = img.getSubImage(0, 111, 78, 37);
		left = new Animation(new SpriteSheet(leftImg, 26, 37), 300);
		right = new Animation(new SpriteSheet(rightImg, 26, 37), 300);
		up = new Animation(new SpriteSheet(upImg, 26, 37), 300);
		down = new Animation(new SpriteSheet(downImg, 26, 37), 300);
		
		currentAnimation = down;
	}


	public void faceDown() {
		currentAnimation = down;
	}
	
	public void faceUp() {
		currentAnimation = up;
	}
	
	public void faceLeft() {
		currentAnimation = left;
	}
	
	public void faceRight() {
		currentAnimation = right;
	}
	
	public void moveX(int amount, Room room, boolean countImageWidth) {
		int newX = this.x + amount;
		
		if (countImageWidth) {
			newX += currentAnimation.getWidth();
		}
		
		if (!room.canSetX(newX)) {
			return;
		}
		
		if (countImageWidth) {
			newX -= currentAnimation.getWidth();
		}
		
		setX(newX);
	}
	
	public void moveY(int amount, Room room, boolean countImageWidth) {
		int newY = this.y + amount;
		
		if (countImageWidth) {
			newY += currentAnimation.getHeight();
		}
		
		if (!room.canSetY(newY)) {
			return;
		}
		
		if (countImageWidth) {
			newY -= currentAnimation.getHeight();
		}
		
		setY(newY);
	}
}
