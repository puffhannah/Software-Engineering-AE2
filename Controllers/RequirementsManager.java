package Controllers;

import java.util.ArrayList;
import java.util.List;

import Models.Teacher;
import Models.TeachingRequirement;
import Utils.FileHandler;

/**
 * Manages teaching requirements in the system.
 * <p>
 * This controller handles:
 * <ul>
 *   <li>Loading requirements from persistent storage</li>
 *   <li>Adding new requirements (with validation)</li>
 *   <li>Retrieving individual or all requirements</li>
 *   <li>Updating existing requirement details</li>
 *   <li>Assigning a teacher to a requirement</li>
 *   <li>Saving changes back to storage</li>
 * </ul>
 * <p>
 * It relies on the {@link TeachingRequirement} class providing the necessary
 * getters and setters (getId, getCourseName, getSkillsNeeded, getHours,
 * setCourseName, setSkillsNeeded, setHours, setAssignedTeacher).
 */
public class RequirementsManager {
    private List<TeachingRequirement> requirements;
    private final FileHandler fileHandler;
    private static final String REQUIREMENT_FILE = "data/requirements.csv";

    /**
     * Constructs a RequirementsManager and loads existing requirements from the CSV file.
     * Teacher data is obtained from the {@link TeacherManager} singleton to properly
     * re-link assigned teachers when loading.
     */
    public RequirementsManager() {
        fileHandler = new FileHandler();
        TeacherManager teacherManager = TeacherManager.getInstance();
        List<Teacher> teachers = teacherManager.getAllTeachers();
        requirements = fileHandler.loadRequirements(REQUIREMENT_FILE, teachers);
        if (requirements == null){
            requirements = new ArrayList<>();
        }
    }

    /**
     * Adds a new teaching requirement to the system.
     * The requirement is validated (non‑null, non‑blank course name and skills,
     * positive hours) and checked for duplicate ID before adding.
     * If successful, the updated list is saved to the CSV file.
     *
     * @param req the TeachingRequirement object to add
     */
    public void addRequirement(TeachingRequirement req) {
        if (req ==null){
            return;
        }
        if (req.getCourseName() ==null || req.getCourseName().trim().isEmpty()) {
            return;
        }
        if (req.getSkillsNeeded() ==null || req.getSkillsNeeded().trim().isEmpty()) {
            return;
        }
        if (req.getHours() <= 0) {
            return;
        }
        if(getRequirement(req.getId()) ==null){
            requirements.add(req);
            fileHandler.saveRequirements(REQUIREMENT_FILE, requirements);
        }
    }

    /**
     * Creates and adds a new teaching requirement from individual fields.
     * Validates input, generates a new unique ID, creates a TeachingRequirement object,
     * and delegates to {@link #addRequirement(TeachingRequirement)}.
     *
     * @param courseName   the name of the course
     * @param skillsNeeded the required skills (as a descriptive string)
     * @param hours        the total teaching hours required (must be positive)
     * @return the newly created TeachingRequirement object, or {@code null} if validation fails
     */
    public TeachingRequirement addRequirement(String courseName, String skillsNeeded, int hours) {
        if (courseName ==null || courseName.trim().isEmpty()){
            return null;
        }
        if (skillsNeeded ==null || skillsNeeded.trim().isEmpty()){
            return null;
        }
        if (hours <= 0){
            return null;
        }
        int newId = getNextRequirementId();
        TeachingRequirement req = new TeachingRequirement(newId, courseName, skillsNeeded, hours);
        addRequirement(req);
        return req;
    }

    /**
     * Returns a copy of the list of all teaching requirements.
     *
     * @return a new ArrayList containing all requirements
     */
    public List<TeachingRequirement> getAllRequirements(){
        return new ArrayList<>(requirements);
    }

    /**
     * Retrieves a teaching requirement by its unique ID.
     *
     * @param id the requirement ID to search for
     * @return the TeachingRequirement with the matching ID, or {@code null} if not found
     */
    public TeachingRequirement getRequirement(int id){
        for(TeachingRequirement req : requirements){
            if (req.getId()==id){
                return req;
            }
        }
        return null;
    }

    /**
     * Updates an existing teaching requirement with new details.
     * The requirement to update is identified by its ID. The new values are taken
     * from the {@code updated} object after validation. If the update is successful,
     * the full list is saved to the CSV file.
     *
     * @param id      the ID of the requirement to update
     * @param updated a TeachingRequirement object containing the new values
     */
    public void updateRequirement(int id, TeachingRequirement updated){
        if (updated == null){
            return;
        }
        if (updated.getCourseName() ==null || updated.getCourseName().trim().isEmpty()){
            return;
        }
        if (updated.getSkillsNeeded() ==null || updated.getSkillsNeeded().trim().isEmpty()){
            return;
        }
        if (updated.getHours() <= 0){
            return;
        }
        TeachingRequirement existing = getRequirement(id);
        if (existing != null){
            existing.setCourseName(updated.getCourseName());
            existing.setSkillsNeeded(updated.getSkillsNeeded());
            existing.setHours(updated.getHours());
            fileHandler.saveRequirements(REQUIREMENT_FILE, requirements);
        }
    }

    /**
     * Assigns a teacher to a teaching requirement.
     * Both the requirement (by ID) and the teacher must exist (non‑null).
     * After assignment, the updated list is saved to the CSV file.
     *
     * @param reqId   the ID of the requirement
     * @param teacher the Teacher to assign (must not be {@code null})
     */
    public void assignTeacher(int reqId, Teacher teacher){
       if (teacher == null){
            return;
       }
        TeachingRequirement requirement = getRequirement(reqId);
        if(requirement!=null){
            requirement.setAssignedTeacher(teacher);
            fileHandler.saveRequirements(REQUIREMENT_FILE, requirements);
        }
    }


    /**
     * Calculates the next available ID for a new requirement by finding the current
     * maximum ID and incrementing it.
     *
     * @return the next unused requirement ID
     */
    private int getNextRequirementId(){
        int maxId =0;
        for(TeachingRequirement req :requirements){
            if(req.getId() >maxId){
                maxId= req.getId();
            }
        }
        return maxId +1;
    }
}
