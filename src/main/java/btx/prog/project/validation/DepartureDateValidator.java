package btx.prog.project.validation;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * This class validates the date input in cli for the departure date of a flight.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */
public class DepartureDateValidator extends LocalDateValidator {

	public DepartureDateValidator() {
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
			// departure date has to be in the future
			if (date.isBefore(LocalDate.now())) {
				throw new InvalidInputException(string, "Departure date cannot be in the past");
			}

			return date;
		} catch (NumberFormatException | DateTimeException ex) {
			throw new InvalidInputException(string, "String " + string + " cannot be parsed to yield date");
		}
	}

}
