package telran.multithreading.Truck;

public class Truck extends Thread {

	private int load;
	private static long elevator_1;
	private static long elevator_2;
	private int nRuns;
	private static final Object mutex = new Object();

	@Override
	public void run() {

		for (int i = 0; i < nRuns; i++) {
			loadElevator_1();
			loadElevator_2();
		}
	}

	private void loadElevator_2() {
		synchronized (mutex) {
			elevator_2 += load;
		}
	}

	private void loadElevator_1() {
		synchronized (mutex) {
			elevator_1 += load;
		}
	}

	public static long getElevator_2() {
		return elevator_2;
	}

	public static long getElevator_1() {
		return elevator_1;
	}

	public Truck(int load, int nRuns) {
		this.load = load;
		this.nRuns = nRuns;
	}

}
