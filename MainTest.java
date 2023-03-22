import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainTest {
  ////////////////////////////////////////////////////////////////////
  // The main method initializes the data then starts a terminal    // 
  // based interaction with the data. Because of this, there are a  //
  // lot of print lines and this whole thing looks pretty ugly. The //
  // only thing that really matters here is the initData function,  //
  // which turns the data.txt file into class and assignments       //
  // objects that can actually be used and manipulated. One thing   //
  // I definitely need to do is reupdate the data after it has been //
  // manipulated, but this small version doesn't manipulate any     //
  // data, just displays it for now.                                //
  ////////////////////////////////////////////////////////////////////
  public static void main(String[] args) {
    final String QUIT = "QUIT";
    final String BACK = "BACK";
    int assignmentSize;
    ArrayList<ClassObject> classes = new ArrayList<ClassObject>();
    CreateData data = new CreateData(); 
    String command = "";
    String assignCommand = "";

    Scanner input = new Scanner(System.in);
    data.makeFile(input);
    classes = initData();
    System.out.println();
    System.out.println("Classes: ");
    for(int i = 0; i < classes.size(); i++) {
      System.out.println(i + ": " + classes.get(i).getName());
    }
    System.out.println("Type in the corresponding number to view a class or QUIT to quit");
    command = input.nextLine();
    while(!command.equals(QUIT)) { // You will get an error if you type in something that is not a valid number. Too bad!
      assignmentSize = classes.get(Integer.parseInt(command)).assignmentList.size(); // This is long and looks really dumb but it works, too bad!
      System.out.println("Assignments found in " + classes.get(Integer.parseInt(command)).getName());
      
      for(int j = 0; j < assignmentSize; j++) { 
        System.out.println(j + ": " + classes.get(Integer.parseInt(command)).assignmentList.get(j).getName());
      }
      System.out.println("Type in the corresponding number to see details of that assignment or " + BACK + " to go back.");
      assignCommand = input.nextLine();
      while(!assignCommand.equals(BACK)) {
        System.out.println("Name: " + classes.get(Integer.parseInt(command)).assignmentList.get(Integer.parseInt(assignCommand)).getName());
        System.out.println("Due Date: " + classes.get(Integer.parseInt(command)).assignmentList.get(Integer.parseInt(assignCommand)).getDueDate());
        System.out.println("Days left: " + classes.get(Integer.parseInt(command)).assignmentList.get(Integer.parseInt(assignCommand)).getDaysLeft());
        System.out.println("Points: " + classes.get(Integer.parseInt(command)).assignmentList.get(Integer.parseInt(assignCommand)).getPoints());
        System.out.println("Type in the corresponding number to see details of that assignment or " + BACK + " to go back.");
        assignCommand = input.nextLine();
      }
      System.out.println("Classes: ");
      for(int i = 0; i < classes.size(); i++) {
      System.out.println(i + ": " + classes.get(i).getName());
      }
      System.out.println("Type in the corresponding number to view a class or QUIT to quit");
      command = input.nextLine();
    }
    input.close();
    return;
  }

  public static ArrayList<ClassObject> initData() {
    try {
      System.out.println("Loading...");
      boolean classExists = false; // This is used later to check if a class exists because I couldn't think of a better way, too bad!
      File file = new File("data.txt"); // Open data file
      ArrayList<ClassObject> classes = new ArrayList<ClassObject>();
      Scanner input = new Scanner(file);
      while(input.hasNextLine()) {
        classExists = false;
        String line = input.nextLine();
        if(line.contains("new")) {
          String[] temp = new String[5];
          for(int i = 0; i < 5; i++) { // Load all data contents into array
            temp[i] = input.nextLine();
          }
          if(classes.isEmpty()) { // If the class list is empty, create a new class object and add the assignment
            ClassObject newClass = new ClassObject();
              newClass.setName(temp[3]); // TODO: ask user if class is important
              newClass.addAssignment(temp);
              classes.add(newClass);
              classExists = true;
          }
          else { // If the class list is NOT empty, search for if class exists
            for(int i = 0; i < classes.size(); i++) { // Loop through each class, if class already exists then add to that class
              
              if(((classes.get(i)).getName()).equals(temp[3])) { 
                classes.get(i).addAssignment(temp);
                classExists = true; // Make classExists true if it exists
                break;
              }
            }
          }
          if(!classExists) { // If we looped through the list and the class doesn't exist, create class then add assignment
              ClassObject newClass = new ClassObject();
              newClass.setName(temp[3]); // TODO: ask user if class is important
              newClass.addAssignment(temp);
              classes.add(newClass);
          }
        }
      }
    input.close();
    return classes;
    } 
    catch(FileNotFoundException e) {
      System.out.println("Error occured while attempting to read data");
      return null;
    }
  }
}