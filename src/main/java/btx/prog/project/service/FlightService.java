package btx.prog.project.service;

import btx.prog.project.domain.*;
import btx.prog.project.domain.FlightAttendant.Language;
import btx.prog.project.domain.Person.Gender;
import btx.prog.project.domain.Pilot.Rank;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This is the class managing our airline company little fly. This class
 * modifies our lists in Little Fly. Additionally it has all the methods to
 * save/persist our data on to a Json-File.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */

public class FlightService {

    private String planesDocument;
    private String pilotsDocument;
    private String flightattendantsDocument;
    private String flightsDocument;

    public FlightService(String planesDocument, String pilotsDocument, String flightattendantsDocument,
                         String flightsDocument) {
        this.planesDocument = planesDocument;
        this.pilotsDocument = pilotsDocument;
        this.flightattendantsDocument = flightattendantsDocument;
        this.flightsDocument = flightsDocument;

        this.init();
    }

    private void init() {

        File planesFile = new File(this.planesDocument);
        File flightsFile = new File(this.flightsDocument);
        File pilotsFile = new File(this.pilotsDocument);
        File faFile = new File(this.flightattendantsDocument);

        if (planesFile.exists() && flightsFile.exists() && pilotsFile.exists() && faFile.exists()) {
            try {
                List<Airplane> planes = this.loadPlanes();
                List<Pilot> pilots = this.loadPilots();
                List<FlightAttendant> flightAttendants = this.loadFlightAttendants();
                List<Flight> flights = this.loadFlights();

                this.assignPlanes(planes);
                this.assignEmployees(pilots, flightAttendants);
                this.assignFlights(flights);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else { // keine Files gefunden mit Daten --> erster Satz Daten generieren

            Pilot pilot1 = new Pilot("Chime", "Sigrist", Person.Gender.MALE, LocalDate.of(1992, 7, 22),
                    LocalDate.of(2000, 7, 22), 95999.90, Pilot.Rank.CAPTAIN);
            Pilot pilot2 = new Pilot("Anthea", "Leung", Person.Gender.FEMALE, LocalDate.of(1994, 12, 3),
                    LocalDate.of(2000, 8, 1), 120000, Pilot.Rank.SENIORFIRSTOFFICER);
            FlightAttendant fa1 = new FlightAttendant("Selina", "Sterchi", Person.Gender.FEMALE,
                    LocalDate.of(1992, 7, 22), LocalDate.of(2000, 7, 22), 84021);
            fa1.addLanguage(FlightAttendant.Language.GERMAN);
            fa1.addLanguage(FlightAttendant.Language.ENGLISH);
            FlightAttendant fa2 = new FlightAttendant("Gerta", "Brügger", Person.Gender.OTHER,
                    LocalDate.of(1995, 12, 23), LocalDate.of(2011, 7, 22), 79999.50);
            fa2.addLanguage(FlightAttendant.Language.GERMAN);
            fa2.addLanguage(FlightAttendant.Language.CHINESE);
            fa2.addLanguage(FlightAttendant.Language.ENGLISH);

            CollectionsManagerLittleFly.getCollectionsManager().addEmployee(pilot1);
            CollectionsManagerLittleFly.getCollectionsManager().addEmployee(pilot2);
            CollectionsManagerLittleFly.getCollectionsManager().addEmployee(fa1);
            CollectionsManagerLittleFly.getCollectionsManager().addEmployee(fa2);

            Airplane ap1 = new Airplane("Boing 905", Airport.AIRPORTS.get(0), 20);
            Airplane ap2 = new Airplane("Gulfstream V", Airport.AIRPORTS.get(7), 12);

            CollectionsManagerLittleFly.getCollectionsManager().addAirplane(ap1);
            CollectionsManagerLittleFly.getCollectionsManager().addAirplane(ap2);

            Flight flight1 = new Flight(ap1, ap1.getLocation(), Airport.AIRPORTS.get(1), LocalDateTime.now());
            Flight flight2 = new Flight(ap2, ap2.getLocation(), Airport.AIRPORTS.get(3),
                    LocalDateTime.of(2022, 8, 13, 12, 45));

            this.registerBooking(flight1, ap1.getSeat(2), new Passenger("Jorma", "Steiner", Person.Gender.MALE,
                    LocalDate.of(1997, 8, 12), "+41 78 608 26 14", "jorma.steiner@gmail.com"));
            this.registerBooking(flight1, ap1.getSeat(3), new Passenger("Nico", "Gujer", Person.Gender.MALE,
                    LocalDate.of(2000, 7, 22), "+41 79 299 19 10", "angry.bacteria@students.bfh.com"));
            this.registerBooking(flight2, ap2.getSeat(6), new Passenger("Shiva", "Shivanna", Person.Gender.MALE,
                    LocalDate.of(1999, 6, 15), "+41 71 223 11 00", "shivchen.shivanna@gmail.com"));

            flight1.addCrewMember(pilot1);
            flight1.addCrewMember(fa1);
            flight1.addCrewMember(fa2);
            flight2.addCrewMember(pilot2);
            flight2.addCrewMember(fa1);
            flight2.addCrewMember(fa2);
            CollectionsManagerLittleFly.getCollectionsManager().addFlight(flight1);
            CollectionsManagerLittleFly.getCollectionsManager().addFlight(flight2);
        }
    }

    public Flight registerFlight(List<UUID> crew, Airplane airplane, Airport start, Airport destination,
                                 LocalDateTime startTime) {
        Flight newFlight = new Flight(airplane, destination, start, startTime);

        for (UUID personId : crew) {
            newFlight.addCrewMember(CollectionsManagerLittleFly.getCollectionsManager().getEmployeeMap().get(personId));
        }

        CollectionsManagerLittleFly.getCollectionsManager().addFlight(newFlight);
        return newFlight;

    }

    public Booking registerBooking(Flight flight, Seat seat, Passenger passenger) {
        Booking booking = new Booking(passenger, seat);
        flight.addBooking(booking);
        seat.setBooked(true);
        return booking;

    }

    public Pilot registerPilot(String firstName, String lastName, Gender gender, LocalDate birthdate,
                               LocalDate hiredSince, double salary, Rank rank) {
        Pilot pilot = new Pilot(firstName, lastName, gender, birthdate, hiredSince, salary, rank);
        CollectionsManagerLittleFly.getCollectionsManager().addEmployee(pilot);
        return pilot;
    }

    public FlightAttendant registerFlightAttendant(String firstName, String lastName, Gender gender,
                                                   LocalDate birthdate, LocalDate hiredSince, double salary, Set<Language> languages) {
        FlightAttendant flightAttendant = new FlightAttendant(firstName, lastName, gender, birthdate, hiredSince,
                salary);
        for (Language l : languages) {
            flightAttendant.addLanguage(l);
        }
        CollectionsManagerLittleFly.getCollectionsManager().addEmployee(flightAttendant);
        return flightAttendant;
    }

    public Set<Employee> getCrew(Flight flight) {
        return flight.getCrew();
    }

    public List<Passenger> getPassengers(Flight flight) {
        List<Booking> bookings = flight.getBookingList();
        List<Passenger> passengers = new ArrayList<>();

        bookings.stream().forEach(b -> passengers.add(b.getPassenger()));

        return Collections.unmodifiableList(passengers);
    }

    public List<Pilot> getPilots() {
        List<Pilot> choosePilot = CollectionsManagerLittleFly.getCollectionsManager().getEmployeeMap().values().stream()
                .filter(employee -> employee instanceof Pilot).map(employee -> (Pilot) employee).toList();
        return Collections.unmodifiableList(choosePilot);
    }

    public List<FlightAttendant> getFlightAttendants() {
        List<FlightAttendant> chooseFlightAttendant = CollectionsManagerLittleFly.getCollectionsManager().getEmployeeMap().values().stream()
                .filter(employee -> employee instanceof FlightAttendant).map(employee -> (FlightAttendant) employee)
                .toList();
        return Collections.unmodifiableList(chooseFlightAttendant);
    }

    public List<Flight> getFlights() {
        return Collections.unmodifiableList(CollectionsManagerLittleFly.getCollectionsManager().getFlightSet().stream().toList());
    }

    public List<Airplane> getPlanes() {
        return Collections.unmodifiableList(CollectionsManagerLittleFly.getCollectionsManager().getAirplaneSet().stream().toList());
    }

    public Map<Integer, Seat> getFreeSeats(Airplane airplane) {
        return airplane.getAllFreeSeats();
    }

    public List<Airport> getAirports() {
        return Airport.AIRPORTS;
    }

    public List<Booking> getBooking(Flight flight) {
        return Collections.unmodifiableList(flight.getBookingList());
    }

    public Passenger makePassenger(String firstName, String lastName, Person.Gender gender, LocalDate birthday,
                                   String phoneNr, String email) {
        Passenger passenger = new Passenger(firstName, lastName, gender, birthday, phoneNr, email);
        return passenger;
    }

    public void persistData() {
        try {
            this.persistAirplanes();
            this.persistEmployees();
            this.persistFlights();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void persistEmployees() throws Exception {
        this.persistPilots();
        this.persistFlightAttendants();

    }

    private void persistPilots() throws Exception {
        File pilotsFile = new File(this.pilotsDocument);

        if (pilotsFile.exists()) { // löscht bestehendes pilotsFile
            pilotsFile.delete();
        }

        try (Writer writer = new FileWriter(pilotsFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            gson.toJson(this.getPilots(), writer);
        } catch (IOException ex) {
            throw new Exception("Could not write " + this.pilotsDocument + " File");
        }
    }

    private void persistFlightAttendants() throws Exception {
        File faFile = new File(this.flightattendantsDocument);

        if (faFile.exists()) { // löscht bestehendes flightattendantFile
            faFile.delete();
        }

        try (Writer writer = new FileWriter(faFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            gson.toJson(this.getFlightAttendants(), writer);
        } catch (IOException ex) {
            throw new Exception("Could not write " + this.flightattendantsDocument + " File");
        }
    }

    private void persistAirplanes() throws Exception {
        File airplaneFile = new File(this.planesDocument);

        if (airplaneFile.exists()) { // löscht bestehendes airplaneFile
            airplaneFile.delete();
        }

        try (Writer writer = new FileWriter(airplaneFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            gson.toJson(CollectionsManagerLittleFly.getCollectionsManager().getAirplaneSet(), writer);
        } catch (IOException ex) {
            throw new Exception("Could not write " + this.planesDocument + " File");
        }

    }

    private void persistFlights() throws Exception {
        File flightsFile = new File(this.flightsDocument);

        if (flightsFile.exists()) { // löscht bestehendes flightsFile
            flightsFile.delete();
        }

        try (Writer writer = new FileWriter(flightsFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            gson.toJson(CollectionsManagerLittleFly.getCollectionsManager().getFlightSet(), writer);
        } catch (IOException ex) {
            throw new Exception("Could not write " + this.flightsDocument + " File");
        }
    }

    private List<Pilot> loadPilots() throws Exception {
        String json = "";

        // build one jsonString with all pilots data
        try (Reader reader = new FileReader(this.pilotsDocument); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNext()) {
                json = json + scanner.nextLine();
            }
        } catch (IOException ex) {
            throw new Exception("Cannot read file: " + this.pilotsDocument, ex);
        }

        // json String to Airplanes in a List
        return Arrays.asList(new Gson().fromJson(json, Pilot[].class));
    }

    private List<FlightAttendant> loadFlightAttendants() throws Exception {
        String json = "";

        // build one jsonString with all flightattendants data
        try (Reader reader = new FileReader(this.flightattendantsDocument); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNext()) {
                json = json + scanner.nextLine();
            }
        } catch (IOException ex) {
            throw new Exception("Cannot read file: " + this.flightattendantsDocument, ex);
        }

        // json String to Airplanes in a List
        return Arrays.asList(new Gson().fromJson(json, FlightAttendant[].class));
    }

    private List<Flight> loadFlights() throws Exception {
        String json = "";

        // build one jsonString with all flight data
        try (Reader reader = new FileReader(this.flightsDocument); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNext()) {
                json = json + scanner.nextLine();
            }
        } catch (IOException ex) {
            throw new Exception("Cannot read file: " + this.flightsDocument, ex);
        }

        // json String to Airplanes in a List
        return Arrays.asList(new Gson().fromJson(json, Flight[].class));
    }

    private List<Airplane> loadPlanes() throws Exception {
        String json = "";

        // build one jsonString with all plane data
        try (Reader reader = new FileReader(this.planesDocument); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNext()) {
                json = json + scanner.nextLine();
            }
        } catch (IOException ex) {
            throw new Exception("Cannot read file: " + this.planesDocument, ex);
        }

        // json String to Airplanes in a List
        return Arrays.asList(new Gson().fromJson(json, Airplane[].class));
    }

    private void assignFlights(List<Flight> flights) {
        for (Flight flight : flights) {
            // assign right airplane
            String planeId = flight.getAirplaneId();
            if (!(CollectionsManagerLittleFly.getCollectionsManager().getAirplane(planeId) == null)) {
                flight.setAirplane(CollectionsManagerLittleFly.getCollectionsManager().getAirplane(planeId));// sets Airplane gotten by Airplane id
                // from LittleFly as flight
                // airplane
            } else {
                System.out.println("assignFlights plane not functioning");
            }

            // Assign Crew to flight
            Set<UUID> crewIds = flight.getCrewIds();
            for (UUID id : crewIds) {
                if (!(CollectionsManagerLittleFly.getCollectionsManager().getEmployeeMap().get(id) == null)) {// not null
                    flight.addCrewMember(CollectionsManagerLittleFly.getCollectionsManager().getEmployeeMap().get(id));

                } else {
                    System.out.println("assign Crewmember not functioning");
                }
            }

            // assign Seats to bookings
            Airplane plane = flight.getAirplane();
            if (!(plane == null)) {
                for (Booking booking : flight.getBookingList()) {
                    if (!(plane.getSeat(booking.getSeatNr()) == null)) {
                        booking.setSeat(plane.getSeat(booking.getSeatNr()));
                    } else {
                        System.out.println("Seat for assigning booking seat not found");
                    }

                }
            } else {
                System.out.println("plane for assigning booking seat not found");
            }

            CollectionsManagerLittleFly.getCollectionsManager().addFlight(flight);

        }
    }

    // lädt alle Employees in LittleFly Liste
    private void assignEmployees(List<Pilot> pilots, List<FlightAttendant> flightsAttendants) {
        for (Pilot pilot : pilots) {
            CollectionsManagerLittleFly.getCollectionsManager().addEmployee(pilot);
        }

        for (FlightAttendant fa : flightsAttendants) {
            CollectionsManagerLittleFly.getCollectionsManager().addEmployee(fa);
        }
    }

    // lädt alle Planes in Airplane Liste von LittleFly
    private void assignPlanes(List<Airplane> planes) {
        for (Airplane airplane : planes) {
            CollectionsManagerLittleFly.getCollectionsManager().addAirplane(airplane);
        }
    }

}
