package View;

import java.util.List;
import java.util.Scanner;
import Models.Teacher;
import Models.TeachingRequirement;
import Controllers.RequirementsManager;
import Controllers.TeacherManager;

public class CommandLineInterface {
    private Scanner scanner = new Scanner(System.in);
    private RequirementsManager requirementsManager;
    private TeacherManager teacherManager;

    public CommandLineInterface(RequirementsManager rm, TeacherManager tm) {
        this.requirementsManager = rm;
        this.teacherManager = tm;
    }
    // Placeholders until all of the actual methods are ready to be added to the list
    // void option1() { System.out.println("Option 1 executed"); }
    // void option2() { System.out.println("Option 2 executed"); }
    // void option3() { System.out.println("Option 3 executed"); }
    // void option4() { System.out.println("Option 4 executed"); }
    // void option5() { System.out.println("Option 5 executed"); }
    // void option6() { System.out.println("Option 6 executed"); }
    // void option7() { System.out.println("Option 7 executed"); }

    public void start() {
    while (true) {
        int choice = showMainMenu();
        if (choice == 9) break;  // or add an exit option
        runSelection(choice);
        }
    }

    public int showMainMenu() {
        String menu = """
        ===== Main Menu =====
        1 - View Requirements
        2 - View Teachers
        3 - Create Requirement
        4 - Edit Requirement
        5 - Add Teacher
        6 - Update Teacher
        7 - Set Teacher Assignment
        8 - View Teacher Profile
        9 - Exit

        Select an item from the menu.
        """;
        final int NO_OF_OPTIONS = 9;
        int selection = -1;

        while (true) {
            System.out.println(menu);
            System.out.print("Enter choice (1-" + NO_OF_OPTIONS + "): ");
            String option = scanner.nextLine().trim();

            try {
                selection = Integer.parseInt(option);
                if (selection >= 1 && selection <= NO_OF_OPTIONS) {
                    return selection; // immediate return when valid
                } else {
                    System.out.println("Please enter a number between 1 and " + NO_OF_OPTIONS + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

    }

    public void runSelection(int selection) {
        Runnable[] actions = new Runnable[] {
                this::optionViewRequirements,
                this::optionViewTeachers,
                this::optionCreateRequirement,
                this::optionEditRequirement,
                this::optionAddTeacher,
                this::optionUpdateTeacher,
                this::optionAssignTeacher,
                this::viewTeacherProfile,
        };

        actions[selection-1].run();
    }

    private void optionViewRequirements() {
        List<TeachingRequirement> reqs = requirementsManager.getAllRequirements();
        for (TeachingRequirement req : reqs) {
            System.out.println(req);   
        }
    }

    private void optionViewTeachers() {

    }

    private void optionCreateRequirement() {

    }

    private void optionEditRequirement() {

    }

    private void optionAddTeacher() {

    }

    private void optionUpdateTeacher(){

    }

    private void optionAssignTeacher() {

    }

    private void viewTeacherProfile() {
        System.out.println("Enter the ID of the teacher profile you would like to view.");
        String  teacherID = scanner.nextLine().trim();

        //Assuming this code will work once teacher manager class is completed
        int id = Integer.parseInt(teacherID);
        Teacher teacher = teacherManager.getTeacher(id);

        System.out.println(teacher);


    }


}



