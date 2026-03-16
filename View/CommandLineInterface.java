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
        List<Teacher> teachers = teacherManager.getAllTeachers();
        if (teacher ==null || teachers.isEmpty()){
            System.out.println(("Teacher not found.");
            return;
        }
        for (Teacher teacher : teachers){
            System.out.println(teacher);
        }
    }

    private void optionCreateRequirement() {
        System.out.println("Enter course name: ");
        String courseName = scanner.nextLine().trim();

        System.out.println("Enter skills needed: ");
        String skillsNeeded = scanner.nextLine().trim();

        System.out.println("Enter hours: ");
        int hours = Integer.parseInt(scanner.nextLine().trim());

        TeachingRequirement newRequirement = requirementsManager.addRequirement(
                courseName, skillsNeeded, hours);

        System.out.println("Requirement created successfully.");
        System.out.println(newRequirement);

    }

    private void optionEditRequirement() {
        System.out.println("Enter the requirement ID you would like to edit: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        TeachingRequirement existingReq = requirementsManager.getRequirement(id);

        if (existingReq == null) {
            System.out.println("Requirement not found.");
            return;
        }
        System.out.println("Enter mew course name: ");
        String courseName = scanner.nextLine().trim();

        System.out.println("Enter new skills needed: ");
        String skillsNeeded = scanner.nextLine().trim();

        System.out.println("Enter new hours: ");
        int hours = Integer.parseInt(scanner.nextLine().trim());

        TeachingRequirement updateReq = new TeachingRequirement(id, courseName, skillsNeeded, hours);

        requirementsManager.updatedRequirement(id, updatedReq);

        System.out.println("Requirement updated successfully");
    }

    private void optionAddTeacher() {
        System.out.println("Enter teacher name: ");
        String name = scanner.nextLine().trim();

        System.out.println("Enter teacher skills: ");
        String skills = scanner.nextLine().trim();

        System.out.println("Enter teaacher teaching status: ");
        String trainingStatus = scanner.nextLine().trim();

        Teacher updateTeacher = new Teacher(id, name, skills, trainingStatus);

        // Incomplete code here, just put stuff that TeacherManager needs
        boolean success = teacherManager.addTeacher(newTeacher);
        if (!success) {
            System.out.println("Teacher ID already exists in the system. Failed to add teacher.");
        }
        else {
            // Success message feedback to user
            System.out.println("New teacher added successfully.");
        }
    }

    private void optionUpdateTeacher(){
        System.out.println("Enter the ID of the teacher profile you would like to UPDATE: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Teacher existingTeacher = teacherManager.getTeacher(id);
        if (existingTeacher == null) {
            System.out.println("Teacher profile not found.Update not successful.");
            return;
        }
        System.out.println("Enter new name: ");
        String name = scanner.nextLine().trim();

        System.out.println("Enter new skill: ");
        String skills = scanner.nextLine().trim();

        System.out.println("Enter new training status: ");
        String trainingStatus = scanner.nextLine().trim();

        Teacher updatedTeacher = new Teacher(id, name, skills, trainingStatus);
        boolean success = teacherManager.updateTeacher(id, updatedTeacher);
        if (success) {
            System.out.println("Teacher profile updated successfully.");
        }
        else {
            System.out.println("Teacher profile not found. Failed to update teacher profile.")
        }
    }



    private void optionAssignTeacher() {
        System.out.println("Enter Requirement ID: ");
        int reqId = Integer.parseInt(scanner.nextLine().trim());

        TeachingRequirement req = requirementsManager.getRequirement(reqId);
        if (req == null) {
            System.out.println("Requirement ID not found.");
            return;
        }
        System.out.println("Enter Teacher ID: ");
        int teacherId = Integer.parseInt(scanner.nextLine().trim());

        Teacher teacher = teacherManager.getTeacher(teacherId);
        if (teacher == null) {
            System.out.println("Teacher not found.");
            return;
        }
        requirementsManager.assignTeacher(reqId, teacher);

        System.out.println("Teacher assigned successfully.");
        System.out.println(requirementsManager.getRequirement(reqId));
    }

    private void viewTeacherProfile() {
        System.out.println("Enter the ID of the teacher profile you would like to view.");
        String  teacherID = scanner.nextLine().trim();

        //Assuming this code will work once teacher manager class is completed
        int id = Integer.parseInt(teacherID);
        Teacher teacher = teacherManager.getTeacher(id);

        if (teacher == null) {
            System.out.println("Teacher not found.");
            }
        else {
            System.out.println(teacher);
        }
    }
}