package btx.prog.project.domain;

import java.time.LocalDate;

/**
 * Instances of this class represents an Employee. It inherits from person:
 * first name, last name, gender and birthdate
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */




public class Employee extends Person {

	private DateBuild hiredSince;
	private double salary;

	/**
	 * This is the constructor of Employee. It inherits from Person: first name, last name, gender and birthdate.
	 * hiredSince and salary are added.
	 *
	 *
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param birthdate
	 * @param hiredSince
	 * @param salary
	 */
	public Employee(String firstName, String lastName, Gender gender, LocalDate birthdate, LocalDate hiredSince,
			Double salary) {
		super(firstName, lastName, gender, birthdate);
		this.hiredSince = new DateBuild(hiredSince.getDayOfMonth(), hiredSince.getMonthValue(), hiredSince.getYear());
		this.salary = salary;
	}

	public LocalDate getHiredSince() {
		return LocalDate.of(this.hiredSince.getYear(), this.hiredSince.getMonth(), this.hiredSince.getDay());
	}

	public double getSalary() {
		return this.salary;
	}

	public void setHiredSince(LocalDate hiredSince) {
		this.hiredSince = new DateBuild(hiredSince.getDayOfMonth(), hiredSince.getMonthValue(), hiredSince.getYear());
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return " ( " + super.toString() + " )";
	}
}
