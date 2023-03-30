import java.util.Scanner;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.*;
import java.util.ArrayList;

public class CreateData {
  ////////////////////////////////////////////////////////////////////
  // The purpose of this class is to create a data file that can be //
  // more easily read by Java. It converts and parses the calendar  //
  // file which will be read in MainTest to convert the data into   //
  // Java objects. Just trust that this works please, its ugly.     //
  ////////////////////////////////////////////////////////////////////

  public void makeFile(Scanner input) {
    try {
      downloadCurrentCalendar("https://moodle.quincy.edu/calendar/export_execute.php?userid=8995&authtoken=727c3a4982abd842425c0b9382a90969cf5a60e0&preset_what=all&preset_time=recentupcoming");
    } catch (IOException e) {
      System.out.println("Error getting new calendar");
      e.printStackTrace();
    }
    try {
      File data = new File("data.txt");
      if(data.createNewFile()) { // If file is created, load data into file
        System.out.println("No existing data detected");
        createData(input);
        return;
      }
      else { // Data file already exists, read the data
        System.out.println("Existing data found");
        replaceData(input);
        return;
      }
    } catch (IOException e) {
      System.out.println("Error reading or creating data.");
    }
  }

  private void createData(Scanner aPoints) {
    // This method parses and stores the data from the calendar file to a data file (data.txt)
    System.out.println("Creating data...");
    try {
      File file = new File("./icalexport.ics"); // Open calendar file
      Scanner input = new Scanner(file);
      while(input.hasNextLine()) {
        String test = input.nextLine(); // Get first line
        if(test.contains("UID")) {  // Each new assignment starts with a unique UID
          try {
            FileWriter myData = new FileWriter("data.txt", true); // Open data.txt to write
            myData.write("new\n"); // Starting each new assignment with "new" to indicate new assignment
            test = test.substring(4); // Make test just UID of assignment
            myData.write(test + "\n"); // Write assignment UID to file
            test = input.nextLine(); // Next Line is assignment name
            test = test.replace("SUMMARY:", ""); // Remove SUMMARY:
            test = test.replace(" is due", ""); // Remove is due
            test = test.replace(" closes", ""); // Remove closes
            String name = test;
            myData.write(test + "\n"); // Write assignment name to file
            while(!test.contains("DTEND")) // Going to end date
              test = input.nextLine();
            String year = test.substring(6, 10); // Parse end date in readable format
            String month = test.substring(10, 12);
            String day = test.substring(12, 14);
            myData.write(month + "/" + day + "/" + year + "\n"); // Write end date to file
            test = input.nextLine(); // Next line is course number
            String courseNum = test.substring(16, 19); // Parse course number
            test = test.substring(11, 14);
            myData.write(test + courseNum + "\n"); // Write course number
            System.out.println("Enter how many points " + name + " in class " + test + courseNum + " is worth:");
            String pointAmount = aPoints.nextLine();
            myData.write(pointAmount + "\n"); // Points the assignment is worth
            myData.write("0\n");                // Weighted points
            myData.write("false\n");            // Importance
            myData.close();
          } catch (IOException e) {
            System.out.println("Failed writing to file");
          }
        }
      }
      input.close();
    }
    catch(FileNotFoundException e) {
      System.out.println("File not found");
    }
    System.out.println("Data file created!");
    return;
  }


  public void updateData(ArrayList<AssignmentObject> assignList) {
    // This method the the opposite of initData kinda, it reads the assignment
    // objects that were previously created and creates a new data file. This
    // is so that if a user were to alter an assignment during the program, 
    // it will stay altered after closing and reopening. 
    try {
      FileWriter myData = new FileWriter("data.txt", false);
      for(int i = 0; i < assignList.size(); i++) {
        myData.write("new\n");
        myData.write(assignList.get(i).getID() + "\n");
        myData.write(assignList.get(i).getName() + "\n");
        myData.write(assignList.get(i).getDueDate() + "\n");
        myData.write(assignList.get(i).getCourse() + "\n");
        myData.write(assignList.get(i).getPoints() + "\n");
        myData.write(assignList.get(i).getWeightedPoints() + "\n");
        if(assignList.get(i).getImportance())
          myData.write("true\n");
        else
          myData.write("false\n");

      }
      myData.close();
    } 
    catch (IOException e) 
    {
      System.out.println("Failed updating file");
      e.printStackTrace();
    } 
  }

  void downloadCurrentCalendar(String calendarURL) throws IOException {
    try {
      
    URL url = new URL(calendarURL);
    InputStream input = url.openStream();
    OutputStream output = new FileOutputStream("icalexport.ics");
    byte[] buffer = new byte[2048];
    int length;
    while((length = input.read(buffer)) != -1) {
      output.write(buffer, 0, length);
    }
    input.close();
    output.close();
    }
    catch (MalformedURLException e) {
      System.out.println("error");
    }
  }

  public void replaceData(Scanner aPoints) {
    // This method replaces the data (if needed). For instance, the newly downloaded ICS file
    // is checked to see if it contains new data. If it does, it is added to the data file. 
    try {
      boolean newAssign = false;
      File file = new File("./icalexport.ics");
      Scanner input = new Scanner(file);
      while(input.hasNextLine()) {
        String test = input.nextLine(); // Get first line
        if(test.contains("UID")) {  // If current line contains the UID of an assignment
          File oldData = new File("./data.txt");
          Scanner oldInput = new Scanner(oldData);
          while(oldInput.hasNextLine()) { // Check the old data file to see if it exists
            newAssign = true;
            String check = oldInput.nextLine();
            if(test.contains(check)) { // If the current line of new calendar contains the same UID as a current assignment
              newAssign = false;
              break;
            }
            // TODO: Check list of completed assignments here, break and set newAssign to false (like above) if its there
          }
          if(!newAssign) // If we went through the entire old file and did not find a match, continue.
            continue;
          else {
            System.out.println("New assignment detected-");
          try {
            FileWriter myData = new FileWriter("data.txt", true); // Open data.txt to write
            myData.write("new\n"); // Starting each new assignment with "new" to indicate new assignment
            test = test.substring(4); // Make test just UID of assignment
            myData.write(test + "\n"); // Write assignment UID to file
            test = input.nextLine(); // Next Line is assignment name
            test = test.replace("SUMMARY:", ""); // Remove SUMMARY:
            test = test.replace(" is due", ""); // Remove is due
            test = test.replace(" closes", ""); // Remove closes
            String name = test;
            myData.write(test + "\n"); // Write assignment name to file
            while(!test.contains("DTEND")) // Going to end date
              test = input.nextLine();
            String year = test.substring(6, 10); // Parse end date in readable format
            String month = test.substring(10, 12);
            String day = test.substring(12, 14);
            myData.write(month + "/" + day + "/" + year + "\n"); // Write end date to file
            test = input.nextLine(); // Next line is course number
            String courseNum = test.substring(16, 19); // Parse course number
            test = test.substring(11, 14);
            myData.write(test + courseNum + "\n"); // Write course number
            System.out.println("Enter how many points " + name + " in class " + test + courseNum + " is worth:");
            String pointAmount = aPoints.nextLine();
            myData.write(pointAmount + "\n"); // Points the assignment is worth
            myData.write("0\n");                // Weighted points
            myData.write("false\n");            // Importance
            myData.close();
          } catch (IOException e) {
            System.out.println("Failed writing to file during replacing data");
          }
        }oldInput.close();}
      }
      input.close();
    }
    catch(FileNotFoundException e) {
      System.out.println("File not found during replacing data");
    }
    return;
  }
}
