package prereqchecker;

import java.util.ArrayList;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
            // WRITE YOUR CODE HERE
        StdIn.setFile(args[0]);
        ArrayList<classes> totalClasses = AdjList.createCourseList();
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);
        boolean yesOrNo = canNowTake("adjlist.in", "validprereq.in", totalClasses);
        if(yesOrNo == false) { //checks if possible to take specificed coureses
            StdOut.println("NO");
        } else {
            StdOut.println("YES");
        }

    }
//dfs that checks for self loops
private static boolean LoopChecker(String course, int prereq, boolean[] check, ArrayList<classes> totalClasses) {
    if (check[prereq]) {
        return false; // Loop detected
    }

    check[prereq] = true;

    for (int preReqID : getPreReqs(prereq, totalClasses)) {
        if (!LoopChecker(course, preReqID, check, totalClasses)) {
            return false; // Loop detected in a deeper lvl of the dfs
        }
    }


    return true; // No loop detected
    }
//get prereqs for a course ID
    private static ArrayList<Integer> getPreReqs(int courseIndex, ArrayList<classes> totalClasses) {
        return totalClasses.get(courseIndex).getPreReqID();
    }
//check if its possible to take those courses   w/o creating self loops
    private static boolean canNowTake(String course1, String course2, ArrayList<classes> totalClasses) {
        boolean[] result = new boolean[totalClasses.size()];
        //iterate thru all the coureses
        for (int i = 0; i < totalClasses.size(); i++) {
            //checks if course 1 matches course 2
            classes currentCourse = totalClasses.get(i);
            if (isMatchingCourse(currentCourse, course1) || isMatchingCourse(currentCourse, course2)) {
                //check for self loops using the loop checker method
                if (!LoopChecker(currentCourse.getClassName(), i, result, totalClasses)) {
                    return false;
                }
            }
        }
        return true;
    }
//checks if two course names match each toher
    private static boolean isMatchingCourse(classes course, String courseName) {
        return course.getClassName().equals(courseName);
    }

}