import Controllers.RequirementsManager;
import Controllers.TeacherManager;
import Boundary.CommandLineInterface;
/**
 * Entry point for the Teacher‑Requirement Management System.
 * <p>
 * This class initialises the necessary controllers and launches
 * the command‑line interface, allowing the user to interact with
 * the system via a menu‑driven console.
 */
public class Main {

    /**
     * The main method – starting point of the application.
     * <p>
     * It creates instances of the requirements manager and teacher manager
     * then constructs and starts the command‑line interface.
     *
     * @param args command‑line arguments (not used)
     */
    public static void main(String[] args) {
        RequirementsManager requirementsManager = new RequirementsManager();
        TeacherManager teacherManager = TeacherManager.getInstance();
        CommandLineInterface cli = new CommandLineInterface(requirementsManager, teacherManager);
        cli.start();
    }
}
