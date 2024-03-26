package btx.prog.project.validation;

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateValidator extends AbstractValidator<LocalDate> {
    // approximate form dd-mm-yyy, min year = 1900, max year = 2099
    // specific validator entry will be checked with map()
    protected static final String pattern = "(([0-9]{2})-([0-9]{2})-((19|20)[0-9]{2}))";

    public LocalDateValidator() {
        super(pattern);
    }

    @Override
    protected LocalDate map(String string) throws InvalidInputException {
        try {
            String[] parts = string.split("-");
            int day = Integer.valueOf(parts[0]);
            int month = Integer.valueOf(parts[1]);
            int year = Integer.valueOf(parts[2]);
            return LocalDate.of(year, month, day);
        } catch (NumberFormatException | DateTimeException ex) {
            throw new InvalidInputException(string, "String " + string + " cannot be parsed to yield date");
        }
    }
}