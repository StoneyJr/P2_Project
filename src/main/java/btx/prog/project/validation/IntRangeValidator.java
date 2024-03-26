package btx.prog.project.validation;

public class IntRangeValidator extends AbstractValidator<Integer> {
	private static final String pattern = "[+-]?[0-9]+";
	private int min;
	private int max;

	public IntRangeValidator(int min, int max) {
		super(pattern);
		this.min = min;
		this.max = max;
	}

	@Override
	protected Integer map(String string) throws InvalidInputException {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			throw new InvalidInputException(string, "String " + string + " cannot be parsed to yield integer");
		}
	}

	@Override
	protected void checkSemantics(Integer value) throws SubtypeMismatchException {
		if (value < this.min || value > this.max) {
			throw new SubtypeMismatchException(String.valueOf(value));
		}
	}
}