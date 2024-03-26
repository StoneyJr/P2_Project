package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import btx.prog.project.domain.*;
import org.junit.jupiter.api.Test;

class FlightTest {

	@Test
	void getTimeToStart() {

		Flight flight = new Flight(new Airplane("Boeing", Airport.AIRPORTS.get(1), 10), Airport.AIRPORTS.get(0),
				Airport.AIRPORTS.get(1), LocalDateTime.of(2022, 10, 20, 12, 05));

		assertEquals(flight.getTimeToStart(), LocalDateTime.of(2022, 10, 20, 12, 05));
	}

	@Test
	void getBookingList() {
		Airplane airplane = new Airplane("Boeing", Airport.AIRPORTS.get(1), 10);
		Flight flight = new Flight(airplane, Airport.AIRPORTS.get(0), Airport.AIRPORTS.get(1),
				LocalDateTime.of(2022, 10, 20, 12, 05));

		assertTrue(flight.getBookingList().size() == 0);

		Passenger passenger = new Passenger("Jorma", "Steiner", Person.Gender.MALE, LocalDate.of(1997, 8, 12),
				"078 608 26 14", "jorma.steiner@gmail.com");
		Booking booking = new Booking(passenger, airplane.getSeat(1));

		flight.addBooking(booking);

		assertTrue(flight.getBookingList().size() == 1);
		assertEquals(booking, flight.getBookingList().get(0));

	}

	@Test
	void getCrew() {
		Airplane airplane = new Airplane("Boeing", Airport.AIRPORTS.get(1), 10);
		Flight flight = new Flight(airplane, Airport.AIRPORTS.get(0), Airport.AIRPORTS.get(1),
				LocalDateTime.of(2022, 10, 20, 12, 05));

		assertTrue(flight.getCrew().size() == 0);

		Pilot pilot1 = new Pilot("Chime", "Sigrist", Person.Gender.MALE, LocalDate.of(1992, 7, 22),
				LocalDate.of(2000, 7, 22), 95999.90, Pilot.Rank.CAPTAIN);
		FlightAttendant fa1 = new FlightAttendant("Selina", "Blabla", Person.Gender.FEMALE, LocalDate.of(1992, 7, 22),
				LocalDate.of(2000, 7, 22), 84021);
		flight.addCrewMember(pilot1);
		flight.addCrewMember(fa1);

		assertTrue(flight.getCrew().size() == 2);
		assertTrue(flight.getCrew().contains(pilot1));
		assertTrue(flight.getCrew().contains(fa1));

	}

	@Test
	void getAirplane() {
		Airplane airplane = new Airplane("Boeing", Airport.AIRPORTS.get(1), 10);
		Flight flight = new Flight(airplane, Airport.AIRPORTS.get(0), Airport.AIRPORTS.get(1),
				LocalDateTime.of(2022, 10, 20, 12, 05));

		assertEquals(airplane, flight.getAirplane());
	}

	@Test
	void getDestinationOfAirplane() {
		Airplane airplane = new Airplane("Boeing", Airport.AIRPORTS.get(1), 10);
		Flight flight = new Flight(airplane, Airport.AIRPORTS.get(0), Airport.AIRPORTS.get(1),
				LocalDateTime.of(2022, 10, 20, 12, 05));

		assertEquals(Airport.AIRPORTS.get(0), flight.getDestinationOfAirplane());
	}

	@Test
	void getStartingPointOfAirplane() {
		Airplane airplane = new Airplane("Boeing", Airport.AIRPORTS.get(1), 10);
		Flight flight = new Flight(airplane, Airport.AIRPORTS.get(0), Airport.AIRPORTS.get(1),
				LocalDateTime.of(2022, 10, 20, 12, 05));

		assertEquals(Airport.AIRPORTS.get(1), flight.getStartingPointOfAirplane());
	}

	@Test
	void addBooking() {
		Airplane airplane = new Airplane("Boeing", Airport.AIRPORTS.get(1), 10);
		Flight flight = new Flight(airplane, Airport.AIRPORTS.get(0), Airport.AIRPORTS.get(1),
				LocalDateTime.of(2022, 10, 20, 12, 05));

		Passenger passenger = new Passenger("Jorma", "Steiner", Person.Gender.MALE, LocalDate.of(1997, 8, 12),
				"078 608 26 14", "jorma.steiner@gmail.com");
		Booking booking = new Booking(passenger, airplane.getSeat(1));

		flight.addBooking(booking);

		assertTrue(flight.getBookingList().size() == 1);
		assertEquals(booking, flight.getBookingList().get(0));

	}

	@Test
	void getAirplaneId() {
		Airplane airplane = new Airplane("Boeing", Airport.AIRPORTS.get(1), 10);
		Flight flight = new Flight(airplane, Airport.AIRPORTS.get(0), Airport.AIRPORTS.get(1),
				LocalDateTime.of(2022, 10, 20, 12, 05));

		assertEquals(airplane, flight.getAirplane());
		assertEquals(airplane.getAirplaneID(), flight.getAirplaneId());
	}

}