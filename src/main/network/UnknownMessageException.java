package network;

/**
 * Diese Ausnahme wird ausgelöst, falls die Art der Nachricht unbekannt ist.
 */
public class UnknownMessageException extends RuntimeException {
	private final String message;
	
	public UnknownMessageException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return String.format("'%s' is not a valid Message!", message);
	}
}
