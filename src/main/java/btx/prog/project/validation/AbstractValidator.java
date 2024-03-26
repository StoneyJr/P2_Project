package btx.prog.project.validation;

public abstract class AbstractValidator<V> implements Validator<V> {
	private String pattern;

	protected AbstractValidator(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public final V validate(String string) throws InvalidInputException {
		string = string.trim();
		checkSyntax(string);
		V res = map(string);
		checkSemantics(res);
		return res;
	}

	private final void checkSyntax(String string) throws RegexMismatchException {
		if (!string.matches(pattern)) {
			throw new RegexMismatchException(string, pattern);
		}
	}

	protected abstract V map(String string) throws InvalidInputException;

	protected void checkSemantics(V string) throws SubtypeMismatchException {
	}
}
