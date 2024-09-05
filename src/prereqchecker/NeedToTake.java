package prereqchecker;

import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the courseat:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the courseat:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the courseat:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }
        //What are all the courses that you have NOT taken yet, which are required in order to take the target course? 
        //In other words, what direct and indirect prerequisites of the target course have you not taken yet?

	// WRITE YOUR CODE HERE
    // Initialize input and output files
    StdIn.setFile(args[0]);
    StdOut.setFile(args[2]);
    //initalize courselist
    ArrayList<classes> courseList = initializeCourseList();
    StdIn.setFile(args[1]);
    String wantedCourse = StdIn.readLine();
    // Initialize classesRequired and temporaryList
    ArrayList<String> classesRequired = new ArrayList<String>();
    ArrayList<String> temporaryList = new ArrayList<String>(); //temporaryList will become Finallist
    classes desiredCourse = null;

        // Find the target course
        desiredCourse = findCourseByName(courseList, wantedCourse);
    
        // Find classes taken and add to the list
        ArrayList<String> taken = classesAlreadyTaken(courseList);
        classesRequired = needToTake(taken, classesRequired, desiredCourse, courseList);
    
        // Add classes to list if not present
        for (int i = 0; i < classesRequired.size(); i++) {
            String course = classesRequired.get(i);
            if (!temporaryList.contains(course)) {
                temporaryList.add(course);
            }
        }
        
        displayCourseList(temporaryList);//display the final course list
    }
public static void displayCourseList(ArrayList<String> displayCourseList) {
        for (String course : displayCourseList) {
            StdOut.println(course);
        }
    }
    

    //this method recursively finds the courses that are needed to be taken
 public static ArrayList<String> needToTake(ArrayList<String> taken, ArrayList<String> needToTake, classes wantedCourse, ArrayList<classes> completedCourse) {
        if (!taken.contains(wantedCourse.getClassName())) {
            ArrayList<String> necessaryRequirements = wantedCourse.getPreReqs(); // Get the prerequisites for the current courseWanted
            int i = 0;
              // Iterate through the prerequisites
            while (i < necessaryRequirements.size()) {
                String tracker = necessaryRequirements.get(i);
                if (!taken.contains(tracker)) {
                    //found the course in completed curses
                    classes foundCourse = findCourseByName(completedCourse, tracker);
                    if (foundCourse != null) {
                        wantedCourse = foundCourse;
                        needToTake.add(tracker);
                        //recusrisvely find prereqs for the found courses
                        ArrayList<String> neededCourses = needToTake(taken, needToTake, wantedCourse, completedCourse);
                        needToTake.addAll(neededCourses);
                    }
                }
                i++;
            }
        }
        return needToTake;
    }
//Gets the list of ALREADY taken courses
    public static ArrayList<String> classesAlreadyTaken(ArrayList<classes> adjList) {
        int numberOfCourses = Integer.parseInt(StdIn.readLine());
        ArrayList<String> completedCourses = new ArrayList<String>();
    //reads completed courses
        for (int i = 0; i < numberOfCourses; i++) {
            String tracker = StdIn.readLine();
            completedCourses.add(tracker);
        }
    //iterates thru complted courses and their prereqs
        ArrayList<String> course = new ArrayList<String>();
        for (String name : completedCourses) {
            classes currentCourse = findCourseByName(adjList, name);
    //recursively finds prereqs
            ArrayList<String> needed = new ArrayList<String>();
            needed = classesAlreadyTaken(adjList, currentCourse, needed);
    //adds couresses and their prereqs to the list
            course.add(name);
            course.addAll(needed);
        }
    //adds additional couress from the list
        ArrayList<String> coursesCompleted = addsElementsForlist(new ArrayList<String>());
        
        for (String element : coursesCompleted) {
            if (!course.contains(element)) {
                course.add(element);
            }
        }
    
        return course;
    }
//finds a coures in the list by its name
private static classes findCourseByName(ArrayList<classes> adjList, String courseName) {
        int i = 0;
        while (i < adjList.size()) {
            classes course = adjList.get(i);
            if (course.getClassName().equals(courseName)) {
                return course;
            }
            i++;
        }
        return null;
    }
//finds prereqs for a given coures and adds to a list
public static ArrayList<String> classesAlreadyTaken(ArrayList<classes> courseList, classes currentCourse, ArrayList<String> needed) {
    String tracker; // Temporary course that eventually needs to be added to the final list
    classes wantedCourse; // the requested class

    if (!currentCourse.getPreReqs().isEmpty()) {
        needed.addAll(currentCourse.getPreReqs());
        ArrayList<String> neededList = currentCourse.getPreReqs();

        int i = 0;
        while (i < neededList.size()) {
            tracker = neededList.get(i); // iterates through prereqs
            wantedCourse = null;

            // find the course in the course list
            int j = 0;
            while (j < courseList.size() && (wantedCourse == null)) {
                if (courseList.get(j).getClassName().equals(tracker)) {
                    wantedCourse = courseList.get(j);
                }
                j++;
            }

            // recursively find the prereqs
            ArrayList<String> finalList = classesAlreadyTaken(courseList, wantedCourse, needed);
            needed.addAll(finalList);

            i++;
        }

    } else if (currentCourse.getPreReqs().isEmpty()) {
        // do nothing if no prereqs
    }

    return needed;
}
//initalises list of ocureses
public static ArrayList<classes> initializeCourseList() {
    int numberOfCourses = StdIn.readInt();
    String courseTitle;
    ArrayList<classes> courseList = new ArrayList<classes>();
    // Initialize courses
    for (int i = 0; i < numberOfCourses; i++) {
        courseTitle = StdIn.readLine();
        classes courseID = new classes();
        courseID.Classes(courseTitle);
        courseList.add(courseID);
    }
    // Read additional course information
    while (!StdIn.isEmpty()) {
        String course = StdIn.readString();
        String needs = StdIn.readString();

        // Update course information
        for (int i = 0; i < courseList.size(); i++) {
            classes currentCourse = courseList.get(i);
            if (currentCourse.getClassName().equals(course)) {
                currentCourse.setTakePrePreq(needs);
                break;
            }
        }
    }
    return courseList;
}
//always returns an empty list
public static ArrayList<String> addsElementsForlist(ArrayList<String> addedList) {
    ArrayList<String> replacementList = new ArrayList<String>(); 
    ArrayList<String> priorList = new ArrayList<String>();

    // Traverse through the first list
    for (int i = 0; i < priorList.size(); i++) {
        String course = priorList.get(i);

        // Check if the element is not already in the new list
        if (!replacementList.contains(course))
            replacementList.add(course);
    }

    return replacementList;
}

public static classes getwantedCourse(ArrayList<classes> completedCourses) {
    // Read a course name from the standard input
    String readCourse = StdIn.readLine();
    classes wantedCourse = null;
    int i = 0;    // Iterate through the list of completed courses
    while (i < completedCourses.size() && wantedCourse == null) {
        classes completed = completedCourses.get(i); // Get the current completed course
        if (completed.getClassName().equals(readCourse)) { // Check if the course name matches the read course name
            wantedCourse = completed;
        }
        i++;
    }

    return wantedCourse;
}
}