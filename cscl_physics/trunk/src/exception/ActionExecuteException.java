package exception;

public class ActionExecuteException extends Exception {
	
	public ActionExecuteException(String message) {
		super(message);
	}

	public ActionExecuteException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
