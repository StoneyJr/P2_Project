package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import btx.prog.project.domain.Person;
import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	public void testNewPerson() {
		Person person = new Person("Anthea", "Leung", Person.Gender.FEMALE, LocalDate.of(1900, 12, 20));

		assertEquals("Anthea", person.getFirstName());
		assertEquals("Leung", person.getLastName());
		assertEquals(Person.Gender.FEMALE, person.getGender());
		assertEquals(LocalDate.of(1900, 12, 20), person.getBirthdate());
	}

}