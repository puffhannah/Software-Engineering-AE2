package Controllers;

import Utils.FileHandler;
import Models.Teacher;
import java.util.ArrayList;
import java.util.List;

/* 

This class is a Control object in BCE.

1. Receive TeacherProfile request from CLI
2. Load TeacherID to Teacher class
3. Receive TeacherProfile object from Teacher
4. Display Teacherprofile to CLI

5. Receive TeacherProfileUpdate request from CLI
6. Send updated Data to Teacher class
7. Receive success status from Teacher
8. Display the updated teacher profile to CLI

 */ 

public class TeacherManager {
    // Singleton Pattern (only one instance + need global access point)
    private static TeacherManager inst;

    private List<Teacher> teachers;

    // Private Contructor
    private TeacherManager() {
        teachers = FileHandler.loadTeachers("data/teachers.csv");
        if (teachers == null) {
            teachers = new ArrayList<>();
        }
    }

    // Public Accessors here
    public static TeacherManager getInstance() {
        if (inst == null) {
            inst = new TeacherManager();
        }
        return inst;
    }

    // Add new teacher to the system
    public boolean addTeacher(Teacher teacher) {
        // Handle edge case of null teacher
        if (teacher == null) {
            return false;
        }
        // Handle edge case of duplicate IDs
        for (Teacher t : teachers) {
            if (t.getId() == teacher.getId()) {
                return false;
            }
        }
        teachers.add(teacher);
        // Save updated list to CSV
        FileHandler.saveTeachers("data/teachers.csv", teachers);
        return true;
    }
    
    // Return full list of teachers
    public List<Teacher> getAllTeachers() {
        if (teachers == null || teachers.isEmpty()) {
            System.out.println("No teacher profile is found in the system."); // Handles edge case
        }
        return teachers;
    }

    // Fetch specific teacher with Teacher's ID
    public Teacher getTeacher(int id) {
        for (Teacher t : teachers) {
            if (t.getId() == id) {
                return t;
            }
        }
        System.out.println("Unable to find teacher with ID: " + id); // Handles edge case
        return null; // for case when ID does not exist
    }

    // Update teacher details
    public boolean updateTeacher(int id, Teacher updated) {
        for (int i=0; i < teachers.size(); i++) {
            if (teachers.get(i).getId() == id) {
                Teacher teacherToBeUpdated = teachers.get(i);
                teacherToBeUpdated.setName(updated.getName());
                teacherToBeUpdated.setSkills(updated.getSkills());
                teacherToBeUpdated.setTrainingStatus(updated.getTrainingStatus());
                FileHandler.saveTeachers("teachers.csv", teachers);
                
                return true; // do not remove, stops the loop upon data updated
            }
        }
        return false;
    }
}
