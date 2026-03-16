import Controllers.TeacherManager;
import Controllers.RequirementsManager;
import View.CommandLineInterface;



public class Main { public static void main(String[] args) {
    TeacherManager tm = TeacherManager.getInstance();
    // The TeacherManger loads the csv (teachers.csv) first 

    //need to link the RequireManger needs to teacher list 
    //help assign teacher when loading csv (requirement.csv)
    RequirementsManager rm = new RequirementsManager(tm.getAllTeachers());

    //CLI
    CommandLineInterface cli = new CommandLineInterface(rm, tm);
    cli.start();
}
    
}
