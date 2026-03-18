package Entitystore;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Entities.Teacher;
import Entities.TeachingRequirement;

/**
 * Handles loading and saving of teacher and teaching requirement data to CSV files.
 * <p>
 * File formats:
 * <ul>
 *   <li>Teachers CSV: {@code id,name,skills,trainingStatus}</li>
 *   <li>Requirements CSV: {@code id,courseName,skillsNeeded,hours,assignedTeacherId}</li>
 * </ul>
 * The {@code assignedTeacherId} field is {@code -1} if no teacher is assigned.
 */
public class FileHandler {

    // ==================== LOADING METHODS ====================

    /**
     * Reads teachers from a CSV file and returns them as a list.
     * The file format is expected to be: {@code id,name,skills,trainingStatus}.
     * Lines starting with '#' are treated as comments and ignored.
     * If the file does not exist, an empty list is returned and a message is printed.
     *
     * @param filepath the path to the teachers CSV file
     * @return a list of Teacher objects loaded from the file, or an empty list if the file is missing or unreadable
     */
    public static List<Teacher> loadTeachers(String filepath) {
        List<Teacher> teachers = new ArrayList<>();
        File file = new File(filepath);

        //if the file doesn't exist yet, return empty list
        if (!file.exists()) {
            System.out.println("No existing teacher data found. Starting fresh.");
            return teachers;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(",", 4);
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String skills = parts[2].trim();
                    String trainingStatus = parts[3].trim();
                    //use of the constructor that takes an existing id
                    Teacher teacher = new Teacher(id, name, skills, trainingStatus);
                    teachers.add(teacher);
                }
            }
            System.out.println("Loaded teachers from file.");
        } catch (IOException e) {
            System.out.println("Error reading teachers file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing teacher data: " + e.getMessage());
        }

        return teachers;
    }

    /**
     * Reads teaching requirements from a CSV file and returns them as a list.
     * The file format is expected to be: {@code id,courseName,skillsNeeded,hours,assignedTeacherId}.
     * Lines starting with '#' are treated as comments and ignored.
     * If a valid teacher ID is present, the corresponding Teacher object is linked using the provided teacher list.
     * If the file does not exist, an empty list is returned and a message is printed.
     *
     * @param filepath the path to the requirements CSV file
     * @param teachers the list of all teachers (used to resolve assigned teacher references)
     * @return a list of TeachingRequirement objects loaded from the file, or an empty list if the file is missing or unreadable
     */
    public List<TeachingRequirement> loadRequirements(String filepath, List<Teacher> teachers) {
        List<TeachingRequirement> requirements = new ArrayList<>();
        File file = new File(filepath);

        //if the file doesn't exist yet, return empty list
        if (!file.exists()) {
            System.out.println("No existing requirements data found. Starting fresh.");
            return requirements;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(",", 5);
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0].trim());
                    String courseName = parts[1].trim();
                    String skillsNeeded = parts[2].trim();
                    int hours = Integer.parseInt(parts[3].trim());
                    int assignedTeacherId = Integer.parseInt(parts[4].trim());

                    //use of the constructor that takes an existing id
                    TeachingRequirement req = new TeachingRequirement(id, courseName, skillsNeeded, hours);

                    //link of the assigned teacher if there is one
                    if (assignedTeacherId != -1) {
                        Teacher assignedTeacher = findTeacherById(teachers, assignedTeacherId);
                        if (assignedTeacher != null) {
                            req.setAssignedTeacher(assignedTeacher);
                        }
                    }

                    requirements.add(req);
                }
            }
            System.out.println("Loaded requirements from file.");
        } catch (IOException e) {
            System.out.println("Error reading requirements file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing requirement data: " + e.getMessage());
        }

        return requirements;
    }

    // ==================== SAVING METHODS ====================

    /**
     * Writes all teachers to a CSV file.
     * The file format is: {@code id,name,skills,trainingStatus}.
     * A header comment line is written first for readability.
     *
     * @param filepath the path to the teachers CSV file
     * @param teachers the list of Teacher objects to save
     */
    public static void saveTeachers(String filepath, List<Teacher> teachers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            //write a header comment so the file is human-readable
            writer.write("# id,name,skills,trainingStatus");
            writer.newLine();

            for (Teacher teacher : teachers) {
                String line = teacher.getId() + ","
                        + teacher.getName() + ","
                        + teacher.getSkills() + ","
                        + teacher.getTrainingStatus();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Saved " + teachers.size() + " teachers to file.");
        } catch (IOException e) {
            System.out.println("Error saving teachers file: " + e.getMessage());
        }
    }

    /**
     * Writes all teaching requirements to a CSV file.
     * The file format is: {@code id,courseName,skillsNeeded,hours,assignedTeacherId}.
     * A header comment line is written first for readability.
     * If a teacher is assigned, the teacher's ID is stored; otherwise, {@code -1} is stored.
     *
     * @param filepath     the path to the requirements CSV file
     * @param requirements the list of TeachingRequirement objects to save
     */
    public void saveRequirements(String filepath, List<TeachingRequirement> requirements) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            writer.write("# id,courseName,skillsNeeded,hours,assignedTeacherId");
            writer.newLine();

            for (TeachingRequirement req : requirements) {
                // -1 if no teacher is assigned
                int teacherId = (req.getAssignedTeacher() != null)
                        ? req.getAssignedTeacher().getId()
                        : -1;

                String line = req.getId() + ","
                        + req.getCourseName() + ","
                        + req.getSkillsNeeded() + ","
                        + req.getHours() + ","
                        + teacherId;
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Saved " + requirements.size() + " requirements to file.");
        } catch (IOException e) {
            System.out.println("Error saving requirements file: " + e.getMessage());
        }
    }

    // ==================== HELPER METHODS ====================

    /**
     * Finds a teacher by its ID from a list of teachers.
     *
     * @param teachers the list of Teacher objects to search
     * @param id       the ID to look for
     * @return the Teacher with the matching ID, or {@code null} if none found
     */
    private Teacher findTeacherById(List<Teacher> teachers, int id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId() == id) {
                return teacher;
            }
        }
        return null;
    }
}
