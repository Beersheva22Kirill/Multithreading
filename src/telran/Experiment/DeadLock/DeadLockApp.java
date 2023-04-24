package telran.Experiment.DeadLock;

public class DeadLockApp {

	public static void main(String[] args) {

		ThreadForDeadLock1 thread1 = new ThreadForDeadLock1();
		ThreadForDeadLock2 thread2 = new ThreadForDeadLock2();
		thread1.start();
		thread2.start();
	}

}
