package btx.prog.project.cli;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

import btx.prog.project.domain.Airplane;
import btx.prog.project.domain.Airport;
import btx.prog.project.domain.Booking;
import btx.prog.project.domain.Employee;
import btx.prog.project.domain.Flight;
import btx.prog.project.domain.FlightAttendant;
import btx.prog.project.domain.FlightAttendant.Language;
import btx.prog.project.domain.CollectionsManagerLittleFly;
import btx.prog.project.domain.Passenger;
import btx.prog.project.domain.Person;
import btx.prog.project.domain.Person.Gender;
import btx.prog.project.domain.Pilot;
import btx.prog.project.domain.Pilot.Rank;
import btx.prog.project.domain.Seat;
import btx.prog.project.service.FlightService;
import btx.prog.project.validation.BooleanValidator;
import btx.prog.project.validation.DepartureDateValidator;
import btx.prog.project.validation.DoubleRangeValidator;
import btx.prog.project.validation.EmailValidator;
import btx.prog.project.validation.GenderValidator;
import btx.prog.project.validation.HiredDateValidator;
import btx.prog.project.validation.IntRangeValidator;
import btx.prog.project.validation.LanguageValidator;
import btx.prog.project.validation.LocalDateValidator;
import btx.prog.project.validation.PhoneValidator;
import btx.prog.project.validation.RankValidator;

/**
 * This is the command line interface of our project and can interact with a
 * user. It can register new flights, new bookings, new airplanes and new employees such
 * as pilots and flight attendants. Next to entering new data it can print out
 * lists of flights, bookings, airplanes and employees. The data is saved into a
 * Json-file.
 *
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */


public class CLI {
	private static Scanner scanner = new Scanner(System.in); // closed on exit()
	private static String ROOT_DOCUMENTS = System.getProperty("user.home") + File.separator + "documents";
	private final static String PLANES_FILE_NAME = "planes.json";
	private final static String PLANES_LOCATION = ROOT_DOCUMENTS + File.separator + PLANES_FILE_NAME;

	private final static String FLIGHATTENDANTS_FILE_NAME = "flightattendants.json";
	private final static String FLIGHATTENDANTS_LOCATION = ROOT_DOCUMENTS + File.separator + FLIGHATTENDANTS_FILE_NAME;

	private final static String PILOTS_FILE_NAME = "pilots.json";
	private final static String PILOTS_LOCATION = ROOT_DOCUMENTS + File.separator + PILOTS_FILE_NAME;

	private final static String FLIGHTS_FILE_NAME = "flights.json";
	private final static String FLIGHTS_LOCATION = ROOT_DOCUMENTS + File.separator + FLIGHTS_FILE_NAME;

	private static FlightService flightService;

	public static void main(String[] args) {
		try {
			CLI.flightService = new FlightService(CLI.PLANES_LOCATION, CLI.PILOTS_LOCATION,
					CLI.FLIGHATTENDANTS_LOCATION, CLI.FLIGHTS_LOCATION);
		} catch (Exception ex) {
			System.err.println("Cannot initialize application: ");
			System.err.println(ex);
			System.err.println("Close application");
			return;
		}

		System.out.println("Welcome to LittleFly");

		Menu mainMenu = new Menu(0);
		int cmdNumber = 1;
		int subCmdNumber = 1;
		Menu listCommands = new Menu(1);
		listCommands.addMenuItem(new MenuItem(subCmdNumber++, "List of all employees", CLI::listEmployees));
		listCommands.addMenuItem(new MenuItem(subCmdNumber++, "List of pilots", CLI::listPilots));
		listCommands.addMenuItem(new MenuItem(subCmdNumber++, "List of flightattendants", CLI::listFlightAttendants));
		listCommands.addMenuItem(new MenuItem(subCmdNumber++, "List of flights", CLI::listFlight));
		listCommands.addMenuItem(new MenuItem(subCmdNumber++, "List of bookings on a flight", CLI::listBookings));
		listCommands.addMenuItem(new MenuItem(subCmdNumber++, "List of passengers on a flight", CLI::listPassengers));
		listCommands.addMenuItem(new MenuItem(subCmdNumber++, "List of crew on a flight", CLI::listCrew));

		mainMenu.addMenuItem(new MenuItem(cmdNumber++, "Lists", listCommands));
		Menu enterInformation = new Menu(1);
		subCmdNumber = 1;
		enterInformation.addMenuItem(new MenuItem(subCmdNumber++, "enter new pilot data", CLI::enterPilot));
		enterInformation.addMenuItem(
				new MenuItem(subCmdNumber++, "enter new flight attendant data", CLI::enterFlightAttendant));
		enterInformation.addMenuItem(new MenuItem(subCmdNumber++, "enter new flight data", CLI::enterFlight));
		enterInformation.addMenuItem(new MenuItem(subCmdNumber++, "enter new booking data", CLI::enterBooking));

		mainMenu.addMenuItem(new MenuItem(cmdNumber++, "Enter Information", enterInformation));

		// "Execute" main menu
		mainMenu.run();
	}

// Alle CHOOSE Methoden

