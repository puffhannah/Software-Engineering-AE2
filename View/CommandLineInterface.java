package View;

import java.util.List;
import java.util.Scanner;
import Models.Teacher;
import Models.TeachingRequirement;
import Controllers.RequirementsManager;
import Controllers.TeacherManager;

/**
 * Command‑line interface for interacting with the teacher and requirement management system.
 * <p>
 * Provides a menu‑driven interface that allows the user to:
 * <ul>
 *   <li>View all teaching requirements or teacher profiles</li>
 *   <li>Create, edit, or delete requirements</li>
 *   <li>Add or update teacher profiles</li>
 *   <li>Assign a teacher to a requirement</li>
 *   <li>View a specific teacher's profile</li>
 * </ul>
 * Input validation is performed to ensure data integrity.
 */
public class CommandLineInterface {
    private final Scanner scanner;
    private final RequirementsManager requirementsManager;
    private final TeacherManager teacherManager;

    /**
     * Constructs a new CLI with the given managers.
     *
     * @param rm the manager for teaching requirements
     * @param tm      the manager for teacher data
     */
    public CommandLineInterface(RequirementsManager rm, TeacherManager tm) {
        this.scanner = new Scanner(System.in);
        this.requirementsManager = rm;
        this.teacherManager = tm;
    }

    // ==================== VALIDATION HELPERS ====================

