package service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import btx.prog.project.service.FlightService;
import org.junit.jupiter.api.Test;

import btx.prog.project.domain.Airplane;
import btx.prog.project.domain.Airport;
import btx.prog.project.domain.Booking;
import btx.prog.project.domain.Flight;
import btx.prog.project.domain.FlightAttendant;
import btx.prog.project.domain.CollectionsManagerLittleFly;
import btx.prog.project.domain.Passenger;
import btx.prog.project.domain.Person;
import btx.prog.project.domain.Pilot;
import btx.prog.project.domain.Seat;

class FlightServiceTest {

	@Test
	void registerFlight() {
		FlightService fs = new FlightService("x", "y", "z", "w"); // lädt direkt erstes Set der Daten siehe
																	// Flightservice.init()

		Pilot pilot1 = new Pilot("Chime", "Sigrist", Person.Gender.MALE, LocalDate.of(1992, 7, 22),
				LocalDate.of(2000, 7, 22), 95999.90, Pilot.Rank.CAPTAIN);
		FlightAttendant fa1 = new FlightAttendant("Selina", "Blabla", Person.Gender.FEMALE, LocalDate.of(1992, 7, 22),
				LocalDate.of(2000, 7, 22), 84021);
		CollectionsManagerLittleFly.getCollectionsManager().addEmployee(pilot1);// registerFLight nimmt Employees aus LittleFly
		CollectionsManagerLittleFly.getCollectionsManager().addEmployee(fa1);

		List<UUID> crew = new ArrayList<>();
		crew.add(pilot1.getFullPersonId());
		crew.add(fa1.getFullPersonId());

		Airplane airplane = new Airplane("Boeing", Airport.AIRPORTS.get(1), 10);

		int preCount = fs.getFlights().size();

		Flight flight = fs.registerFlight(crew, airplane, Airport.AIRPORTS.get(0), Airport.AIRPORTS.get(1),
				LocalDateTime.of(2022, 10, 20, 12, 05));

		assertTrue(fs.getFlights().size() == preCount + 1);
		assertTrue(fs.getFlights().contains(flight));

	}

	@Test
	void registerBooking() {
		FlightService fs = new FlightService("x", "y", "z", "w"); // lädt direkt erstes Set der Daten siehe
		// Flightservice.init()

		List<Flight> flights = CollectionsManagerLittleFly.getCollectionsManager().getFlightSet().stream().toList();
		Flight flight = flights.get(0);

		Seat seat = flight.getAirplane().getSeat(1);

		Passenger passenger = new Passenger("Jorma", "Steiner", Person.Gender.MALE, LocalDate.of(1997, 8, 12),
				"078 608 26 14", "jorma.steiner@gmail.com");

		int bookingCount = flight.getBookingList().size();

		Booking booking = fs.registerBooking(flight, seat, passenger);

		assertTrue(flight.getBookingList().size() == bookingCount + 1);
		assertTrue(flight.getBookingList().contains(booking));
		assertTrue(!(flight.getAirplane().getAllFreeSeats().containsValue(seat)));
		assertTrue(flight.getAirplane().getSeat(seat.getSeatNr()).isBooked());

	}

	@Test
	void registerPilot() {
		FlightService fs = new FlightService("x", "y", "z", "w"); // lädt direkt erstes Set der Daten siehe
		// Flightservice.init()

		Pilot pilot = fs.registerPilot("fn", "ln", Person.Gender.OTHER, LocalDate.of(2022, 12, 12),
				LocalDate.of(2000, 1, 1), 10, Pilot.Rank.CAPTAIN);

		assertTrue(fs.getPilots().contains(pilot));
	}

	@Test
	void registerFlightAttendant() {
		FlightService fs = new FlightService("x", "y", "z", "w"); // lädt direkt erstes Set der Daten siehe
		// Flightservice.init()

		FlightAttendant fa = fs.registerFlightAttendant("fn", "ls", Person.Gender.FEMALE, LocalDate.of(1900, 2, 2),
				LocalDate.of(1900, 4, 4), 200, Set.of(FlightAttendant.Language.CHINESE));

		assertTrue(fs.getFlightAttendants().contains(fa));
	}

}