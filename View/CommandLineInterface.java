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

    //validation functions:
    //readPositiveInt: check if input is a required positive int
    //readOptionalPositiveInt: check if input is a positive int and optional e.g. to edit hours or just press enter for default
    //readNonEmptyLine: When a value is required and not optional
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
    private String readNonEmptyLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;}
            System.out.println("This field cannot be empty.");
        }
    }

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
        if (reqs ==null || reqs.isEmpty()){
            System.out.println("No teaching requirements found.");
            return;
        }
        System.out.println("*** Teaching Requirements ***");
        for (TeachingRequirement req : reqs) {
            System.out.println(req);
        }
    }

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
        //course name
        System.out.print("New course name [" + existing.getCourseName() + "]: ");
        String courseName = scanner.nextLine().trim();
        if (courseName.isEmpty()) {
            courseName= existing.getCourseName();
        }
        //skills
        System.out.print("New skills needed [" + existing.getSkillsNeeded() + "]: ");
        String skillsNeeded = scanner.nextLine().trim();
        if (skillsNeeded.isEmpty()) {
            skillsNeeded= existing.getSkillsNeeded();
        }
        //hours
        int hours= readOptionalPositiveInt("New hours [" + existing.getHours() + "]: ", existing.getHours());

        TeachingRequirement updated= new TeachingRequirement(id, courseName, skillsNeeded, hours);
        requirementsManager.updateRequirement(id, updated);
        System.out.println("Requirement updated successfully:");
        System.out.println(requirementsManager.getRequirement(id));
    }

    private void optionAddTeacher() {
        // (Done) Incomplete code here, just put stuff that TeacherManager needs
        // Edit: Added the logic to add new teacher
        System.out.println("*** Add Teacher ***");
        String name = readNonEmptyLine("Enter teacher name: ");
        String skills = readNonEmptyLine("Enter teacher skills: ");
        String trainingStatus = readNonEmptyLine("Enter training status: ");
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
        //add name here
        System.out.print("New name [" + existingTeacher.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()){
            name= existingTeacher.getName();
        }
        //add skills here
        System.out.print("New skills [" + existingTeacher.getSkills() + "]: ");
        String skills = scanner.nextLine().trim();
        if (skills.isEmpty()){
            skills= existingTeacher.getSkills();
        }
        //add training status here
        System.out.print("New training status [" + existingTeacher.getTrainingStatus() + "]: ");
        String trainingStatus = scanner.nextLine().trim();
        if (trainingStatus.isEmpty()){
            trainingStatus= existingTeacher.getTrainingStatus();
        }

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

    private void viewTeacherProfile() {
        System.out.println("*** View Teacher Profile ***");

        int id = readPositiveInt("Enter the ID of the teacher profile you would like to view: ");
        Teacher teacher = teacherManager.getTeacher(id);

//        System.out.println(teacher);

        if (teacher == null) {
            System.out.println("Teacher not found.");
            }
        else {
            System.out.println(teacher);
        }

    }


}



