package btx.prog.project.domain;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Instances of this class represents a flight attendant. Besides inheriting from Employee it has an extra set filled
 * with languages. There are 6 languages to choose from.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */



public class FlightAttendant extends Employee {

	public enum Language {
		GERMAN, SPANISH, ENGLISH, FRENCH, ITALIAN, CHINESE
	}

	private Set<Language> languages = new HashSet<>();

	/**
	 * This constructor is the same as Employee.
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param birthdate
	 * @param hiredSince
	 * @param salary
	 */
	public FlightAttendant(String firstName, String lastName, Gender gender, LocalDate birthdate, LocalDate hiredSince,
			double salary) {
		super(firstName, lastName, gender, birthdate, hiredSince, salary);
	}

	public Set<Language> getLanguages() {
		return Collections.unmodifiableSet(this.languages);
	}

	public FlightAttendant addLanguage(Language language) {
		this.languages.add(language);
		return null;
	}

	@Override
	public String toString() {
		return "flight attendant ( " + super.toString() + " can speak " + languages + " )";
	}

}