	private static Pilot choosePilot() {
		List<Pilot> pilots = flightService.getPilots();

		CLI.printPilots(pilots);

		int choosenPilotNr = new ConsoleInput<Integer>(new IntRangeValidator(0, pilots.size() - 1),
				"Enter number of Pilot: ").enterValue();
		return pilots.get(choosenPilotNr);
	}

	private static FlightAttendant chooseFlightAttendant() {

		List<FlightAttendant> flightAttendants = flightService.getFlightAttendants();

		CLI.printFlightAttendants(flightAttendants);

		int choosenFlightAttendantNr = new ConsoleInput<Integer>(new IntRangeValidator(0, flightAttendants.size() - 1),
				"Enter number of Flightattendant: ").enterValue();
		return flightAttendants.get(choosenFlightAttendantNr);
	}

	private static FlightAttendant chooseSecondFlightAttendant(FlightAttendant firstAttendant) {
		boolean ok = false;
		FlightAttendant secondAttendant = null;

		while (!ok) {
			secondAttendant = CLI.chooseFlightAttendant();
			if (!(secondAttendant.getFullPersonId().equals(firstAttendant.getFullPersonId()))) { // ok = true if Id's
																									// dont match
				ok = true;
			} else {
				System.out.println("!!!!Cannot choose same Flightattendant twice!");
			}
		}
		return secondAttendant;
	}

	private static Flight chooseFlight() {

		List<Flight> flights = flightService.getFlights();

		CLI.printFlights(flights);

		int choosenFlightNr = new ConsoleInput<Integer>(new IntRangeValidator(0, flights.size() - 1),
				"Enter number of Flight: ").enterValue();
		return flights.get(choosenFlightNr);

	}

	private static Airplane chooseAirplane() {

		List<Airplane> planes = flightService.getPlanes();

		CLI.printPlanes(planes);

		int choosenPlaneNr = new ConsoleInput<Integer>(new IntRangeValidator(0, planes.size() - 1),
				"Enter number of Plane: ").enterValue();
		return planes.get(choosenPlaneNr);
	}

	private static Seat chooseFreeSeat(Airplane airplane) {
		Map<Integer, Seat> freeSeats = flightService.getFreeSeats(airplane);

		boolean validInput = false;
		Seat freeSeat = null;

		// loop as long until a free Seat Nr was given as Input
		while (!validInput) {
			CLI.printSeatsOfPlane(airplane, freeSeats);
			System.out.print("Choose SeatNr: ");
			String input = scanner.nextLine();

			try {
				int choosenSeatNr = Integer.valueOf(input);
				if (freeSeats.containsKey(choosenSeatNr)) {
					freeSeat = freeSeats.get(choosenSeatNr);
					validInput = true;
				} else {
					System.out.println("Seat not found! Try again");
				}
			} catch (NumberFormatException ex) {
				System.out.println("Input not a number! Try again");
			}
		}

		return freeSeat;
	}

