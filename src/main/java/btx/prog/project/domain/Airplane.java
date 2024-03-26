

package btx.prog.project.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
/**
 * Instances of this class represent airplanes, which is used in the whole project. It gets most of its parameter
 * from plane, except the seats. Seats are assigned in this class.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */



public class Airplane {


	private String airplaneID;
	private String type;
	private Airport location;
	private Map<Integer, Seat> seats = new HashMap<>();

	private static Set<String> usedIDs = new HashSet<>();

	/**
	 * This constructor generates an airplane from a given typ of plane, an airport location and a seat count.
	 *
	 * @param type
	 * @param location
	 * @param seatCount
	 */

	public Airplane(String type, Airport location, int seatCount) {
		this.type = type;
		this.location = location;
		this.airplaneID = this.generateID();
		this.fillUpSeats(seatCount);
	}

	/**
	 * This constructor generates an airplane from a given typ of plane, an airport location and a seat count.
	 * Here an airplaneID is added with a prefix "LF" for Little Fly and a random int between 100 and 999
	 *
	 * @param airplaneID
	 * @param type
	 * @param location
	 * @param seatCount
	 */
	public Airplane(String airplaneID, String type, Airport location, int seatCount) {
		this.type = type;
		this.location = location;
		this.airplaneID = this.generateID();
		this.fillUpSeats(seatCount);
	}

	public Airport getLocation() {
		return this.location;
	}

	public void setLocation(Airport location) {
		this.location = location;
	}

	public String getAirplaneID() {
		return this.airplaneID;
	}

	public String getType() {
		return this.type;
	}

	public List<Seat> getAllSeats() {
		return Collections.unmodifiableList(seats.values().stream().toList());
	}

	public Object getSeatsCount() {
		return this.seats.size();
	}

	public Map<Integer, Seat> getAllFreeSeats() {

		// version 1
		Map<Integer, Seat> freeSeats = new HashMap<>();
		// puts each free Seat from seats into new Map freeSeats
		for (Seat seat : this.seats.values()) {
			if (!seat.isBooked()) {
				freeSeats.put(seat.getSeatNr(), seat);
			}
		}
		return Collections.unmodifiableMap(freeSeats);

	}

	public Seat getSeat(int seatNr) {
		return this.seats.get(seatNr);
	}

	private void addSeat(Seat seat) {
		this.seats.put(seat.getSeatNr(), seat);
	}

	// generates Seats as many as wished by parameter seatCount
	private void fillUpSeats(int seatCount) {
		for (int i = 1; i <= seatCount; i++) {
			Seat newSeat = new Seat(i);
			this.addSeat(newSeat); // adds seat to map seat with seatnr as key
		}
	}

	private String generateID() {
		boolean ok = false;
		String id = "default";

		do {
			String string = "LF";
			int integer = new Random().nextInt(899) + 100;// generates random integer (100 -999)
			id = string + integer; // Concatenate capital Letter and integer to String ID
			if (!Airplane.usedIDs.contains(id)) { // checks if ID is already in usedIDs, if false do if clause
				Airplane.usedIDs.add(id);
				ok = true;
			}
		} while (!ok);
		return id;
	}

	@Override
	public String toString() {
		return "Airplane ( " + airplaneID + " | " + type + " | " + location + " )";
	}
}
