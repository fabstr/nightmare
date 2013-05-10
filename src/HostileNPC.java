import org.newdawn.slick.SlickException;

public class HostileNPC extends NPC {
	private TimingLock tl;
	
	public HostileNPC(int x, int y, String spriteSheetPath) throws SlickException {
		super(x, y, spriteSheetPath);
		
		// we don't need to lock, it is locked in randomizeDirection
		tl = new TimingLock(700);
		
		randomizeDirection();
	}

	public void move(Room r, int deltaTime) {
		if (tl.isLocked() == false) {
			randomizeDirection();
		}
		
		switch (getDirection()) {
		case LEFT:
			if (moveX(-1, r, false, deltaTime) == -1) {
				randomizeDirection();
			}
			break;
		case RIGHT:
			if (moveX(1, r, true, deltaTime) == -1) {
				randomizeDirection();
			}
			break;
		case UP:
			if (moveY(-1, r, false, deltaTime) == -1) {
				randomizeDirection();
			}
			break;
		case DOWN:
			if (moveY(1, r, true, deltaTime) == -1) {
				randomizeDirection();
			}
			break;
		default:
			setDirection(AnimationManager.directions.LEFT);
		}
	}

	/**
	 * Randomize the direction and lock the timinglock again
	 */
	private void randomizeDirection() {
		int dir = r.nextInt() % 4;
		switch (dir) {
		case 0:
			setDirection(AnimationManager.directions.LEFT);
			break;
		case 1:
			setDirection(AnimationManager.directions.RIGHT);
			break;
		case 2:
			setDirection(AnimationManager.directions.UP);
			break;
		case 3:
			setDirection(AnimationManager.directions.DOWN);
			break;
		}

		tl.lock();
		updateAnimationDirection();
	}
}
