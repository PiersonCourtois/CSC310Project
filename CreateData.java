import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class CreateData {
  ////////////////////////////////////////////////////////////////////
  // The purpose of this class is to create a data file that can be //
  // more easily read by Java. It converts and parses the calendar  //
  // file which will be read in MainTest to convert the data into   //
  // Java objects.                                                  //
  ////////////////////////////////////////////////////////////////////

  public void makeFile(Scanner input) {
    try {
      File data = new File("data.txt");
      if(data.createNewFile()) { // If file is created, load data into file
        System.out.println("No existing data detected");
        createData(input);
        return;
      }
      else { // Data file already exists, read the data
        System.out.println("Existing data found"); // TODO: Ask user if they want to overwrite existing data with new file
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
      File file = new File("./icalexport.ics"); // Open calendar file // TODO: Probably just make this .ics instead of txt
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
}
