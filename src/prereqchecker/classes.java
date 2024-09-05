package prereqchecker;

import java.util.*;

public class classes {
    // Instance Variables
    private String classTitle;
    private ArrayList<String> needsToComplete = new ArrayList<String>();
    private ArrayList<Integer> needsToCompleteID = new ArrayList<Integer>();

    // Constructor - Initializes the class with a title and empty lists of prerequisites.
    public void Classes(String classTitle) {
        this.classTitle = classTitle;
        this.needsToComplete = new ArrayList<String>();
        this.needsToCompleteID = new ArrayList<Integer>();
    }

    // Override toString method to return the class title when the object is printed.
    public String toString() {
        
        return classTitle;
    }
    

    // Getter Method- Returns the class title.
    public String getClassName() {
        return classTitle;
    }
    

    // Getter Method -  Returns the list of prerequisite course titles.
    public ArrayList<String> getPreReqs() {
        return this.needsToComplete;
    }

    // Getter Method - Returns the list of prerequisite course IDs.
    public ArrayList<Integer> getPreReqID() {
        return this.needsToCompleteID;
        
    }

     // Setter Method -  Adds a course name to the list of prerequisites
    public void addCourseName(String className) {
        this.needsToComplete.add(className);
    }


    // Setter Method - Adds a prerequisite course title to the list.
    public void setTakePrePreq(String preReq) {
        this.needsToComplete.add(preReq);
    }

    // Setter Method - Adds a prerequisite course ID to the list
    public void setTakePreReqID(int preReq) {
        this.needsToCompleteID.add(preReq);

    
    }
}