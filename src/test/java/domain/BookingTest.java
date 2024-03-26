package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import btx.prog.project.domain.*;
import org.junit.jupiter.api.Test;

class BookingTest {

	@Test
	void getPassenger() {
		Airplane airplane = new Airplane("Boeing", Airport.AIRPORTS.get(1), 10);

		Passenger passenger = new Passenger("Jorma", "Steiner", Person.Gender.MALE, LocalDate.of(1997, 8, 12),
				"078 608 26 14", "jorma.steiner@gmail.com");
		Booking booking = new Booking(passenger, airplane.getSeat(1));

		assertEquals(passenger, booking.getPassenger());

	}

	@Test
	void getSeat() {
		Airplane airplane = new Airplane("Boeing", Airport.AIRPORTS.get(1), 10);

		Booking booking = new Booking(new Passenger("Jorma", "Steiner", Person.Gender.MALE, LocalDate.of(1997, 8, 12),
				"078 608 26 14", "jorma.steiner@gmail.com"), airplane.getSeat(1));

		assertEquals(1, booking.getSeatNr());
		assertEquals(airplane.getSeat(1), booking.getSeat());
	}

	@Test
	void setSeat() {
		Airplane airplane = new Airplane("Boeing", Airport.AIRPORTS.get(1), 10);

		Booking booking = new Booking(new Passenger("Jorma", "Steiner", Person.Gender.MALE, LocalDate.of(1997, 8, 12),
				"078 608 26 14", "jorma.steiner@gmail.com"), airplane.getSeat(1));

		assertEquals(1, booking.getSeatNr());
		assertEquals(airplane.getSeat(1), booking.getSeat());

		booking.setSeat(airplane.getSeat(5));

		assertEquals(5, booking.getSeatNr());
		assertEquals(airplane.getSeat(5), booking.getSeat());

	}
}