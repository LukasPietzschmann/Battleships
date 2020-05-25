package network;

public class UnknownMessageException extends Exception {
	private String message;
	
	public UnknownMessageException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return String.format("'%s' is not a valid Message!", message);
	}
}
