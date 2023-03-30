import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.Date;  

public class AssignmentObject {
  ////////////////////////////////////////////////////////////////////
  // This class is for the assignments. Each assignment has a name, //
  // course, ID, due date, points, days left, weighted points (that //
  // is, the points after considering things such as due date,      //
  // importance, etc...). Only thing left to do here is calculate   //
  //the importance and have the course importance influence the     //
  // points of it.                                                  //
  ////////////////////////////////////////////////////////////////////
  private String name;
  private String course;
  private String ID; // ID will be needed later to check for duplicate assignments
  private String dueDate;
  private int points;
  private int daysLeft;
  private int weightedPoints;
  private boolean isImportant;

  public void calculateImportance() {
    // TODO: calculate the importance based on how many days are left and how many points the assignment is worth
  }

  public void calculateDaysLeft() {
    // This method calculates how many days are left in the assignment then stores it in this objects daysLeft
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    try {
      long now = System.currentTimeMillis();
      Date due = sdf.parse(getDueDate());
      long difference = due.getTime() - now;
      long convert = TimeUnit.MILLISECONDS.toDays(difference);
      this.setDaysLeft((int)convert);
    }
    catch (ParseException e) {
      e.printStackTrace();
  }

  }

  // Setters
  public void setImportance(boolean iImportance) {
    // TODO: when this is set to true, double points. 
    // Also, important to make this cannot be called with the 
    // same importance twice (ie: If you make it so that when 
    // iImportance is true double the points, setting the importance
    // to two twice in a row will effectively quadruple its points.
    // Maybe make a new function that inverts the importance and
    // adjusts the points, THEN use this setImportance function.)
    this.isImportant = iImportance;
  }

  public void setWeightedPoints(int iWeightedPoints) {
    this.weightedPoints = iWeightedPoints;
  }

  public void setPoints(int iPoints) {
    this.points = iPoints;
  }

  public void setName(String iName) {
    this.name = iName;
  } 

  public void setCourse(String iCourse) {
    this.course = iCourse;
  }

  public void setDueDate(String iDueDate) {
    this.dueDate = iDueDate;
    this.calculateDaysLeft();
  }

  public void setDaysLeft(int iDaysLeft) {
    this.daysLeft = iDaysLeft;
  }

  public void setID(String iID) {
    this.ID = iID;
  }

  // Getters
  public boolean getImportance() {
    return this.isImportant;
  }

  public int getWeightedPoints() {
    return this.weightedPoints;
  }

  public int getPoints() {
    return this.points;
  }

  public String getName() {
    return this.name;
  }

  public String getCourse() {
    return this.course;
  }

  public String getDueDate() {
    return this.dueDate;
  }

  public int getDaysLeft() {
    return this.daysLeft;
  }

  public String getID() {
    return this.ID;
  }
}
