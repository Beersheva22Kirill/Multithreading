package telran.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import telran.multithreading.consumer.Reciever;
import telran.multithreading.producer.Sender;
import telran.multithreading.utils.MyLinkedBlockingQueue;

public class SenderReciverAPP {

	private static final int N_MESSAGES = 21;
	private static final int N_RECIVERS = 9;
	

	public static void main(String[] args) throws InterruptedException {
		List<Reciever> list = new ArrayList<Reciever>();
		BlockingQueue<Message> messageBox = new MyLinkedBlockingQueue<>(1);
		Sender sender = new Sender(messageBox, N_MESSAGES);
		sender.start();
		
		for (int i = 0; i < N_RECIVERS; i++) {
			list.add(new Reciever(messageBox));
			list.get(i).start();
		}
		
		sender.join();
		for (Reciever reciever : list) {
			reciever.interrupt();
		}
		for (Reciever reciever : list) {
			reciever.join();
		}
		

	
		System.out.println("END APPLICATION");		

	}

}
