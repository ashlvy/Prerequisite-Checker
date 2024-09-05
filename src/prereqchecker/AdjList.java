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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then
 * listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {

        if (args.length < 2) {
            StdOut.println(
                    "Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

        // WRITE YOUR CODE HERE
         // Set input and output files based on command-line arguments
         StdIn.setFile(args[0]);
         StdOut.setFile(args[1]);
 
         // Create a list of classes by reading input file
         ArrayList<classes> courses = createCourseList();
 
         // Print the adjacency list to the output file
         printAdjacencyList(courses);
     }
 
     // Read input file and create a list of classes
   public  static ArrayList<classes> createCourseList() {
         int courseNum = StdIn.readInt();
         StdIn.readLine(); // consume the newline
 
         ArrayList<classes> courseList = new ArrayList<>();
 
         // Create each course and add it to the list
         for (int i = 0; i < courseNum; i++) {
             courseList.add(createCourse());
         }
 
         // Read prerequisites and update the course list
         readPrerequisites(courseList);
 
         return courseList;
     }
 
     // Create a course with its title
     private static classes createCourse() {
         String courseTitle = StdIn.readLine();
         classes courseID = new classes();
         courseID.Classes(courseTitle);
         return courseID;
     }
 
     // Read prerequisites from the input file and update the course list
     private static void readPrerequisites(ArrayList<classes> courseList) {
         int courseNum = StdIn.readInt();
         StdIn.readLine(); // consume the newline
 
         // Read each line containing prerequisites
         while (!StdIn.isEmpty()) {
             String course = StdIn.readString();
             String req = StdIn.readString();
 
             // Update the prerequisite for the corresponding course
             for (int i = 0; i < courseList.size(); i++) {
                 if (courseList.get(i).getClassName().equals(course)) {
                     courseList.get(i).setTakePrePreq(req);
                     break;
                 }
             }
         }
     }
 
     // Print the adjacency list to the output file
     private static void printAdjacencyList(ArrayList<classes> courses) {
         for (int i = 0; i < courses.size(); i++) {
             classes course = courses.get(i);
             StdOut.print(course.getClassName() + " "); // Getting  the actual name of the course
 
             ArrayList<String> prereqs = course.getPreReqs();
             for (int j = 0; j < prereqs.size(); j++) {
                 StdOut.print(prereqs.get(j) + " ");
             }
 
             StdOut.println(); // Prints the spac
         }
     }


    }