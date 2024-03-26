
package btx.prog.project.cli;

import java.util.Scanner;

import btx.prog.project.validation.InvalidInputException;
import btx.prog.project.validation.Validator;

/**
 * This is the class with helps to validate the users input in our command line interface. It also contains the scanner
 * which reads the inputs. *
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */

public class ConsoleInput<V> {
	private static Scanner scanner = new Scanner(System.in);
	private Validator<V> validator;
	private String displayString;

	public ConsoleInput(Validator<V> validator, String displayString) {
		this.validator = validator;
		this.displayString = displayString;
	}

	private static String getInput() {
		return scanner.nextLine();
	}

	public V enterValue() {
		boolean validInput = false;
		V value = null;

		while (!validInput) {
			try {
				System.out.print(displayString);
				String str = getInput();
				value = validator.validate(str);
				validInput = true;
			} catch (InvalidInputException e) {
				System.out.println(e.toString());
			}
		}
		return value;
	}

	public void onExit() {
		scanner.close();
	}

	public static String scanLine() {
		// String with more than one word allowed
		scanner.useDelimiter("[\\n\\r]+");
		String line = scanner.nextLine();
		scanner.reset();
		return line;
	}

}
