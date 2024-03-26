
package btx.prog.project.cli;


/**
 * This class supports our menu in the cli
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */
public class MenuItem {
	private Integer cmdNbr;
	private String description;
	private Runnable action;

	public MenuItem(Integer cmdNbr, String description, Runnable action) {
		this.cmdNbr = cmdNbr;
		this.description = description;
		this.action = action;
	}

	public Integer getCommand() {
		return this.cmdNbr;
	}

	public String getDescription() {
		return this.description;
	}

	public void performAction() {
		// Execute runnable
		System.out.println("Command: " + this.description);
		this.action.run();
	}
}
