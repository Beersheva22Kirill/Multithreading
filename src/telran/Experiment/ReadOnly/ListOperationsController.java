package telran.Experiment.ReadOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class ListOperationsController {

	private static final int N_NUMBERS = 100000;
	private static final int N_THREAD = 100;
	private static final int UPDATE_PROB = 0;
	private static final int N_RUNS = 1000;
	

	public static void main(String[] args) {
		Integer[] numbers = new Integer[N_NUMBERS];
		Arrays.fill(numbers, 100);
		List<Integer> list = new LinkedList<>(Arrays.asList(numbers));
		ListOperations[] operations = new ListOperations[N_THREAD];
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		Lock readLock = lock.readLock();
		Lock updateLock = lock.writeLock();
		AtomicInteger count = new AtomicInteger(0);
		IntStream.range(0, N_THREAD).forEach(i -> {
			operations[i] = new ListOperations(UPDATE_PROB, list, N_RUNS, readLock, updateLock, count);
			operations[i].start();
		});
		Arrays.stream(operations).forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println("total operation of iterations for waiting lock " + count);

	}

}
