package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Models.Teacher;
import Models.TeachingRequirement;

//file format for teachers: id,name,skills,trainingStatus
//file format for requirements: id,courseName,skillsNeeded,hours,assignedTeacherId
//(assignedTeacherId is -1 if no teacher is assigned)

public class FileHandler {

    //LOADING
    //reads teachers from a CSV file and returns them as a list.
    //each line has format: id,name,skills,trainingStatus

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

    //we need the teacher list so we can link assigned teachers to requirements.
    public static List<TeachingRequirement> loadRequirements(String filepath, List<Teacher> teachers) {
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

    //SAVING 
    //writes all teachers to a CSV file.
    //format: id,name,skills,trainingStatus
    
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

    //writes all teaching requirements to a CSV file.
    //format: id,courseName,skillsNeeded,hours,assignedTeacherId
     
    public static void saveRequirements(String filepath, List<TeachingRequirement> requirements) {
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

    //HELPER

    //find a teacher by their id from a list/ return null
    private Teacher findTeacherById(List<Teacher> teachers, int id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId() == id) {
                return teacher;
            }
        }
        return null;
    }
}
