package Schiffeversenken.network;

public class UnexpectedMessageException extends RuntimeException {
	final Message message;
	
	public UnexpectedMessageException(Message message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return String.format("The Message-Type %s was not expected!", message.getMessageType());
	}
}
