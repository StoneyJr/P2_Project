
package btx.prog.project.cli;


import btx.prog.project.validation.IntRangeValidator;

import java.util.Map;
import java.util.TreeMap;


/**
 * This is a menu class to help to structure the cli.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */


public class Menu implements Runnable {
	private Map<Integer, MenuItem> menuItems = new TreeMap<>();
	private int level;

	public Menu(int level) {
		this.level = level;
		if (level == 0) {
			this.addMenuItem(new MenuItem(0, "Terminate Application", () -> CLI.exit()));
		} else {
			this.addMenuItem(new MenuItem(0, "Return to upper level", () -> {}));
		}
	}

	public void addMenuItem(MenuItem menuItem) {
		if (this.menuItems.containsKey(menuItem.getCommand())) {
			throw new IllegalArgumentException("Key already in use: " + menuItem.getCommand());
		}
		this.menuItems.put(menuItem.getCommand(), menuItem);
	}

	@Override
	public void run() {
		boolean done = false;
		do {
			String indent = "";
			for (int i = 0; i < this.level; i++) {
				indent = indent + "  ";
			}
			displayMenuItems(indent);
			Integer cmdNbr = new ConsoleInput<Integer>(new IntRangeValidator(0, this.menuItems.size() - 1), indent + "Enter command [0 - " + (this.menuItems.size() - 1) + "]: ")
					.enterValue();
			if (this.menuItems.containsKey(cmdNbr)) {
				this.menuItems.get(cmdNbr).performAction();
			} else {
				System.out.println("Illegal command, choose another one");
			}
			if (cmdNbr.equals(Integer.valueOf(0))) { // Terminate menu item must have key = 0
				done = true;
			}
		} while (!done);
		System.out.println();
	}

	private void displayMenuItems(String indent) {
		this.menuItems.forEach((k, v) -> System.out.println(indent + k + ": " + v.getDescription()));
	}
}
