import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class AnimationManager {
	/**
	 * The directions that are possible
	 */
	public static enum directions {
		LEFT, RIGHT, UP, DOWN, STANDING_LEFT, STANDING_RIGHT, STANDING_UP, 
		STANDING_DOWN
	}
	
	/**
	 * The animation for walking left
	 */
	private Animation walkingLeft;
	
	/**
	 * The animation for walking right
	 */
	private Animation walkingRight;

	/**
	 * The animation for walking up
	 */
	private Animation walkingUp;

	/**
	 * The animation for walking down
	 */
	private Animation walkingDown;
	
	/**
	 * The animation for standing and looking left
	 */
	private Animation standingLeft;

	/**
	 * The animation for standing and looking right
	 */
	private Animation standingRight;

	/**
	 * The animation for standing and looking up
	 */
	private Animation standingUp;

	/**
	 * The animation for standing and looking down
	 */
	private Animation standingDown;
	
	/**
	 * Create a new AnimationManager and get the animations from the given
	 * sprite sheet.
	 * @param spriteSheetPath
	 * @throws SlickException
	 */
	public AnimationManager(String spriteSheetPath) throws SlickException {
		Image img = new Image(spriteSheetPath);
		walkingLeft = Resources.getPlayerWalkingLeft(img);
		walkingRight = Resources.getPlayerWalkingRight(img);
		walkingUp = Resources.getPlayerWalkingUp(img);
		walkingDown = Resources.getPlayerWalkingDown(img);
		standingLeft = Resources.getPlayerStandingLeft(img);
		standingRight = Resources.getPlayerStandingRight(img);
		standingUp = Resources.getPlayerStandingUp(img);
		standingDown = Resources.getPlayerStandingDown(img);
	}
	
	/**
	 * Return the animation from the given direction
	 * @param direction
	 * @return
	 */
	public Animation getAnimation(directions direction) {
		switch (direction) {
		case LEFT:
			return walkingLeft;
		case RIGHT:
			return walkingRight;
		case UP: 
			return walkingUp;
		case DOWN: 
			return walkingDown;
		case STANDING_LEFT:
			return standingLeft;
		case STANDING_RIGHT:
			return standingRight;
		case STANDING_UP:
			return standingUp;
		case STANDING_DOWN:
			return standingDown;
		}
		
		return null;
	}
}
