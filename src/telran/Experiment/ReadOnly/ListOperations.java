package telran.Experiment.ReadOnly;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class ListOperations extends Thread {
	
	int probUpdate;
	int nRuns;
	AtomicInteger count;
	Lock readLock;
	Lock updateLock;
	List<Integer> list;
	
	
	@Override
	public void run() {
		for (int i = 0; i < nRuns; i++) {
			ThreadLocalRandom tir = ThreadLocalRandom.current(); // Tread safe random number generator 
			if (tir.nextInt(0, 100) < probUpdate) {
				updateList();
			} else {
				readList();
			} 
		}
	}

	private void readList() {
		lock (readLock);
		try {
			int size = list.size();
			for (int i = 0; i < 100; i++) {
				list.get(size - 1);
			}
		} finally {
			readLock.unlock();
		}

			
		
	}

	private void lock (Lock lock) {
		while (!lock.tryLock()) {
			count.incrementAndGet();
		}
		
	}

	private void updateList() {
		lock (updateLock);
		try {
			list.remove(0);
			list.remove(0);
			list.remove(0);
			list.add(100);
			list.add(101);
			list.add(102);
		} finally {
			updateLock.unlock();
		}
			
		
	}




	public ListOperations(int probUpdate,List<Integer> list, int nRuns, Lock readLock, Lock updateLock
			, AtomicInteger count
			) {
		this.probUpdate = probUpdate;
		this.nRuns = nRuns;
		this.count = count;
		this.readLock = readLock;
		this.updateLock = updateLock;
		this.list = list;
	}
	

}
