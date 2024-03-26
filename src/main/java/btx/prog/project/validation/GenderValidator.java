package btx.prog.project.validation;

import btx.prog.project.domain.Person;

public class GenderValidator extends EnumValidator<Person.Gender> {

	public GenderValidator() {
		super(Person.Gender.values());
	}
}
