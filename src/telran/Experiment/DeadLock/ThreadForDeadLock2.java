package telran.Experiment.DeadLock;

import java.util.ArrayList;

public class ThreadForDeadLock2 extends Thread {

	@Override
	public void run() {
		synchronized (ShareResources.resource2) {
			System.out.println("Thread 1 block 1");
			synchronized (ShareResources.resource1) {
				System.out.println("Thread 1 block 2");
			}
		}
	}

}
