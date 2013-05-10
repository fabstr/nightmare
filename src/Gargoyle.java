import org.newdawn.slick.SlickException;

public class Gargoyle extends Character {
	/**
	 * Create a new gargoyle at the given position.
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Gargoyle(int x, int y) throws SlickException {
		super(0, x, y, Resources.GARGOYLE_WIDTH, Resources.GARGOYLE_HEIGHT);
		currentAnimation = Resources.getGargoyleAnimation();
	}
	
	/**
	 * The gargoyle does not move, this method does nothing.
	 */
	public void move(Room r, int deltaTime) {}
}
