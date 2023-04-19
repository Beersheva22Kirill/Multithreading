package telran.multithreading.TruckAtomicOperation;

import java.util.concurrent.atomic.AtomicLong;

public class TruckAtomic extends Thread {

	private int load;
	private static AtomicLong elevator_1 = new AtomicLong(0);
	private static AtomicLong elevator_2 = new AtomicLong(0);
	private int nRuns;

	@Override
	public void run() {

		for (int i = 0; i < nRuns; i++) {
			loadElevator_1();
			loadElevator_2();
		}
	}

	private void loadElevator_2() {
			elevator_2.addAndGet(load);
	}

	private void loadElevator_1() {
			elevator_1.addAndGet(load);
	}

	public static long getElevator_2() {
		return elevator_2.get();
	}

	public static long getElevator_1() {
		return elevator_1.get();
	}

	public TruckAtomic(int load, int nRuns) {
		this.load = load;
		this.nRuns = nRuns;
	}

}