	private static Airport chooseAirport(String string) { // string --> art von Flughafen zb. "Destination" oder
															// "Starting"
		List<Airport> airports = flightService.getAirports();

		CLI.printAirports(airports);

		int choosenAirportNr = new ConsoleInput<Integer>(new IntRangeValidator(0, airports.size() - 1),
				"Enter number of " + string + " Airport: ").enterValue();
		return airports.get(choosenAirportNr);
	}

// Alle ENTER Methoden

	private static void enterFlight() {
		System.out.println("  Enter flight data:");
		Airplane airplane = CLI.chooseAirplane();
		Airport startingPointOfAirplane = CLI.chooseAirport("Starting");
		Airport destinationOfAirplane = CLI.chooseAirport("Destination");
		LocalDate dateOfDeparture = new ConsoleInput<LocalDate>(new DepartureDateValidator(),
				" What day is the Departure of the airplane? ").enterValue();
		Integer hourOfDeparture = new ConsoleInput<Integer>(new IntRangeValidator(0, 23),
				" What hour [00 - 23] is the Departure? ").enterValue();
		Integer minuteOfDeparture = new ConsoleInput<Integer>(new IntRangeValidator(0, 59),
				" What minute [00 - 59] is the Departure? ").enterValue();
		LocalDateTime timeOfDeparture = LocalDateTime.of(dateOfDeparture,
				LocalTime.of(hourOfDeparture, minuteOfDeparture));

		Pilot pilot = CLI.choosePilot();
		System.out.println(" Choose two Flightattendants:");
		FlightAttendant flightAttendant1 = CLI.chooseFlightAttendant();
		FlightAttendant flightAttendant2 = CLI.chooseSecondFlightAttendant(flightAttendant1);

		List<UUID> crew = Arrays.asList(pilot.getFullPersonId(), flightAttendant1.getFullPersonId(),
				flightAttendant2.getFullPersonId());

		flightService.registerFlight(crew, airplane, startingPointOfAirplane, destinationOfAirplane, timeOfDeparture);

		System.out.println("-->registered Flight");
	}

	private static void enterBooking() {
		System.out.println(" Choose a flight:");
		Flight chooseFlight = chooseFlight();
		System.out.println(" Choose airplane seat:");
		Seat seat = chooseFreeSeat(chooseFlight.getAirplane());

		Passenger passenger = enterPassenger();

		flightService.registerBooking(chooseFlight, seat, passenger);

		System.out.println("--> Registered Booking");

	}

	private static Person enterPerson() {
		System.out.print("  First name: ");
		String firstName = ConsoleInput.scanLine();
		System.out.print("  Last name: ");
		String lastName = ConsoleInput.scanLine();

		return new Person(firstName, lastName, CLI.enterGender(), CLI.enterBirthdate());
	}

	private static Employee enterEmployee() {
		Person person = CLI.enterPerson();

		return new Employee(person.getFirstName(), person.getLastName(), person.getGender(), person.getBirthdate(),
				CLI.enterHiredSince(), CLI.enterSalary());
	}

	// only called with enterBooking method
	private static Passenger enterPassenger() {
		System.out.println("  Enter passenger data");
		Person person = CLI.enterPerson();

		return new Passenger(person.getFirstName(), person.getLastName(), person.getGender(), person.getBirthdate(),
				CLI.enterPhoneNr(), CLI.enterEmail());
	}

	private static void enterPilot() {
		System.out.println("  Enter pilot data");

		Employee employee = CLI.enterEmployee();
		flightService.registerPilot(employee.getFirstName(), employee.getLastName(), employee.getGender(),
				employee.getBirthdate(), employee.getHiredSince(), employee.getSalary(), CLI.enterRank());

		System.out.println("--> registered a pilot");
	}

	private static void enterFlightAttendant() {
		System.out.println(" Enter flight attendant data");

		Employee employee = CLI.enterEmployee();
		Set<Language> languages = CLI.enterLanguage();

		flightService.registerFlightAttendant(employee.getFirstName(), employee.getLastName(), employee.getGender(),
				employee.getBirthdate(), employee.getHiredSince(), employee.getSalary(), languages);

		System.out.println("--> registered a flight attendant");

	}

