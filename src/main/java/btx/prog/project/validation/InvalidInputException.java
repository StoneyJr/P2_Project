package btx.prog.project.validation;

public class InvalidInputException extends Exception {
	private static final long serialVersionUID = 1L;

	protected String string;

	protected InvalidInputException(String string, String message) {
		super(message);
		this.string = string;
	}

}
