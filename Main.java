import Controllers.RequirementsManager;
import Controllers.TeacherManager;
import View.CommandLineInterface;

public class Main {
    public static void main(String[] args) {
        RequirementsManager requirementsManager = new RequirementsManager();
        TeacherManager teacherManager = TeacherManager.getInstance();
        CommandLineInterface cli = new CommandLineInterface(requirementsManager, teacherManager);
        cli.start();
    }
}
