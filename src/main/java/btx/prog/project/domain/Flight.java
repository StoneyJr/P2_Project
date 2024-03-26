package btx.prog.project.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Instances of this class represents a flight. It saves a set of the crew (1 pilot and 2 flight attendants),
 * booking list for that flight, an airplane, an airplaneId, the destination and the starting
 * point with a track off time of an airplane.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */

public class Flight {

	protected class TimeBuild {
		private int year;
		private int month;
		private int day;
		private int hour;
		private int minutes;

		protected TimeBuild(int dayOfMonth, int monthValue, int year2, int hour, int minutes) {
			this.day = dayOfMonth;
			this.month = monthValue;
			this.year = year2;
			this.hour = hour;
			this.minutes = minutes;
		}

		@Override
		public String toString() {
			return day + "-" + month + "-" + year + " " + hour + ":" + minutes;
		}
	}

	private UUID id;
	private List<Booking> bookingList;
	private transient Set<Employee> crew;
	private Set<UUID> crewIds;
	private transient Airplane airplane;
	private String airplaneId;
	private Airport destinationOfAirplane;
	private Airport startingPointOfAirplane;
	private TimeBuild timeOfStart;

	/**
	 *This is the constructor for a flight.
	 * @param airplane
	 * @param destinationOfAirplane
	 * @param startingPointOfAirplane
	 * @param timeOfStart
	 */
	public Flight(Airplane airplane, Airport destinationOfAirplane, Airport startingPointOfAirplane,
			LocalDateTime timeOfStart) {

		this.airplane = airplane;
		this.airplaneId = airplane.getAirplaneID();
		this.destinationOfAirplane = destinationOfAirplane;
		this.startingPointOfAirplane = startingPointOfAirplane;
		this.timeOfStart = new TimeBuild(timeOfStart.getDayOfMonth(), timeOfStart.getMonthValue(),
				timeOfStart.getYear(), timeOfStart.getHour(), timeOfStart.getMinute());
		this.bookingList = new ArrayList<>();
		this.crew = new HashSet<>();
		this.crewIds = new HashSet<>();
		this.id = UUID.randomUUID();
	}

	public LocalDateTime getTimeToStart() {
		return LocalDateTime.of(this.timeOfStart.year, this.timeOfStart.month, this.timeOfStart.day,
				this.timeOfStart.hour, this.timeOfStart.minutes);
	}

	public void settimeToStart(LocalDateTime timeToStart) {
		this.timeOfStart = new TimeBuild(timeToStart.getDayOfMonth(), timeToStart.getMonthValue(),
				timeToStart.getYear(), timeToStart.getHour(), timeToStart.getMinute());
	}

	public List<Booking> getBookingList() {
		return Collections.unmodifiableList(this.bookingList);
	}

	public Set<Employee> getCrew() {
		return Collections.unmodifiableSet(this.crew);
	}

	public Airplane getAirplane() {
		return this.airplane;
	}

	public void setAirplane(Airplane plane) {
		this.airplane = plane;
	}

	public Airport getDestinationOfAirplane() {
		return this.destinationOfAirplane;
	}

	public Airport getStartingPointOfAirplane() {
		return this.startingPointOfAirplane;
	}

	public void addBooking(Booking booking) {
		this.bookingList.add(booking);
	}

	public void addCrewMember(Employee employee) {
		// falls per json erste Flüge eingefügt werden muss Liste initialisiert werden
		if (this.crew == null) {
			this.crew = new HashSet<>();
		}
		if (this.crewIds == null) {
			this.crewIds = new HashSet<>();
		}

		this.crew.add(employee);
		this.crewIds.add(employee.getFullPersonId());
	}

	public UUID getId() {
		return this.id;
	}

	public String getAirplaneId() {
		return airplaneId;
	}

	public Set<UUID> getCrewIds() {
		return Collections.unmodifiableSet(this.crewIds);
	}

	@Override
	public String toString() {
		return "Flight from " + this.startingPointOfAirplane.toString() + " to " + this.destinationOfAirplane.toString()
				+ " with " + this.airplane.getAirplaneID();
	}

}
