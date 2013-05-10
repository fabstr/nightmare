import org.newdawn.slick.SlickException;

public class HostileNPC extends NPC {
	public HostileNPC(int x, int y, String spriteSheetPath) throws SlickException {
		super(x, y, spriteSheetPath);
	}

	public void move(Room r, int deltaTime) {
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

	private void randomizeDirection() {
		int dir = r.nextInt() % 4;
		System.out.println(dir);
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
		
		updateAnimationDirection();
	}
}
