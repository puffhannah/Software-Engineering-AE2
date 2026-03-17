package Controllers;

import java.util.ArrayList;
import java.util.List;

import Models.Teacher;
import Models.TeachingRequirement;
import Utils.FileHandler;

/**
*Controllers.RequirementsManager class consists:
*-addRequirement(TeachingRequirement req): adds a new requirement to the list
*-getAllRequirements(): returns the full list
*-getRequirement(int id): returns one specific requirement
*-updateRequirement(int id, TeachingRequirement updated): edits an existing requirement
*-assignTeacher(int reqId, Teacher teacher): links a teacher to a requirement
 * I am relying on TeachingRequirement consisting the collowing methods:
 * getId(), getCourseName(), getSkillsNeeded(), getHours(), setCourseName(), setSkillsNeeded(), setHours(),
 * and setAssignedTeacher()
 */
public class RequirementsManager {
    private List<TeachingRequirement> requirements;
    private final FileHandler fileHandler;
    private static final String Requirement_File = "data/requirements.csv";
    public RequirementsManager() {
        fileHandler = new FileHandler();
        TeacherManager teacherManager = TeacherManager.getInstance();
        List<Teacher> teachers = teacherManager.getAllTeachers();
        requirements = fileHandler.loadRequirements(Requirement_File, teachers);
        if (requirements == null){
            requirements = new ArrayList<>();
        }
    }
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
            fileHandler.saveRequirements(Requirement_File, requirements);
        }
    }
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
    public List<TeachingRequirement> getAllRequirements(){
        return new ArrayList<>(requirements);
    }
    public TeachingRequirement getRequirement(int id){
        for(TeachingRequirement req : requirements){
            if (req.getId()==id){
                return req;
            }
        }
        return null;
    }
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
            fileHandler.saveRequirements(Requirement_File, requirements);
        }
    }
    public void assignTeacher(int reqId, Teacher teacher){
       if (teacher == null){
            return;
       }
        TeachingRequirement requirement = getRequirement(reqId);
        if(requirement!=null){
            requirement.setAssignedTeacher(teacher);
            fileHandler.saveRequirements(Requirement_File, requirements);
        }
    }
    //added this to find the next available Id.
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
