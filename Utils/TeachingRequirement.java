package Utils;//getCourseName() returns course name
//setCourseName(String name) sets course name
//getSkillsNeeded() returns required skills
//setSkillsNeeded(String skills) sets required skills
//getHours() returns hours
//setHours(int hours) sets hours
//getAssignedTeacher() returns the assigned teacher (or null)
//setAssignedTeacher(Teacher teacher) assigns a teacher
//getId() returns the requirement's id
//toString() returns a readable summary

public class TeachingRequirement {
    private int id;
    private String courseName;
    private String skillsNeeded;
    private int hours;
    private Teacher assignedTeacher;

    public TeachingRequirement(int id, String courseName, String skillsNeeded, int hours) {
        this.id = id;
        this.courseName = courseName;
        this.skillsNeeded = skillsNeeded;
        this.hours = hours;
        this.assignedTeacher = null;
    }

    public int getId() {
        return id;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String name) {
        this.courseName = name;
    }
    public String getSkillsNeeded() {
        return skillsNeeded;
    }
    public void setSkillsNeeded(String needed) {
        this.skillsNeeded = skills;
    }
    public int getHours() {
        return hours;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }
    public Teacher getAssignedTeacher() {
        return assignedTeacher = teacher;
    }
    @Override
    public String toString(){
        String teacherInfo = (assignedTeacher ==null)
                ? "None"
                : assignedTeacher.getName();

        return String.format("[Req %d] %s | Skills: %s | Hours: %d | Teacher: %s",
                id, courseName, skillsNeeded, hours, teacherInfo);

    }

}