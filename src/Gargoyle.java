import org.newdawn.slick.SlickException;


public class Gargoyle extends Character {

	public Gargoyle(int health, int x, int y, int width, int height)
			throws SlickException {
		super(health, x, y, width, height);
		currentAnimation = Resources.getGargoyleAnimation();
	}
	
	public Gargoyle(int x, int y) throws SlickException {
		this(3, x, y, 64, 64);
	}
}
