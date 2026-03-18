package Entities;

/**
 * Represents a teacher with name, skills, and training status.
 * Each teacher is assigned a unique ID automatically upon creation.
 */
public class Teacher {
    private static int assignedId = 1;
    private final int id;
    private String name;
    private String skills;
    private String trainingStatus;

    /**
     * Constructs a new Teacher with an auto-generated ID.
     *
     * @param name           the teacher's name
     * @param skills         the teacher's skills
     * @param trainingStatus the teacher's current training status e.g. Completed, In Progress.
     */
    public Teacher(String name, String skills, String trainingStatus){
        this.id = assignedId++;
        this.name = name;
        this.trainingStatus = trainingStatus;
        this.skills = skills;
    }

    /**
     * Constructs a Teacher with a predefined ID (e.g. when loading from a file).
     * Ensures that the static ID counter does not fall behind existing IDs.
     *
     * @param id             the teacher's existing ID
     * @param name           the teacher's name
     * @param skills         the teacher's skills
     * @param trainingStatus the teacher's training status
     */
    public Teacher(int id, String name, String skills, String trainingStatus){
        this.id = id;
        if (id>= assignedId) {
            assignedId = id + 1;
        }
        this.name = name;
        this.skills = skills;
        this.trainingStatus = trainingStatus;

    }
    /**
     * getName() returns name
     * setName(String name) sets name
     * getSkills() returns skills
     * setSkills(String skills) sets skills
     * getTrainingStatus() returns training status
     * setTrainingStatus(String status) sets training status
     * getId() returns the teacher's id
     * toString() returns a readable summary
     * all my initialized parameters name, skills, training_status, and id
     */
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public int getId(){return id;}
    public String getTrainingStatus(){return trainingStatus;}
    public void setTrainingStatus(String status){this.trainingStatus= status;}
    public String getSkills(){return skills;}
    public void setSkills(String skills){this.skills=skills;}
    
    @Override
    public String toString(){
        return String.format("[ID %s] | Name: %s | Skills: %s | Training Status: %s",id,name,skills,trainingStatus);
    }


}
