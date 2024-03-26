package btx.prog.project.validation;


public class EmailValidator extends AbstractValidator<String> {
    private static String pattern = "^[a-zA-Z0-9_%+-]+\\.[a-zA-Z0-9_%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public EmailValidator() {
        super(pattern);

    }


    @Override
    protected String map(String string) throws InvalidInputException {

        return string;
    }
}
