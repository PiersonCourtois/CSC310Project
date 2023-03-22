import java.util.ArrayList;

public class ClassObject {
  ////////////////////////////////////////////////////////////////////
  // This is the class object. Each class object has a name, an     //
  // important value, and an array list that contains each of the   //
  // assignments available for that class. Accessing individual     //
  // data for assignment is a very long command because of this.    //
  // Maybe I'll find a better way of doing this?                    //
  ////////////////////////////////////////////////////////////////////
  public ArrayList<AssignmentObject> assignmentList = new ArrayList<AssignmentObject>();
  private boolean isImportant;
  private String name;

  // Setters
  public void setName(String iName) {
    this.name = iName;
  }

  public void addAssignment(String[] assignData) {
    AssignmentObject assign = new AssignmentObject();
    assign.setID(assignData[0]);
    assign.setName(assignData[1]);
    assign.setDueDate(assignData[2]);
    assign.setCourse(assignData[3]);
    assign.setPoints(Integer.parseInt(assignData[4]));
    this.assignmentList.add(assign);
  }

  public void setImportance(boolean important) {
    this.isImportant = important; // TODO: Alter all assignments in this class to be worth the correct amount of points
                                  // when changing the importance.
  }

  // Getters
  public String getName() {
    return this.name;
  }

  public boolean getImportance() {
    return this.isImportant;
  }

  public ArrayList<AssignmentObject> getWholeAssignmentList() {
    return this.assignmentList;
  }
}