    /**
     * Prompts the user and reads a positive integer.
     * Keeps asking until a valid positive number is entered.
     *
     * @param prompt the message to display to the user
     * @return the positive integer entered
     */
    private int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >0){
                    return value;}
                System.out.println("Please enter a number greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    /**
     * Prompts the user for an optional positive integer.
     * If the user enters nothing, the given default value is returned.
     *
     * @param prompt       the message to display
     * @param defaultValue the value to return if input is empty
     * @return the positive integer entered, or the default
     */
    private int readOptionalPositiveInt(String prompt, int defaultValue) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return defaultValue;
            }
            try {
                int value = Integer.parseInt(input);
                if (value >0){
                    return value;}
                System.out.println("Please enter a number greater than 0.");
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    /**
     * Prompts the user for a non‑empty string.
     *
     * @param prompt the message to display
     * @return the trimmed input string (never empty)
     */
    private String readNonEmptyLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;}
            System.out.println("This field cannot be empty.");
        }
    }

    /**
     * Presents a menu of training status options and returns the user's choice.
     * If the user presses Enter with no input, an empty string is returned.
     *
     * @return the selected training status, or an empty string if no choice was made
     */
    private String getTrainingStatus() {
        String[] trainingStatusOptions = new String[] {"Completed", "In Progress", "Not Started", "Unknown"};

        while (true) {
            System.out.println("Enter training status:");
            for (int i = 0; i < trainingStatusOptions.length; i++) {
                System.out.println((i+1) + " - " + trainingStatusOptions[i]);
            }

            System.out.print("Enter choice (1-" + trainingStatusOptions.length + "): ");

            String input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                return "";
            }

            try {
                int choice = Integer.parseInt(input.trim());
                if (choice >= 1 && choice <= trainingStatusOptions.length) {
                    return trainingStatusOptions[choice - 1];
                }
            } catch (NumberFormatException ignored) {}
            //int choice = Integer.parseInt(readNonEmptyLine("Enter choice (1-" + trainingStatusOptions.length + "): "));

            System.out.println("Invalid input. Please enter a number between 1 and " + trainingStatusOptions.length + ", or press Enter to leave blank.");
        }

    }

    /**
     * Prompts the user to update a specific field of a teacher.
     * Shows the current value in brackets. For the "training status" field,
     * the {@link #getTrainingStatus()} method is used; otherwise a simple text input.
     * If the user enters nothing, the existing value is kept.
     *
     * @param field         the name of the field being updated (used in prompt)
     * @param existingValue the current value of the field
     * @return the new value (or the existing value if input was empty)
     */
    private String promptUpdateField(String field, String existingValue) {

        System.out.print("New " + field + " [" + existingValue + "]: ");
        String update;
        if (field.equals("training status")) {
            update = getTrainingStatus();
        }
        else {
            update = scanner.nextLine().trim();
        }

        if (update.isEmpty()){
            update = existingValue;
        }

        return update;

    }

    // ==================== MAIN MENU & LOOP ====================

    /**
     * Starts the main command‑line loop.
     * Displays the menu, processes the user's choice, and repeats until exit.
     */
    public void start() {
        while (true) {
            int choice = showMainMenu();
            if (choice == 9){
                System.out.println("Exiting system.");
                break;
            }
            runSelection(choice);
        }
    }

    /**
     * Displays the main menu and reads the user's selection.
     *
     * @return a number between 1 and 9 (inclusive) representing the chosen option
     */
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

        while (true) {
            System.out.println(menu);
            System.out.print("Enter choice (1-" + NO_OF_OPTIONS + "): ");
            String option = scanner.nextLine().trim();

            try {
                int selection = Integer.parseInt(option);
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

    /**
     * Executes the action corresponding to the given menu selection.
     *
     * @param selection the menu option (1‑8, as 9 is handled separately)
     */
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

    // ==================== MENU ACTIONS ====================

    /** Displays all teaching requirements. */
    private void optionViewRequirements() {
        List<TeachingRequirement> reqs = requirementsManager.getAllRequirements();
        if (reqs ==null || reqs.isEmpty()){
            System.out.println("No teaching requirements found.");
            return;
        }
        System.out.println("*** Teaching Requirements ***");
        for (TeachingRequirement req : reqs) {
            System.out.println(req);
        }
    }

    /** Displays all teacher profiles. */
    private void optionViewTeachers() {
        List<Teacher> teachers = teacherManager.getAllTeachers();

        if (teachers ==null || teachers.isEmpty()){
            System.out.println("No teacher profiles found.");
            return;
        }
        System.out.println("*** Teachers ***");
        for (Teacher teacher:teachers){
            System.out.println(teacher);
        }
    }

    /** Guides the user through creating a new teaching requirement. */
    private void optionCreateRequirement() {
        System.out.println("*** Create Requirement ***");
        String courseName = readNonEmptyLine("Enter course name: ");
        String skillsNeeded = readNonEmptyLine("Enter skills needed: ");
        int hours = readPositiveInt("Enter number of hours: ");

        TeachingRequirement req = requirementsManager.addRequirement(courseName, skillsNeeded, hours);
        if (req == null){
            System.out.println("Failed to create requirement.");
        } else {
            System.out.println("Requirement created successfully:");
            System.out.println(req);
        }
    }

    /** Allows the user to edit an existing teaching requirement. */
    private void optionEditRequirement() {
        System.out.println("*** Edit Requirement ***");
        int id =  readPositiveInt("Enter requirement id to update: ");
        TeachingRequirement existing = requirementsManager.getRequirement(id);
        if (existing ==null){
            System.out.println("Requirement not found.");
            return;
        }
        System.out.println("Current requirement:");
        System.out.println(existing);
        System.out.println("Leave a field blank to keep the current value.");

        String courseName = promptUpdateField("course name", existing.getCourseName());
        String skillsNeeded = promptUpdateField("skills needed", existing.getSkillsNeeded());
        int hours= readOptionalPositiveInt("New hours [" + existing.getHours() + "]: ", existing.getHours());


        TeachingRequirement updated= new TeachingRequirement(id, courseName, skillsNeeded, hours);
        requirementsManager.updateRequirement(id, updated);
        System.out.println("Requirement updated successfully:");
        System.out.println(requirementsManager.getRequirement(id));
    }

    /** Guides the user through adding a new teacher. */
    private void optionAddTeacher() {
        // (Done) Incomplete code here, just put stuff that TeacherManager needs
        // Edit: Added the logic to add new teacher
        System.out.println("*** Add Teacher ***");
        String name = readNonEmptyLine("Enter teacher name: ");
        String skills = readNonEmptyLine("Enter teacher skills: ");
        String trainingStatus;

        while (true) {
            trainingStatus = getTrainingStatus();
            if (trainingStatus.isEmpty()) {
                System.out.println("You must enter the teacher's training status.");
                continue;
            }
            break;
        }


        Teacher newTeacher = new Teacher(name, skills, trainingStatus);
        boolean success = teacherManager.addTeacher(newTeacher);
        if (!success) {
            System.out.println("Teacher ID already exists in the system. Failed to add teacher.");
        }
        else {
            // Success message feedback to user
            System.out.println("New teacher added successfully.");
            System.out.println(newTeacher); //just for clarity
        }
    }

    /** Allows the user to update an existing teacher's details. */
    private void optionUpdateTeacher(){
        System.out.println("*** Update Teacher ***");
        // System.out.println("Enter the ID of the teacher profile you would like to UPDATE: ");
        int id = readPositiveInt("Enter the ID of the teacher profile you would like to UPDATE: ");
        // (Done) This is incomplete, just put logics that TeacherManager class needs (esp the booleans)
            //Edit: Added logics fro name, skills, training status
        Teacher existingTeacher = teacherManager.getTeacher(id);
        if (existingTeacher == null) {
            System.out.println("Teacher profile not found. Failed to update teacher profile.");
            return;
        }
        System.out.println("Current teacher profile:");
        System.out.println(existingTeacher);
        System.out.println("Leave a field blank to keep the current value.");

        String name = promptUpdateField("name", existingTeacher.getName());
        String skills = promptUpdateField("skills", existingTeacher.getSkills());
        String trainingStatus = promptUpdateField("training status", existingTeacher.getTrainingStatus());

        Teacher updatedTeacher = new Teacher(id, name, skills, trainingStatus);
        boolean success = teacherManager.updateTeacher(id, updatedTeacher);
        if (success) {
            System.out.println("Teacher profile updated successfully.");
            System.out.println(teacherManager.getTeacher(id));
        }
        else {
            System.out.println("Teacher profile not found. Failed to update teacher profile.");
        }
    }

    /** Assigns a teacher to a teaching requirement. */
    private void optionAssignTeacher() {
        System.out.println("*** Assign Teacher to Requirement ***");
        int reqId= readPositiveInt("Enter requirement ID: ");
        TeachingRequirement requirement = requirementsManager.getRequirement(reqId);
        if (requirement ==null){
            System.out.println("Requirement not found.");
            return;
        }
        int teacherId = readPositiveInt("Enter teacher ID: ");
        Teacher teacher = teacherManager.getTeacher(teacherId);

        if (teacher ==null){
            System.out.println("Teacher not found.");
            return;
        }
        requirementsManager.assignTeacher(reqId, teacher);
        System.out.println("Teacher assigned successfully:");
        System.out.println(requirementsManager.getRequirement(reqId));
    }

    /** Displays the full profile of a single teacher. */
    private void viewTeacherProfile() {
        System.out.println("*** View Teacher Profile ***");

        int id = readPositiveInt("Enter the ID of the teacher profile you would like to view: ");
        Teacher teacher = teacherManager.getTeacher(id);

        if (teacher == null) {
            System.out.println("Teacher not found.");
            }
        else {
            System.out.println(teacher);
        }

    }

}



