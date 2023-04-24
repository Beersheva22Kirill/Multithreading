package telran.game;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Race {

	Integer winner = -1;
	ArrayList<Runner> runners = new ArrayList<>();
	int distanceRace;
	Instant startRace;
	List<String> tableRunners = new LinkedList<>();
	public Lock lock = new ReentrantLock(true);

	public void createRunners(int countRanners, int distanceRace, Race race) {
		for (int i = 0; i < countRanners; i++) {
			runners.add(new Runner(i, distanceRace, race));
		}
		this.distanceRace = distanceRace;
	}

	public void startRace() throws InterruptedException {
		startRace = Instant.now();
		for (Runner runner : runners) {
			runner.start();
		}
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(Runner runner) {
		runner.setTime(ChronoUnit.MILLIS.between(startRace, Instant.now()));
		if (winner == -1) {winner = runner.name;}
		tableRunners.add(" Runner: " + runner.name + " Time: " + runner.getTime());
	}

	public List<String> getWinners() {
		return tableRunners;
	}

	public void setWinners(List<String> winners) {
		this.tableRunners = winners;
	}

}
