package prereqchecker;
import java.util.*;

public class Eligible {
    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list input file> <eligible input file> <eligible output file>");
            return;
        }

        // Set input and output files based on command-line arguments
        StdIn.setFile(args[0]);
        StdOut.setFile(args[2]);

        // Step 1: Read adjacency list from file
        Map<String, List<String>> adjacencyList = readAdjacencyList();

        // Step 2: Read eligible courses from file
        StdIn.setFile(args[1]);
        ArrayList<String> completedCourses = readEligibleInput();
        StdIn.setFile(args[0]);

        // Step 3: Determine eligible courses using DFS
        ArrayList<String> eligibleCourses = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        ArrayList<String> fullCompletedCourses = new ArrayList<>();
        for(String course : completedCourses) {
            dfsTaken(course, adjacencyList, fullCompletedCourses, visited);
        }
        completedCourses.addAll(fullCompletedCourses);

        for (String course : adjacencyList.keySet()) {
            if (!visited.contains(course)) {
                dfs(course, adjacencyList, completedCourses, eligibleCourses, visited);
            }
        }

        // Step 4: Output eligible courses to file in the specified format
        outputEligibleCourses(eligibleCourses);
    }

    // Method to read adjacency list from file
    private static Map<String, List<String>> readAdjacencyList() {
        int numCourses = StdIn.readInt();
        StdIn.readLine();
        Map<String, List<String>> adjacencyList = new HashMap<>();
        String[] courseList = new String[numCourses];

        for (int i = 0; i < numCourses; i++) {
            String line = StdIn.readLine();
            String[] tokens = line.split("\\s+");
            String course = tokens[0];
            courseList[i] = course;
            List<String> prerequisites = new ArrayList<>();
            adjacencyList.put(course, prerequisites);
        }

        numCourses = StdIn.readInt();
        StdIn.readLine();

        for (int i = 0; i < numCourses; i++) {
            String line = StdIn.readLine();
            String[] tokens = line.split("\\s+");

            String course = tokens[0];
            List<String> prerequisites = adjacencyList.get(course);

            for (int j = 1; j < tokens.length; j++) {
                prerequisites.add(tokens[j]);
            }

            adjacencyList.put(course, prerequisites);
        }

        return adjacencyList;
    }

    // Method to read eligible input from file
    private static ArrayList<String> readEligibleInput() {
        int numCourses = StdIn.readInt();
        StdIn.readLine();
        ArrayList<String> completedCourses = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            String course = StdIn.readLine();
            completedCourses.add(course);
        }

        return completedCourses;
    }

  // DFS traversal to check prerequisites for each course
    private static void dfs(String course, Map<String, List<String>> adjacencyList, ArrayList<String> completedCourses, ArrayList<String> eligibleCourses, Set<String> visited) {
        if (!visited.contains(course)) {
            boolean isEligible = true;
            for (String requiredCourse : adjacencyList.get(course)) {
                if (!completedCourses.contains(requiredCourse)) {
                    isEligible = false;
                }
            }

            if (isEligible && !completedCourses.contains(course)) {
                eligibleCourses.add(course);
            }

            for (String dependentCourse : adjacencyList.get(course)) {
                if (!visited.contains(dependentCourse)) {
                    dfs(dependentCourse, adjacencyList, completedCourses, eligibleCourses, visited);
                }
            }
        }
        visited.add(course);
    }

    // Method to output eligible courses to file in the specified format
    private static void outputEligibleCourses(ArrayList<String> eligibleCourses) {
        for (String course : eligibleCourses) {
            StdOut.println(course);
        }
    }

    private static void dfsTaken(String course, Map<String, List<String>> adjacencyList, ArrayList<String> completedCourses, Set<String> visited) {
        if (!visited.contains(course)) {
            for (String prevCourse : adjacencyList.get(course)) {
                dfsTaken(prevCourse, adjacencyList, completedCourses, visited);
                completedCourses.add(prevCourse);
                visited.add(prevCourse);
            }
        }
    }
}