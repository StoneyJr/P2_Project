package btx.prog.project.validation;

public class PhoneValidator extends AbstractValidator<String> {
	private static String pattern = "((0|(\\+41 ))[0-9]{2} [0-9]{3} [0-9]{2} [0-9]{2})";

	public PhoneValidator() {
		super(pattern);

	}

	@Override
	protected String map(String string) throws InvalidInputException {

		return string;
	}
}
