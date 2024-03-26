package btx.prog.project.validation;

public class RegexMismatchException extends InvalidInputException {
	private static final long serialVersionUID = 1L;
	private String regex;

	public RegexMismatchException(String string, String regex) {
		super(string, "String " + string + " does not meet regex specification " + regex);
		this.regex = regex;
	}

	@Override
	public String toString() {
		return "RegexMismatchException [string=" + this.string + ", regex=" + this.regex + "]";
	}
}
