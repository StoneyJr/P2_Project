package btx.prog.project.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * This is the class of our project which contains all the company
 * information/lists. It includes a set of airplanes, a set of flights and a map
 * of employees. This is the class which manages all our lists.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */


public class CollectionsManagerLittleFly {

	private String name;
	private Set<Airplane> airplaneSet;
	private Map<UUID, Employee> employeeMap;
	private Set<Flight> flightSet;

	private static CollectionsManagerLittleFly collectionsManager = null;

	public static CollectionsManagerLittleFly getCollectionsManager() {
		if (collectionsManager == null) {
			collectionsManager = new CollectionsManagerLittleFly();
		}
		return CollectionsManagerLittleFly.collectionsManager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Airplane> getAirplaneSet() {
		if (airplaneSet == null) {
			airplaneSet = new HashSet<>();
		}

		return airplaneSet;
	}

	public void addAirplane(Airplane airplane) {
		getAirplaneSet().add(airplane);
	}

	public Map<UUID, Employee> getEmployeeMap() {
		if (employeeMap == null) {
			employeeMap = new HashMap<>();
		}
		return employeeMap;
	}

	public void addEmployee(Employee employee) {
		getEmployeeMap().put(employee.getFullPersonId(), employee);
	}

	public Set<Flight> getFlightSet() {
		if (flightSet == null) {
			flightSet = new HashSet<>();
		}
		return flightSet;
	}

	public void addFlight(Flight flight) {
		getFlightSet().add(flight);
	}

	public static void setLittleFly(CollectionsManagerLittleFly collectionsManager) {
		CollectionsManagerLittleFly.collectionsManager = collectionsManager;
	}

	public Airplane getAirplane(String planeId) {

		for (Airplane airplane : this.airplaneSet) {
			if (airplane.getAirplaneID().equals(planeId)) {
				return airplane;
			}
		}

		return null;
	}
}
