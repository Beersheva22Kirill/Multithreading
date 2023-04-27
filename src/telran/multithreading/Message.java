package telran.multithreading;

public class Message {
	private String message;
	private Long numberMessage;
	
	public Message(String message, Long numberMessage) {
		this.message = message;
		this.numberMessage = numberMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getNumberMessage() {
		return numberMessage;
	}

	public void setNumberMessage(Long numberMessage) {
		this.numberMessage = numberMessage;
	}
	

}
