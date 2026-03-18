package Controllers;

import Utils.FileHandler;
import Models.Teacher;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all teacher-related operations in the system.
 * <p>
 * This controller class handles:
 * <ul>
 *   <li>Loading teacher data from persistent storage</li>
 *   <li>Adding new teachers</li>
 *   <li>Retrieving individual or all teachers</li>
 *   <li>Updating existing teacher details</li>
 *   <li>Saving changes back to storage</li>
 * </ul>
 * It follows the Singleton design pattern to ensure a single point of control
 * throughout the application.
 */
public class TeacherManager {
    // Singleton Pattern (only one instance + need global access point)
    private static TeacherManager inst;

    private static final String TEACHER_FILE = "data/teachers.csv";
    private List<Teacher> teachers;

    /**
     * Private constructor to prevent direct instantiation.
     * Loads existing teacher data from the CSV file; if loading fails,
     * initializes an empty list.
     */
    private TeacherManager() {
        teachers = FileHandler.loadTeachers(TEACHER_FILE);
        if (teachers == null) {
            teachers = new ArrayList<>();
        }
    }

    /**
     * Returns the singleton instance of TeacherManager.
     * Creates the instance on the first call.
     *
     * @return the single TeacherManager instance
     */
    public static TeacherManager getInstance() {
        if (inst == null) {
            inst = new TeacherManager();
        }
        return inst;
    }

    /**
     * Adds a new teacher to the system.
     * Performs validation to prevent null teachers and duplicate IDs.
     * If successful, the updated teacher list is saved to the CSV file.
     *
     * @param teacher the Teacher object to add
     * @return true if the teacher was added successfully, false otherwise
     */
    public boolean addTeacher(Teacher teacher) {
        if (teacher == null) {
            return false;
        }

        for (Teacher t : teachers) {
            if (t.getId() == teacher.getId()) {
                return false;
            }
        }
        teachers.add(teacher);

        FileHandler.saveTeachers(TEACHER_FILE, teachers);
        return true;
    }

    /**
     * Returns a list of all teachers currently in the system.
     * If the list is empty or null, a message is printed to the console.
     *
     * @return the list of teachers (may be empty)
     */
    public List<Teacher> getAllTeachers() {
        if (teachers == null || teachers.isEmpty()) {
            System.out.println("No teacher profile is found in the system."); // Handles edge case
        }
        return teachers;
    }


    /**
     * Retrieves a teacher by their unique ID.
     * If no teacher with the given ID exists, a message is printed and null is returned.
     *
     * @param id the teacher ID to search for
     * @return the Teacher object with the matching ID, or null if not found
     */
    public Teacher getTeacher(int id) {
        for (Teacher t : teachers) {
            if (t.getId() == id) {
                return t;
            }
        }
        System.out.println("Unable to find teacher with ID: " + id);
        return null;
    }

    /**
     * Updates the details of an existing teacher.
     * The teacher to update is identified by the provided ID.
     * Only the name, skills, and training status are updated from the
     * {@code updated} parameter. After updating, the entire teacher list
     * is saved to the CSV file.
     *
     * @param id      the ID of the teacher to update
     * @param updated a Teacher object containing the new values
     * @return true if the teacher was found and updated, false otherwise
     */
    public boolean updateTeacher(int id, Teacher updated) {
        for (int i=0; i < teachers.size(); i++) {
            if (teachers.get(i).getId() == id) {
                Teacher teacherToBeUpdated = teachers.get(i);
                teacherToBeUpdated.setName(updated.getName());
                teacherToBeUpdated.setSkills(updated.getSkills());
                teacherToBeUpdated.setTrainingStatus(updated.getTrainingStatus());
                FileHandler.saveTeachers(TEACHER_FILE, teachers);
                
                return true;
            }
        }
        return false;
    }
}
