package btx.prog.project.validation;

import btx.prog.project.domain.FlightAttendant;

public class LanguageValidator extends EnumValidator<FlightAttendant.Language> {

	public LanguageValidator() {
		super(FlightAttendant.Language.values());
	}
}
