package telran.multithreading;

public class Printer extends Thread{
	private String string;
	private int nRuns;
	
	@Override
	public void run() {
		for (int i = 0; i < nRuns; i++) {
			System.out.println(string);
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Printer(String string, int nRuns) {
		this.string = string;
		this.nRuns = nRuns;
	}

}
