package Utils;

import java.util.Scanner;
import Models.Teacher;

public class CommandLineInterface {
    // Placeholders until all of the actual methods are ready to be added to the list
    static void option1() { System.out.println("Option 1 executed"); }
    static void option2() { System.out.println("Option 2 executed"); }
    static void option3() { System.out.println("Option 3 executed"); }
    static void option4() { System.out.println("Option 4 executed"); }
    static void option5() { System.out.println("Option 5 executed"); }
    static void option6() { System.out.println("Option 6 executed"); }
    static void option7() { System.out.println("Option 7 executed"); }

    public void start() {

    }

    public static int showMainMenu() {
        Scanner menuOption = new Scanner(System.in);
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

        Select an item from the menu.
        """;
        final int NO_OF_OPTIONS = 8;
        int selection = -1;

        while (true) {
            System.out.println(menu);
            System.out.print("Enter choice (1-" + NO_OF_OPTIONS + "): ");
            String option = menuOption.nextLine().trim();

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

    public static void runSelection(int selection) {
        Runnable[] actions = new Runnable[] {
                CommandLineInterface::option1,
                CommandLineInterface::option2,
                CommandLineInterface::option3,
                CommandLineInterface::option4,
                CommandLineInterface::option5,
                CommandLineInterface::option6,
                CommandLineInterface::option7,
                CommandLineInterface::viewTeacherProfile,
        };

        actions[selection-1].run();
    }

    public static void viewTeacherProfile() {
        Scanner getTeacherID = new Scanner(System.in);
        System.out.println("Enter the ID of the teacher profile you would like to view.");
        String  teacherID = getTeacherID.nextLine().trim();

        //Assuming this code will work once teacher manager class is completed
        Teacher teacher = getTeacher(teacherID);

        System.out.println(teacher);


    }
    
}



