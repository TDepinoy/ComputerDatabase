package exceptions;

@SuppressWarnings("serial")
public class DataAccessException extends Exception {

	public DataAccessException () {}
	
	public DataAccessException (String message) {
		super(message);
	}
	
}
