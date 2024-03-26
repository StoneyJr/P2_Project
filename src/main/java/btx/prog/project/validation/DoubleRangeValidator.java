package btx.prog.project.validation;

public class DoubleRangeValidator extends AbstractValidator<Double> {
	private static final String pattern = "[+-]?[0-9]+([\\.|,][0-9]+)?";
	private double min;
	private double max;

	public DoubleRangeValidator(double min, double max) {
		super(pattern);
		this.min = min;
		this.max = max;
	}

	@Override
	protected Double map(String string) throws InvalidInputException {
		string = string.replace(',', '.');
		try {
			return Double.valueOf(string);
		} catch (NumberFormatException ex) {
			throw new InvalidInputException(string, "String " + string + " cannot be parsed to yield double");
		}
	}

	@Override
	protected void checkSemantics(Double value) throws SubtypeMismatchException {
		if (value < this.min || value > this.max) {
			throw new SubtypeMismatchException(String.valueOf(value));
		}
	}
}