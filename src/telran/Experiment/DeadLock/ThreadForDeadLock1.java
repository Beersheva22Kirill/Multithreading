package telran.Experiment.DeadLock;

import java.util.ArrayList;

public class ThreadForDeadLock1 extends Thread {
			
	@Override
	public void run() {
		synchronized (ShareResources.resource1) {
			System.out.println("Thread 2 block 1");
			synchronized (ShareResources.resource2) {
				System.out.println("Thread 2 block 2");
			}		
		}
	}
		


}
