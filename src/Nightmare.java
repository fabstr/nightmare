
public class Nightmare {
;
private Room start;
private Time timer;

public Nightmare(Room start, Time timer) {
	this.start = start;
	this.timer = timer;
	
}

public Room getStart() {
	return start;
}

public boolean timeLeft() {
	return timer.timeLeft();
}
}
