package telran.multithreading;

import telran.multithreading.consumer.Reciever;
import telran.multithreading.producer.Sender;

public class SenderReciverAPP {

	private static final int N_MESSAGES = 21;
	private static final int N_RECIVERS = 9;

	public static void main(String[] args) throws InterruptedException {
		MessageBox messageBox = new MessageBox();
		Sender sender = new Sender(messageBox, N_MESSAGES);
		sender.start();
		
		for (int i = 0; i < N_RECIVERS; i++) {
			new Reciever(messageBox).start();
		}
			Thread.sleep(100);
			sender.join();
			System.out.println("END APPLICATION");		

	}

}
