//getName() returns name 

//setName(String name) sets name 

//getSkills() returns skills 

//setSkills(String skills) sets skills 

//getTrainingStatus() returns training status 

//setTrainingStatus(String status) sets training status 

//getId() returns the teacher's id 

//toString() returns a readable summary 
// all my initialized parameters name, skills, training_status, and id

public class Teacher {
    private static int indexid=1;
    private int id;
    private String name;
    private String skills;
    private String training_status;

    public Teacher(String name, String skills, String training_status){
        this.id= indexid++;
        this.name= name;
        this.training_status= training_status;
        this.skills= skills;

    }

}
