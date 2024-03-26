package btx.prog.project.domain;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Instances of this class represents a booking on a flight. It contains a bookingId and a passenger.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */

public class Booking {

    private int bookingID;
    private Passenger passenger;

    private transient Seat seat;
    private int seatNr;

    private static Set<Integer> usedIDs = new HashSet<>();

    /**
     * This constructor generates a booking with an instance of a passenger and seat
     * from airplane. A bookingId is randomly generated between 1000 and 8999
     * bookingIds bigger than 8999 throw out an exception
     *
     * @param passenger
     * @param seat
     */

    public Booking(Passenger passenger, Seat seat) {
        this.passenger = passenger;
        this.seat = seat;
        this.seatNr = seat.getSeatNr();
        this.bookingID = this.generateID();
    }

    public int getBookingID() {
        return this.bookingID;
    }

    public Passenger getPassenger() {
        return this.passenger;
    }

    public Seat getSeat() {
        return this.seat;
    }

    public int getSeatNr() {
        return this.seatNr;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
        this.seatNr = seat.getSeatNr();
    }

    // generates random unused ID for BookingID
    private int generateID() {
        boolean ok = false;
        int id = -1;


        do {
            id = new Random().nextInt(8999) + 1000;
            if (!Booking.usedIDs.contains(id)) { // checks if ID is already in usedIDs, if false do if clause
                Booking.usedIDs.add(id);
                ok = true;
            }
        } while (!ok);
        return id;
    }

    @Override
    public String toString() {
        return "Booking {" + passenger + ", assigned seat= " + seat + " bookingId = " + bookingID + " }";
    }
}