	private static Set<Language> enterLanguage() {
		boolean choosingLanguage = true;
		Set<Language> languages = new HashSet<>();

		do {
			String languagesString = "Languages: \n";
			for (Language l : FlightAttendant.Language.values()) {
				languagesString += " " + l + "\n";
			}

			Language language = new ConsoleInput<FlightAttendant.Language>(new LanguageValidator(), languagesString)
					.enterValue();
			languages.add(language);

			choosingLanguage = new ConsoleInput<Boolean>(new BooleanValidator(), "Add another language [y/n]?")
					.enterValue();
		} while (choosingLanguage);

		return languages;

	}

	private static Double enterSalary() {
		Double salary = new ConsoleInput<Double>(new DoubleRangeValidator(0, 20000000), "Salary").enterValue();
		return salary;
	}

	private static LocalDate enterHiredSince() {
		LocalDate value = new ConsoleInput<LocalDate>(new HiredDateValidator(), "Hired since").enterValue();
		return value;
	}

	private static LocalDate enterBirthdate() {
		LocalDate value = new ConsoleInput<LocalDate>(new LocalDateValidator(), "Birthdate").enterValue();
		return value;
	}

	private static Person.Gender enterGender() {
		String genders = "Gender: \n";
		for (Gender g : Person.Gender.values()) {
			genders += " " + g + "\n";
		}
		Gender gender = new ConsoleInput<Person.Gender>(new GenderValidator(), genders).enterValue();
		return gender;
	}

	private static Rank enterRank() {

		String ranks = "Ranks: \n";
		for (Rank r : Pilot.Rank.values()) {
			ranks += " " + r + "\n";
		}

		Rank rank = new ConsoleInput<Rank>(new RankValidator(), ranks).enterValue();
		return rank;
	}

	private static String enterEmail() {

		String email = new ConsoleInput<String>(new EmailValidator(), "  Enter Email:").enterValue();
		return email;
	}

	private static String enterPhoneNr() {
		String phone = new ConsoleInput<String>(new PhoneValidator(), "  Enter Phone [+41(0)XX XXX XX XX]:")
				.enterValue();
		return phone;
	}

	// Alle LIST Methoden

	private static void listEmployees() {
		printEmployee(CollectionsManagerLittleFly.getCollectionsManager().getEmployeeMap().values().stream().toList());
	}

	private static void listPilots() {
		List<Pilot> pilots = flightService.getPilots();

		CLI.printPilots(pilots);
	}

	private static void listFlightAttendants() {
		List<FlightAttendant> flightAttendants = flightService.getFlightAttendants();

		CLI.printFlightAttendants(flightAttendants);
	}

	private static void listBookings() {
		Flight flight = CLI.chooseFlight();

		System.out.println(flight.toString());
		CLI.printBookings(flight.getBookingList());
	}

	private static void listPassengers() {
		Flight flight = CLI.chooseFlight();

		System.out.println(flight.toString());
		CLI.printPassengers(flightService.getPassengers(flight));
	}

	private static void listCrew() {
		Flight flight = CLI.chooseFlight();

		CLI.printEmployee(flight.getCrew().stream().toList());

	}

	private static void listFlight() {
		Set<Flight> SetFlight = CollectionsManagerLittleFly.getCollectionsManager().getFlightSet();
		List<Flight> FlightList = SetFlight.stream().toList();
		printFlights(FlightList);

	}

	// Alle PRINT Methoden

	private static void printBookings(List<Booking> bookings) {
		System.out.println("************************************************************************");
		System.out.println("#### Bookings:");
		System.out.println("  BookingID | Seat | Passenger ");
		for (Booking b : bookings) {
			System.out.printf("  %-9d | %-4d | %-18s ", b.getBookingID(), b.getSeat().getSeatNr(),
					b.getPassenger().toString());
			System.out.println();
		}
		System.out.println("************************************************************************");
	}

	private static void printPassengers(List<Passenger> passengers) {
		System.out.println("************************************************************************");
		System.out.println("#### Passengers:");
		System.out.println("  Nr | Last Name  | First Name | Gender  | Birthdate | phoneNr          | Email ");
		int i = 0;
		for (Passenger p : passengers) {
			System.out.printf("  %-2d | %-10s | %-10s | %-7s  | %-6s | %-16s | %-15s", i++, p.getLastName(),
					p.getFirstName(), p.getGender().name(), p.getBirthdate(), p.getPhoneNr(), p.getEmail());
			System.out.println();

		}
		System.out.println("************************************************************************");
	}

