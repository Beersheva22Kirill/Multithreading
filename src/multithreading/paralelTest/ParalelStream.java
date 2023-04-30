package multithreading.paralelTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ParalelStream {
	private static final long N_NUMBERS = 10_000_000;
	
	Integer[] array = {100, -10, 20, -90, 50, 40, 60, 100,-10};
	int[] bigArray = ThreadLocalRandom.current().ints(N_NUMBERS).toArray();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@Disabled
	void test() {
		Integer[] expected = {-90, -10, -10, 20, 40, 50, 60, 100, 100};
		Integer[] actual = new Integer[expected.length];
		HashSet<String> threadNames = new HashSet<>();
		int[] index = {0};
		
		Arrays.stream(array).parallel().sorted((a,b) -> {
			return a - b;
			}).forEachOrdered((num)-> {
				threadNames.add(Thread.currentThread().getName());
				actual[index[0]++] = num;
			});
		System.out.println(threadNames);
		assertArrayEquals(expected, actual);
		}
	
	@Test
	void bigArrayTest() {

		HashSet<String> threadNames = new HashSet<>();
		
		Arrays.stream(bigArray).parallel().boxed().sorted((a,b) -> {
			threadNames.add(Thread.currentThread().getName());
			return Integer.compare(a, b);
			}).toArray();
		
		for (String string : threadNames) {
			System.out.println(string);
		}
		System.out.println("Count threads: " + threadNames.size());
		}
}
