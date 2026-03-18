package Models;

/**
 * Represents a teaching requirement, including course name, required skills,
 * total hours, and an optionally assigned teacher.
 */
public class TeachingRequirement {
    private final int id;
    private String courseName;
    private String skillsNeeded;
    private int hours;
    private Teacher assignedTeacher;


    /**
     * Constructs a teaching requirement with the given details.
     * Initially no teacher is assigned.
     *
     * @param id           the unique identifier for this requirement
     * @param courseName   the name of the course
     * @param skillsNeeded description of skills required to teach the course
     * @param hours        the total teaching hours required
     */
    public TeachingRequirement(int id, String courseName, String skillsNeeded, int hours) {
        this.id = id;
        this.courseName = courseName;
        this.skillsNeeded = skillsNeeded;
        this.hours = hours;
        this.assignedTeacher = null;
    }

    /**
     * getCourseName() returns course name
     * setCourseName(String name) sets course name
     * getSkillsNeeded() returns required skills
     * setSkillsNeeded(String skills) sets required skills
     * getHours() returns hours
     * setHours(int hours) sets hours
     * getAssignedTeacher() returns the assigned teacher (or null)
     * setAssignedTeacher(Teacher teacher) assigns a teacher
     * getId() returns the requirement's id
     * toString() returns a readable summary
     */
    public int getId() {return id;}
    public String getCourseName() {return courseName;}
    public void setCourseName(String name) {this.courseName = name;}
    public String getSkillsNeeded() {return skillsNeeded;}
    public void setSkillsNeeded(String skills) {this.skillsNeeded = skills;}
    public int getHours() {return hours;}
    public void setHours(int hours) {this.hours = hours;}
    public Teacher getAssignedTeacher() {return assignedTeacher;}
    public void setAssignedTeacher(Teacher teacher) {this.assignedTeacher = teacher;}

    @Override
    public String toString(){
        String teacherInfo = (assignedTeacher ==null)
                ? "None"
                : assignedTeacher.getName();

        return String.format("[Req %d] %s | Skills: %s | Hours: %d | Teacher: %s",
                id, courseName, skillsNeeded, hours, teacherInfo);

    }

}