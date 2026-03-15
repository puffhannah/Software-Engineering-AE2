package Utils;

import java.util.ArrayList;
import java.util.List;

/**
*Utils.RequirementsManager class consists:
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
    public RequirementsManager() {
        requirements = new ArrayList<>();
    }
    public void addRequirement(TeachingRequirement req) {
        if (req == null){
            return;
        }
        if(getRequirement(req.getId()) ==null){
            requirements.add(req);
        }
    }
    public TeachingRequirement addRequirement(String courseName, String skillsNeeded, int hours) {
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
        TeachingRequirement existing = getRequirement(id);
        if (existing != null){
            existing.setCourseName(updated.getCourseName());
            existing.setSkillsNeeded(updated.getSkillsNeeded());
            existing.setHours(updated.getHours());
        }
    }
    public void assignTeacher(int reqId, Teacher teacher){
        TeachingRequirement requirement = getRequirement(reqId);
        if(requirement!=null){
            requirement.setAssignedTeacher(teacher);
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