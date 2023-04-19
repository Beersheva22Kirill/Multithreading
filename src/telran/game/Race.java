package telran.game;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Race {

	AtomicInteger winner = new AtomicInteger(-1);
	ArrayList<Runner> runners = new ArrayList<>();
	int distanceRace;
	Instant startRace;
	List<String> winners = new LinkedList<>();

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
		for (Runner runner : runners) {
			runner.join();
		}
	}

	public int getWinner() {
		return winner.get();
	}

	public void setWinner(Runner runner) {
		this.winner.compareAndSet(-1, runner.name);
		synchronized (winners) {
			addWinner(runner);
		}
	}

	private void addWinner(Runner runner) {
		winners.add(" Runner: " + runner.name + " Time: " + runner.getTime());
	}

	public List<String> getWinners() {
		return winners;
	}

	public void setWinners(List<String> winners) {
		this.winners = winners;
	}

}
