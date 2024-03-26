package btx.prog.project.validation;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * This class validates the user input for hiring date of employees in the cli.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */

public class HiredDateValidator extends LocalDateValidator {

    public HiredDateValidator() {
        super();
    }

    @Override
    protected LocalDate map(String string) throws InvalidInputException {
        try {
            String[] parts = string.split("-");
            int day = Integer.valueOf(parts[0]);
            int month = Integer.valueOf(parts[1]);
            int year = Integer.valueOf(parts[2]);
            LocalDate date = LocalDate.of(year, month, day);
            // hired date cant be in the past
            if (date.isAfter(LocalDate.now())) {
                throw new InvalidInputException(string, "Hired date cannot be in the future");
            }

            return date;
        } catch (NumberFormatException | DateTimeException ex) {
            throw new InvalidInputException(string, "String " + string + " cannot be parsed to yield date");
        }
    }
}
