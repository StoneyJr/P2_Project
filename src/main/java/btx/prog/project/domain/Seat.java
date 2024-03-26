package btx.prog.project.domain;
/**
 * This is the seat in an airplane.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */
public class Seat {

	private int seatNr;
	private boolean booked;

	/**
	 * This is the constructor, it needs a seat number.
	 * @param seatNr
	 */
	public Seat(int seatNr) {
		this.seatNr = seatNr;
		this.booked = false;
	}

	public int getSeatNr() {
		return this.seatNr;
	}

	public boolean isBooked() {
		return this.booked;

	}

	public void setBooked(boolean bool) {
		this.booked = bool;
	}

	@Override
	public String toString() {
		return "( "+ this.seatNr +" )";
	}
}
