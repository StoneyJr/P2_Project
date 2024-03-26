package btx.prog.project.validation;

public class BooleanValidator extends AbstractValidator<Boolean> {
    private static final String pattern = "(?i)(^([yntf]|(true)|(false)|(ok)|(yes)|(no)?)$)";

    public BooleanValidator() {
        super(pattern);
    }

    @Override
    protected Boolean map(String string) throws InvalidInputException {
        return switch (string) {
            case "y", "t", "yes", "true", "ok" -> true;
            case "n", "f", "no", "false" -> false;
            default -> throw new InvalidInputException(string, "String " + string + " cannot be parsed to yield boolean");
        };
    }
}
