package telran.multithreading;

public class TimerTeseApp {

	public static void main(String[] args) throws InterruptedException {
		
		Timer timer = new Timer();
		timer.start();	
		
		//running imitation
		Thread.sleep(5000);
		timer.interrupt();
		Thread.sleep(5000);
		Thread mainThread = Thread.currentThread();
		mainThread.interrupt();
		mainThread.join();

	}

}
