package btx.prog.project.domain;

import java.util.Arrays;
import java.util.List;

/**
 * Instances of this class represent airport. Our airline can accommodate flights between 11 european airports,
 * in 9 countries.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */




public class Airport {

	// finales Set an Flughäfen
	public static final List<Airport> AIRPORTS = Arrays.asList(new Airport("Zürich Flughafen", Country.SWITZERLAND),
			new Airport("Basel Flughafen", Country.SWITZERLAND), new Airport("Berlin Flughafen", Country.GERMANY),
			new Airport("Düsseldorf Flughafen", Country.GERMANY), new Airport("Paris-Orly Flughafen", Country.FRANCE),
			new Airport("Mailand Flughafen", Country.ITALY), new Airport("Stockholm Flughafen", Country.SWEDEN),
			new Airport("Wien Flughafen", Country.AUSTRIA), new Airport("Oslo Flughafen", Country.NORWAY),
			new Airport("Reykjavik Flughafen", Country.ICELAND), new Airport("Athen Flughafen", Country.GREECE));

	private String name;
	private Country country;

	public enum Country {
		SWITZERLAND, GERMANY, FRANCE, ITALY, SWEDEN, AUSTRIA, NORWAY, ICELAND, GREECE
	}

	/**
	 * This is the constructor of an airport. It is generated with a name and a country in
	 * which the airport is located.
	 * @param name
	 * @param country
	 */
	public Airport(String name, Country country) {
		this.name = name;
		this.country = country;
	}

	public String getName() {
		return this.name;
	}

	public Country getCountry() {
		return this.country;
	}

	@Override
	public String toString() {
		return this.name + " [ " + this.country + " ]";
	}


}
