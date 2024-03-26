package btx.prog.project.validation;

public class SubtypeMismatchException extends InvalidInputException {
	private static final long serialVersionUID = 1L;
	private String string;

	public SubtypeMismatchException(String string) {
		super(string, "String " + string + " does not meet subtype requirement");
	}

	@Override
	public String toString() {
		return "SubtypeMismatchException [string=" + this.string + "]";
	}
}