	private static void printFlights(List<Flight> flight) {
		System.out.println("************************************************************************");
		System.out.println("#### Flights :");
		System.out.println("  Nr | AirplaneID | Destination          | Departure Airport    | Time of Flight   |");
		int i = 0;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		for (Flight f : flight) {
			System.out.printf("  %-2d | %-10s | %-20s | %-20s | %-16s ", i++, f.getAirplane().getAirplaneID(),
					f.getDestinationOfAirplane().getName(), f.getStartingPointOfAirplane().getName(),
					f.getTimeToStart().format(formatter));
			System.out.println();
		}
		System.out.println("************************************************************************");
	}

	private static void printPlanes(List<Airplane> planes) {
		System.out.println("************************************************************************");
		System.out.println("#### Planes:");
		System.out.println("  Nr | Type       | Location           | Seats |");
		int i = 0;
		for (Airplane a : planes) {
			System.out.printf("  %-2d | %-10s | %-18s | %-5s ", i++, a.getType(), a.getLocation().getName(),
					a.getSeatsCount());
			System.out.println();
		}
		System.out.println("************************************************************************");

	}

	private static void printSeatsOfPlane(Airplane airplane, Map<Integer, Seat> seats) {
		System.out.println("************************************************************************");
		System.out.println("#### free Seats of " + airplane.getAirplaneID() + ":");
		System.out.println("  SeatNr |");
		seats.forEach((nr, seat) -> System.out.print(nr + " |")); // prints seatNr of each seat of map out
		System.out.println();
	}

	private static void printAirports(List<Airport> airports) {
		System.out.println("************************************************************************");
		System.out.println("#### Airports:");
		System.out.println("  Nr | Location             |");
		int i = 0;
		for (Airport a : airports) {
			System.out.printf("  %-2d | %-20s | ", i++, a.getName());
			System.out.println();
		}
		System.out.println("************************************************************************");
	}

	private static void printFlightAttendants(List<FlightAttendant> employee) {
		System.out.println("************************************************************************");
		System.out.println("#### Flight attendants:");
		System.out.println("  Nr | Last Name  | First Name | Gender  | Birthdate  | Languages");
		int i = 0;
		for (FlightAttendant f : employee) {
			System.out.printf("  %-2d | %-10s | %-10s | %-7s | %-6s | %-10s", i++, f.getLastName(), f.getFirstName(),
					f.getGender().name(), f.getBirthdate(), f.getLanguages().toString());
			System.out.println();
		}
		System.out.println("************************************************************************");
	}

	private static void printPilots(List<Pilot> employee) {
		System.out.println("************************************************************************");
		System.out.println("#### Pilots:");
		System.out.println("  Nr | Last Name  | First Name | Gender  | Birthdate  | Rank               |");
		int i = 0;
		for (Pilot p : employee) {
			System.out.printf("  %-2d | %-10s | %-10s | %-7s | %-6s | %-18s", i++, p.getLastName(), p.getFirstName(),
					p.getGender().name(), p.getBirthdate(), p.getRank());
			System.out.println();
		}
		System.out.println("************************************************************************");
	}

	private static void printEmployee(List<Employee> employee) {
		System.out.println("************************************************************************");
		System.out.println("#### Employee:");
		System.out.println("  Nr | Last Name  | First Name | Gender  | Birthdate | Occupation");
		int i = 0;
		for (Employee p : employee) {
			System.out.printf("  %-2d | %-10s | %-10s | %-7s  | %-6s | %-6s", i++, p.getLastName(), p.getFirstName(),
					p.getGender().name(), p.getBirthdate(), p.getClass().getSimpleName());
			System.out.println();

		}
		System.out.println("************************************************************************");
	}

	static void exit() {
		scanner.close();

		CLI.flightService.persistData();// alle daten in json speichern

	}
}
