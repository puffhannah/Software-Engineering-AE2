package Models;
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
    private static int assignedid=1;
    private int id;
    private String name;
    private String skills;
    private String training_status;
//self. is same as this.
    public Teacher(String name, String skills, String training_status){
        this.id= assignedid++;
        this.name= name;
        this.training_status= training_status;
        this.skills= skills;

    
    }
    public Teacher(int id, String name, String skills, String trainingstatus){
        this.id= id;
        if (id>= assignedid) assignedid = id +1; // so each teacher has its own ID 
        this.name=name;
        this.skills=skills;
        this.training_status=trainingstatus;

    }

    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public int getId(){return id;}
    public String getTrainingStatus(){return training_status;}
    public void setTrainingStatus(String status){this.training_status= status;}
    public String getSkills(){return skills;}
    public void setSkills(String skills){this.skills=skills;}
    
    @Override
    public String toString(){
        return String.format("[ID %d]%s,| Name %s| Skills %s| Training Status %s",id,name,skills,training_status);
    }


}
