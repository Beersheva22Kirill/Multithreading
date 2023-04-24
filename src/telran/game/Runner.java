package telran.game;

public class Runner extends Thread {
	
	private static final int MIN_VALUE = 1;
	private static final int MAX_VALUE = 10;
	public int name;
	private int distance;
	private long time;
	Race race;
	
	
	@Override
	public void run() {
		for (int i = 0; i < distance; i++) {
			try {
				sleep(getRandom());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}					
		}
		
		race.lock.lock(); // good solution
		try {
			race.setWinner(this);
		} finally {
			race.lock.unlock();
		}
		
//		synchronized (race.lock) { \\ object "race.lock" in block "synchronized" works like a normal object. No blocking queue...
//			race.setWinner(this);
//		}
		
	}

	public Runner(int name, int distance, Race race) {
		this.race = race;
		this.name = name;
		this.distance = distance;
	}
	
	private Integer getRandom() {
		return (int) (MIN_VALUE + Math.random() * (MAX_VALUE - MIN_VALUE + 1));
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	
	
	


}
