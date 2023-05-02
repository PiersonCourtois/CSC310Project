package application;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainTest {
  ////////////////////////////////////////////////////////////////////
  // The main method initializes the data. If you want to test, I   //
  // added a section that shows where you can do that.              //
  ////////////////////////////////////////////////////////////////////
  public static void main(String[] args) {
    ArrayList<AssignmentObject> assignList = new ArrayList<AssignmentObject>();
    //CreateData data = new CreateData(); 
    Scanner input = new Scanner(System.in);

    //data.makeFile(input, null); // Create data file
    MainTest mainTest = new MainTest(); // I have to do this because the program won't work otherwise. Too bad!
    assignList = mainTest.initData(); // Make data into AssignmentObjects and add to this list

    /*        Everything is now setup after this, and the objects can be used after here for testing.            */

    

    
    input.close();
    //data.updateData(assignList);
    return;
  }


  public AssignmentObject addAssignment(String[] assignData) {
    // Creates an assignment object from a String array that contains the details of that assignment
    // String array is as follows: [ID, Name, Due Date (MM/DD/YYYY), Course, Points, Weighted points, Importance]
    AssignmentObject assign = new AssignmentObject();
    assign.setID(assignData[0]);
    assign.setName(assignData[1]);
    assign.setDueDate(assignData[2]);
    assign.setCourse(assignData[3]);
    assign.setPoints(Integer.parseInt(assignData[4]));
    if(assignData[6].equals("true"))
      assign.setImportance(true);
    else
      assign.setImportance(false);
    assign.setWeightedPoints(0);
    return assign;
  }



  public ArrayList<AssignmentObject> initData() {
    // This method creates the Java objects from the data.txt file
    // !Literally don't look at this and trust me it works!

    try {
      System.out.println("Loading...");
      File file = new File("data.txt"); // Open data file
      ArrayList<AssignmentObject> assigns = new ArrayList<AssignmentObject>();
      Scanner input = new Scanner(file);

      // Parse through each line of the data file
      while(input.hasNextLine()) {
        String line = input.nextLine();

        // New assignment is found
        if(line.contains("new")) {
          String[] temp = new String[7]; // Each index of the array contains one data field of an assignment
          for(int i = 0; i < 7; i++) { // Load all data contents into array
            temp[i] = input.nextLine();
          }
          assigns.add(addAssignment(temp));
        }
      }
    input.close();
    return assigns;
    } 
    catch(FileNotFoundException e) {
      System.out.println("Error occured while attempting to read data");
      return null;
    }
  }


  public void sortAssignments(ArrayList<AssignmentObject> assignList) {
    // This method prints the sorted assignment list by weighted points
    for(int i = 0; i < assignList.size(); i++) { // dumb bubble sort
    	for(int j = 1; j < assignList.size(); j++) {
    		if((assignList.get(i).getWeightedPoints()) < (assignList.get(j).getWeightedPoints())) { // if j greater than i
    			AssignmentObject tempor = assignList.get(j); // store j in temp
    			assignList.set(j, (assignList.get(i))); // replace j with i
    			assignList.set(i, tempor); // replace i with temp
    			
    		}
    	}
    }
  }
}
