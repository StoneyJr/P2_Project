package btx.prog.project.domain;

import java.time.LocalDate;
import java.util.UUID;
/**
 * This class contains a person. It is the super-class for employee and for passenger. Every person has an individual
 * random Id.
 *
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @ version 1.0
 */
public class Person {

	protected class DateBuild {
		private int year;
		private int month;
		private int day;

		protected DateBuild(int dayOfMonth, int monthValue, int year2) {
			this.day = dayOfMonth;
			this.month = monthValue;
			this.year = year2;
		}

		@Override
		public String toString() {
			return day + "-" + month + "-" + year;
		}

		protected int getYear() {
			return year;
		}

		protected int getMonth() {
			return month;
		}

		protected int getDay() {
			return day;
		}
	}

	public enum Gender {
		MALE, FEMALE, OTHER;

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	private UUID personId;
	private String firstName;
	private String lastName;
	private Gender gender;
	private DateBuild birthdate;

	/**
	 * This empty constructor gives a random created ID to a person.
	 */
	public Person() {
		personId = UUID.randomUUID();
	}

	/**
	 * This is the constructor, which is used for the classes: Employee and Passenger. It needs:
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param birthdate
	 */
	public Person(String firstName, String lastName, Gender gender, LocalDate birthdate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthdate = new DateBuild(birthdate.getDayOfMonth(), birthdate.getMonthValue(), birthdate.getYear());
		personId = UUID.randomUUID();
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public Gender getGender() {
		return this.gender;
	}

	public LocalDate getBirthdate() {
		return LocalDate.of(this.birthdate.getYear(), this.birthdate.getMonth(), this.birthdate.getDay());
	}

	public String getShortPersonId() {
		return this.personId.toString().substring(0, 8) + "...";
	}

	public UUID getFullPersonId() {
		return this.personId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = new DateBuild(birthdate.getDayOfMonth(), birthdate.getMonthValue(), birthdate.getYear());
	}

	@Override
	public String toString() {
		return this.getFirstName().charAt(0) + ". " + this.getLastName() + ", " + this.getGender() + " , "
				+ this.getBirthdate();

	}

}
