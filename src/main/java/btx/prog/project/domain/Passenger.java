package btx.prog.project.domain;

import java.time.LocalDate;

/**
 * This class contains our flight passengers. It inherits everything from person. A passenger is always created when
 * registering a new booking.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */

public class Passenger extends Person {

	private String phoneNr;
	private String email;

	/**
	 *This contructor inherits  everything from person except a phone number and an email address.
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param birthdate
	 * @param phoneNr
	 * @param email
	 */
	public Passenger(String firstName, String lastName, Gender gender, LocalDate birthdate, String phoneNr,
			String email) {
		super(firstName, lastName, gender, birthdate);
		this.phoneNr = phoneNr;
		this.email = email;
	}

	public String getPhoneNr() {
		return this.phoneNr;
	}

	public String getEmail() {
		return this.email;
	}

	@Override
	public String toString() {
		return "passenger ( " + super.toString() + " )";
	}

}
