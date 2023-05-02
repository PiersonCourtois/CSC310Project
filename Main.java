/* TODO: 
 * 
 * Assignment points prompt - might just keep the way it is (would like GUI tho)
 * Resave assignments on close - will do last
 * Sort assignments after adding
 * Weighted points on right side in bigger font
 * Hover styles (ill try doing this later)
 * ^^^^^^^I plan on doing these later^^^^^^^
 * \/ \/ \/ Things that would be nice to get done \/ \/ \/
 * verify input boxes (May 5th 2023 MUST be 05/05/23, never 5/05/2023, 5/5/2023, etc...)
 * shadows? (not sure if this would look good)
 * strokes?	(I think buttons should have a stroke)
 * set individual assignment max width (our current course eval assignments stretch too much)
 * set colors for point ranges (RYG)
 * For split assignments, maybe just default to current splitting assignment instead of having user input
 * */
package application;
	
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scanner stdIn = new Scanner(System.in);	
		String link;
		
		//in order to get the screen to stay the same size, you need to set the width, height and make it maximized
		stage.setMaximized(true);
		stage.setWidth(1300);
		
		File dataFile = new File("calendarLink.txt");
		if(!dataFile.exists()) { // If file is created, load data into file
			dataFile.createNewFile();
			System.out.println("Enter Moodle link: ");
		    link = stdIn.nextLine();
		    FileWriter linkFile = new FileWriter("calendarLink.txt", true);
		    linkFile.write(link);
		    linkFile.close();
			}
		else { // Data file already exists, read the data
		    Scanner reader = new Scanner(dataFile);
		    link = reader.nextLine();
		    reader.close();
		}
		
		//so for this version I am prompting the user to input assignments. In the actual code they will be imported from moodle
		//lines 28-36 will not be used in the final code
		ArrayList<AssignmentObject> assignments = new ArrayList<AssignmentObject>();
	    CreateData data = new CreateData(); 
	    data.makeFile(stdIn, link); // Create data file
	    MainTest mainTest = new MainTest(); // I have to do this because the program won't work otherwise. Too bad!
	    assignments = mainTest.initData(); // Make data into AssignmentObjects and add to this list
	    
		
		//this marks the end of the temporary code. the rest will be used in the final product	
		
		//Set the title of the stage and put the header rectangle every time
		String title = "QU MOODLE CHECKLIST";
		stage.setTitle(title);

		//Now we will call the method to set the scene to the home page.
		//This is always where you start, so there is no need to call any other methods from the start method
		//all other pages will be called when you interact with the home page
		Home(assignments, stage);
		
	}//end start
	
	public void Home(ArrayList<AssignmentObject> assignments, Stage stage) {
		Group root = new Group();

		ScrollPane scrollPane= new ScrollPane();
		scrollPane.setContent(root);
		scrollPane.setStyle("-fx-background: gold;");
		Scene home = new Scene(scrollPane);
		
		
		Rectangle headerHome = new Rectangle(1300, 105);
		headerHome.setY(-5);
		headerHome.setFill(Color.SADDLEBROWN);
		
		//you can't change the height of the window or else you can't scroll
		//problem is then the height is cut off where the last thing is
		//to go around this problem we'll just put a tiny rectangle in the corner
		//to make the window atleast as big as the screen
		Rectangle cornerWeight = new Rectangle(100, 100);
		cornerWeight.setY(600);
		cornerWeight.setX(1300);
		cornerWeight.setFill(Color.GOLD);
		
		Text headerText = new Text("QU MOODLE CHECKLIST");
		headerText.setX(335);
		headerText.setY(60);
		headerText.setFont(Font.font(50));
		headerText.setFill(Color.WHITE);
		
		//make a button that takes you to the add assignments page
		Button add = new Button("+");
		add.setTranslateX(1150);
		add.setTranslateY(-5);
		Font font = Font.font("Verdana", FontWeight.BOLD, 50);
		add.setFont(font);
		add.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513;");
		add.setMaxHeight(90);
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				addAssignment(assignments, stage);
			}
		
		});
		
		//add the header to the root
		
		//now add the assignments
		//this will call the RowHome method for each assignment you have in your arrayList
		for (int i = 0; i < assignments.size(); i++)
		{
			root = RowHome(root, i, assignments.get(i), stage, assignments);//returning root makes sure to keep updating the root in this version. not doing this means you can't add to the root that's being used
		}//end add each assignment
		
		root.getChildren().add(headerHome);
		root.getChildren().add(headerText);
		root.getChildren().add(add);
		root.getChildren().add(cornerWeight);
		
		stage.setScene(home);
		stage.show();
	}//end home method
	
	//pass the root to add to, the index they're at in the list (determines placement) and the current assignment to get information from it
	//we also have to pass the stage and the scene so we can pass those to any individual assignments we want to open.
	public Group RowHome(Group root, int i, AssignmentObject current, Stage stage, ArrayList<AssignmentObject> assignments)//because this one loops, make sure you return the updated root
	{
		//Assignment 1
				Button title = new Button(current.getName());
				title.setTranslateX(0);
				title.setMaxWidth(1200);
				title.setTranslateY(95+(i*130));
				title.setMaxHeight(50);
				title.setStyle("-fx-text-fill: black;  -fx-background-color: gold; -fx-font-size: 4em; ");
				title.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						Individual(current, stage, assignments);
					}
					
				});
				
				Text points = new Text("Points: " + current.getPoints() + "         |");
				points.setX(35);
				points.setY(200 + (i*130));
				points.setFont(Font.font(20));
				
				Text course = new Text(current.getCourse());
				course.setX(220);
				course.setY(200 + (i*130));
				course.setFont(Font.font(20));
				
				Text dueDate = new Text("Due Date: " + current.getDueDate());
				dueDate.setX(1020);
				dueDate.setY(200 + (i*130));
				dueDate.setFont(Font.font(20));
				
				Line divider = new Line();
				divider.setStartX(30);
				divider.setStartY(220 + (i*130));
				divider.setEndX(1215);
				divider.setEndY(220 + (i*130));
				divider.setStrokeWidth(5);
				divider.setStroke(Color.SADDLEBROWN);
				
				root.getChildren().add(title);
				root.getChildren().add(points);
				root.getChildren().add(dueDate);
				root.getChildren().add(course);
				root.getChildren().add(divider);
				return root;
	}
	
	//the individual method need the assignment whose specifics it will be displaying, and the stage to display it
	//it also needs the assignment list to call the home object when you hit the back button
	public void Individual(AssignmentObject current, Stage stage, ArrayList<AssignmentObject> assignments)//this one doesn't have to return the root because it doesn't loop
	{
		Group rootSpecifics = new Group();
		ScrollPane scrollPane= new ScrollPane();
		scrollPane.setContent(rootSpecifics);
		scrollPane.setStyle("-fx-background: gold;");
		Scene AssignmentSpecifics = new Scene(scrollPane);
		
		Rectangle headerSpecifics = new Rectangle(1300, 105);
		headerSpecifics.setY(-5);
		headerSpecifics.setFill(Color.SADDLEBROWN);
		
		//you can't change the height of the window or else you can't scroll
		//problem is then the height is cut off where the last thing is
		//to go around this problem we'll just put a tiny rectangle in the corner
		//to make the window atleast as big as the screen
		Rectangle cornerWeight = new Rectangle(100, 100);
		cornerWeight.setY(600);
		cornerWeight.setX(1300);
		cornerWeight.setFill(Color.GOLD);
		
		Text title = new Text("Specifics for " + current.getName());
		title.setX(35);
		title.setY(60);
		
		title.setFont(Font.font(50));
		title.setFill(Color.WHITE);
		
		String text = ("Course: " + current.getCourse() + "\nDue: " + current.getDueDate() + "\nDays Left: " +current.getDaysLeft() + "\nPoints: " + current.getPoints() + "\nWeighted Points: " 
				 + current.getPoints() + "/" + current.getWeightedPoints() + "\nMarked as Important: ");
		if (current.getImportance())//if isImportant == true
		{
			text += ("yes");
		}//end if
		else //if isImportant == false
		{
			text += ("no");
		}//end else
		
		Text body = new Text(text);
		body.setX(35);
		body.setY(200);
		body.setFont(Font.font(50));
		
		//make a button that lets you go back to the main page
		Button exit = new Button("x");
		exit.setTranslateX(1150);
		exit.setTranslateY(-5);
		exit.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 4em; ");
		exit.setPrefHeight(90);
		exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Home(assignments, stage);
			}
			
		});
		
		Button split = new Button("Split Assignment");
		split.setTranslateX(35);
		split.setTranslateY(570);
		split.setPrefWidth(500);
		split.setPrefHeight(100);
		split.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 4em; ");
		split.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				 SplitAssignment(assignments, stage, current);
				 //this should take the assignment, get it off of the main list and then add 2 more assignments that break becasuse of him
			}
			
		});
		
		Button setImportant = new Button("Set As Important");
		setImportant.setTranslateX(635);
		setImportant.setTranslateY(570);
		setImportant.setPrefWidth(500);
		setImportant.setPrefHeight(100);
		setImportant.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 4em; ");
		
		Button setUnimportant = new Button("Set as Unimportant");
		setUnimportant.setTranslateX(635);
		setUnimportant.setTranslateY(570);
		setUnimportant.setPrefWidth(500);
		setUnimportant.setPrefHeight(100);
		setUnimportant.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 4em; ");
		
		
		if (current.getImportance())//if it is marked as important, hide the button that lets them make it important
		{
			setImportant.setVisible(false);
		}//end if important
		else //if it is unimportant, hide the button that lets them set it as un important
		{
			setUnimportant.setVisible(false);
		}
		//this way only one button is visible at a time because if its true, you can set it to false and vice verse
		
		//the actions of both buttons need to come after both buttons are made
		setUnimportant.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				 System.out.println("not important");
				 current.setImportance(false);//make sure to update this in the assignmentObject
				 current.setWeightedPoints(0);
				 body.setText("Course: " + current.getCourse() + "\nDue: " + current.getDueDate() + "\nDays Left: " +current.getDaysLeft() + "\nPoints: " + current.getPoints() + "\nWeighted Points: " 
						 + current.getPoints() + "/" + current.getWeightedPoints() + "\nMarked as Important: NO");;
				 setUnimportant.setVisible(false);//make this button go away
				 setImportant.setVisible(true);//and make the other button show up
			}
			
		});
		setImportant.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				 System.out.println("important");
				 current.setImportance(true);//make sure to update this in the assignmentObject
				 current.setWeightedPoints(0);
				 body.setText("Course: " + current.getCourse() + "\nDue: " + current.getDueDate() + "\nDays Left: " +current.getDaysLeft() + "\nPoints: " + current.getPoints() + "\nWeighted Points: " 
						 + current.getPoints() + "/" + current.getWeightedPoints() + "\nMarked as Important: YES");
				 setImportant.setVisible(false);//make this button go away
				 setUnimportant.setVisible(true);//and make the other button show up
			}
			
		});
		
		//now add the children
		rootSpecifics.getChildren().add(headerSpecifics);
		rootSpecifics.getChildren().add(title);
		rootSpecifics.getChildren().add(body);
		rootSpecifics.getChildren().add(exit);
		rootSpecifics.getChildren().add(split);
		rootSpecifics.getChildren().add(setImportant);
		rootSpecifics.getChildren().add(setUnimportant);
		rootSpecifics.getChildren().add(cornerWeight);
		
		stage.setScene(AssignmentSpecifics);
		stage.show();
	}
	public void addAssignment(ArrayList<AssignmentObject> assignments, Stage stage)
	{
		Group rootAdd = new Group();
		ScrollPane scrollPane= new ScrollPane();
		scrollPane.setContent(rootAdd);
		scrollPane.setStyle("-fx-background: gold;");
		Scene AddAssignment = new Scene(scrollPane);
		
		AssignmentObject temp = new AssignmentObject();
		
		Rectangle headerAdd = new Rectangle(1300, 105);
		headerAdd.setY(-5);
		headerAdd.setFill(Color.SADDLEBROWN);
		
		//you can't change the height of the window or else you can't scroll
		//problem is then the height is cut off where the last thing is
		//to go around this problem we'll just put a tiny rectangle in the corner
		//to make the window atleast as big as the screen
		Rectangle cornerWeight = new Rectangle(100, 100);
		cornerWeight.setY(600);
		cornerWeight.setX(1300);
		cornerWeight.setFill(Color.GOLD);
		
		Text title = new Text("Add Assignment");
		title.setX(35);
		title.setY(60);
		title.setFont(Font.font(50));
		title.setFill(Color.WHITE);
		
		//make a button that lets you go back to the main page
		Button exit = new Button("x");
		exit.setTranslateX(1160);
		exit.setTranslateY(-5);
		exit.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 4em; ");
		exit.setMaxHeight(70);
		exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				Home(assignments, stage);
			}
			
		});
		
		TextField name = new TextField();
		name.setPromptText("Input Assignment Name");
		name.setTranslateX(30);
		name.setTranslateY(130);
		name.setPrefWidth(1200);
		name.setPrefHeight(75);
		name.setStyle("-fx-text-fill: black;  -fx-background-color: white; -fx-font-size: 3em; ");
		name.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				temp.setName(name.getText());
			}
		});
		
		TextField course = new TextField();
		course.setPromptText("Input Assignment Course");
		course.setTranslateX(30);
		course.setTranslateY(230);
		course.setPrefWidth(1200);
		course.setPrefHeight(75);
		course.setStyle("-fx-text-fill: black;  -fx-background-color: white; -fx-font-size: 3em; ");
		course.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				temp.setCourse(course.getText());
			}
		});
		
		TextField dueDate = new TextField();
		dueDate.setPromptText("Input Due Date (mm/dd/yyyy)");
		dueDate.setTranslateX(30);
		dueDate.setTranslateY(330);
		dueDate.setPrefWidth(1200);
		dueDate.setPrefHeight(75);
		dueDate.setStyle("-fx-text-fill: black;  -fx-background-color: white; -fx-font-size: 3em; ");
		dueDate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				temp.setDueDate(dueDate.getText());
			}
		});
		
		TextField points = new TextField();
		points.setPromptText("Input Number of Points");
		points.setTranslateX(30);
		points.setTranslateY(430);
		points.setPrefWidth(1200);
		points.setPrefHeight(75);
		points.setStyle("-fx-text-fill: black;  -fx-background-color: white; -fx-font-size: 3em; ");
		points.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				temp.setPoints(Integer.parseInt(points.getText()));
			}
		});
		

		Button setImportant = new Button("Set As Important");
		setImportant.setTranslateX(30);
		setImportant.setTranslateY(530);
		setImportant.setPrefWidth(500);
		setImportant.setPrefHeight(100);
		setImportant.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 4em; ");
		
		Button setUnimportant = new Button("Set as Unimportant");
		setUnimportant.setTranslateX(30);
		setUnimportant.setTranslateY(530);
		setUnimportant.setPrefWidth(500);
		setUnimportant.setPrefHeight(100);
		setUnimportant.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 4em; ");
		
		
		setUnimportant.setVisible(false);//assignments default as unimportant, so you don't have to give them the object to set it as important yet
		//the actions of both buttons need to come after both buttons are made
		setUnimportant.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				setUnimportant.setVisible(false);//make this button go away
				setImportant.setVisible(true);//and make the other button show up
				temp.setImportance(false); 
			}
			
		});
		setImportant.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				 setImportant.setVisible(false);//make this button go away
				 setUnimportant.setVisible(true);//and make the other button show up
				 temp.setImportance(true);//make sure to update this in the assignmentObject
			}
			
		});
		
		Button add = new Button("Add Assignment");
		add.setTranslateX(730);
		add.setTranslateY(530);
		add.setPrefWidth(500);
		add.setPrefHeight(100);
		add.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 4em; ");
		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				temp.setName(name.getText());
				temp.setCourse(course.getText());
				temp.setDueDate(dueDate.getText());
				temp.setPoints(Integer.parseInt(points.getText()));
				temp.setWeightedPoints(0);
				assignments.add(temp);
				
				Home(assignments, stage);
			}
			
		});
		
		
		rootAdd.getChildren().add(headerAdd);
		rootAdd.getChildren().add(cornerWeight);
		rootAdd.getChildren().add(title);
		rootAdd.getChildren().add(exit);
		rootAdd.getChildren().add(name);
		rootAdd.getChildren().add(course);
		rootAdd.getChildren().add(dueDate);
		rootAdd.getChildren().add(points);
		rootAdd.getChildren().add(add);
		rootAdd.getChildren().add(setImportant);
		rootAdd.getChildren().add(setUnimportant);
		
		stage.setScene(AddAssignment);
		stage.show();
	}//end addAssignment
	
	public void SplitAssignment (ArrayList<AssignmentObject> assignments, Stage stage, AssignmentObject current)
	{
		Group rootSplit = new Group();
		ScrollPane scrollPane= new ScrollPane();
		scrollPane.setContent(rootSplit);
		scrollPane.setStyle("-fx-background: gold;");
		Scene SplitAssignment = new Scene(scrollPane);
		
		AssignmentObject temp1 = new AssignmentObject();
		AssignmentObject temp2 = new AssignmentObject();
		
		Rectangle headerAdd = new Rectangle(1300, 105);
		headerAdd.setY(-5);
		headerAdd.setFill(Color.SADDLEBROWN);
		
		//you can't change the height of the window or else you can't scroll
		//problem is then the height is cut off where the last thing is
		//to go around this problem we'll just put a tiny rectangle in the corner
		//to make the window atleast as big as the screen
		Rectangle cornerWeight = new Rectangle(100, 100);
		cornerWeight.setY(600);
		cornerWeight.setX(1300);
		cornerWeight.setFill(Color.GOLD);
		
		Text title = new Text("Spit Assignment");
		title.setX(35);
		title.setY(60);
		title.setFont(Font.font(50));
		title.setFill(Color.WHITE);
		
		//make a button that lets you go back to the main page
		Button exit = new Button("x");
		exit.setTranslateX(1160);
		exit.setTranslateY(-5);
		exit.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 4em; ");
		exit.setMaxHeight(70);
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
					Individual(current, stage, assignments);
			}
					
		});
		
		String text = ("Name: " + current.getName() + "\nCourse: " + current.getCourse() + "\nDue: " + current.getDueDate() + "\nDays Left: " +current.getDaysLeft() + "\nPoints: " + current.getPoints() + "\nWeighted Points: " 
				 + current.getPoints() + "/" + current.getWeightedPoints() + "\nMarked as Important: ");
		if (current.getImportance())//if isImportant == true
		{
			text += ("yes");
		}//end if
		else //if isImportant == false
		{
			text += ("no");
		}//end else
		
		Text body = new Text(text);
		body.setX(35);
		body.setY(200);
		body.setFont(Font.font(30));
		
		Line divider1 = new Line();
		divider1.setStartX(400);
		divider1.setStartY(130);
		divider1.setEndX(400);
		divider1.setEndY(650);
		divider1.setStrokeWidth(5);
		divider1.setStroke(Color.SADDLEBROWN);
		
		Line divider2 = new Line();
		divider2.setStartX(850);
		divider2.setStartY(130);
		divider2.setEndX(850);
		divider2.setEndY(650);
		divider2.setStrokeWidth(5);
		divider2.setStroke(Color.SADDLEBROWN);
		
		TextField name1 = new TextField();
		name1.setPromptText("Input Assignment Name");
		name1.setTranslateX(430);
		name1.setTranslateY(130);
		name1.setPrefWidth(350);
		name1.setStyle("-fx-text-fill: black;  -fx-background-color: white; -fx-font-size: 2em; ");
		name1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				temp1.setName(name1.getText());
			}
		});
		
		TextField course1 = new TextField();
		course1.setPromptText("Input Assignment Course");
		course1.setTranslateX(430);
		course1.setTranslateY(230);
		course1.setPrefWidth(350);
		course1.setStyle("-fx-text-fill: black;  -fx-background-color: white; -fx-font-size: 2em; ");
		course1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				temp1.setCourse(course1.getText());
			}
		});
		
		TextField dueDate1 = new TextField();
		dueDate1.setPromptText("Input Due Date (mm/dd/yyyy)");
		dueDate1.setTranslateX(430);
		dueDate1.setTranslateY(330);
		dueDate1.setPrefWidth(350);
		dueDate1.setStyle("-fx-text-fill: black;  -fx-background-color: white; -fx-font-size: 2em; ");
		dueDate1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				temp1.setDueDate(dueDate1.getText());
			}
		});
		
		TextField points1 = new TextField();
		points1.setPromptText("Input Number of Points");
		points1.setTranslateX(430);
		points1.setTranslateY(430);
		points1.setPrefWidth(350);
		points1.setStyle("-fx-text-fill: black;  -fx-background-color: white; -fx-font-size: 2em; ");
		points1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				temp1.setPoints(Integer.parseInt(points1.getText()));
			}
		});
		

		Button setImportant1 = new Button("Set As Important");
		setImportant1.setTranslateX(430);
		setImportant1.setTranslateY(530);
		setImportant1.setPrefWidth(350);
		setImportant1.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 2em; ");
		
		Button setUnimportant1 = new Button("Set as Unimportant");
		setUnimportant1.setTranslateX(430);
		setUnimportant1.setTranslateY(530);
		setUnimportant1.setPrefWidth(350);
		setUnimportant1.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 2em; ");
		
		
		setUnimportant1.setVisible(false);//assignments default as unimportant, so you don't have to give them the object to set it as important yet
		//the actions of both buttons need to come after both buttons are made
		setUnimportant1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				setUnimportant1.setVisible(false);//make this button go away
				setImportant1.setVisible(true);//and make the other button show up
				temp1.setImportance(false); 
			}
			
		});
		setImportant1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				 setImportant1.setVisible(false);//make this button go away
				 setUnimportant1.setVisible(true);//and make the other button show up
				 temp1.setImportance(true);//make sure to update this in the assignmentObject
			}
			
		});
		
		
		TextField name2 = new TextField();
		name2.setPromptText("Input Assignment Name");
		name2.setTranslateX(880);
		name2.setTranslateY(130);
		name2.setPrefWidth(350);
		name2.setStyle("-fx-text-fill: black;  -fx-background-color: white; -fx-font-size: 2em; ");
		name2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				temp2.setName(name2.getText());
			}
		});
		
		TextField course2 = new TextField();
		course2.setPromptText("Input Assignment Course");
		course2.setTranslateX(880);
		course2.setTranslateY(230);
		course2.setPrefWidth(350);
		course2.setStyle("-fx-text-fill: black;  -fx-background-color: white; -fx-font-size: 2em; ");
		course2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				temp2.setCourse(course2.getText());
			}
		});
		
		TextField dueDate2 = new TextField();
		dueDate2.setPromptText("Input Due Date (mm/dd/yyyy)");
		dueDate2.setTranslateX(880);
		dueDate2.setTranslateY(330);
		dueDate2.setPrefWidth(350);
		dueDate2.setStyle("-fx-text-fill: black;  -fx-background-color: white; -fx-font-size: 2em; ");
		dueDate2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				temp2.setDueDate(dueDate2.getText());
			}
		});
		
		TextField points2 = new TextField();
		points2.setPromptText("Input Number of Points");
		points2.setTranslateX(880);
		points2.setTranslateY(430);
		points2.setPrefWidth(350);
		points2.setStyle("-fx-text-fill: black;  -fx-background-color: white; -fx-font-size: 2em; ");
		points2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				temp2.setPoints(Integer.parseInt(points2.getText()));
			}
		});
		

		Button setImportant2 = new Button("Set As Important");
		setImportant2.setTranslateX(880);
		setImportant2.setTranslateY(530);
		setImportant2.setPrefWidth(350);
		setImportant2.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 2em; ");
		
		Button setUnimportant2 = new Button("Set as Unimportant");
		setUnimportant2.setTranslateX(880);
		setUnimportant2.setTranslateY(530);
		setUnimportant2.setPrefWidth(350);
		setUnimportant2.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 2em; ");
		
		
		setUnimportant2.setVisible(false);//assignments default as unimportant, so you don't have to give them the object to set it as important yet
		//the actions of both buttons need to come after both buttons are made
		setUnimportant2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				setUnimportant2.setVisible(false);//make this button go away
				setImportant2.setVisible(true);//and make the other button show up
				temp2.setImportance(false); 
			}
			
		});
		setImportant2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				 setImportant2.setVisible(false);//make this button go away
				 setUnimportant2.setVisible(true);//and make the other button show up
				 temp1.setImportance(true);//make sure to update this in the assignmentObject
			}
			
		});
		
		Button add = new Button("Add Assignment");
		add.setTranslateX(30);
		add.setTranslateY(530);
		add.setPrefWidth(350);
		add.setStyle("-fx-text-fill: white;  -fx-background-color: #8b4513; -fx-font-size: 2em; ");
		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				temp1.setName(name1.getText());
				temp1.setCourse(course1.getText());
				temp1.setDueDate(dueDate1.getText());
				temp1.setPoints(Integer.parseInt(points1.getText()));
				temp1.setWeightedPoints(0);
				temp2.setName(name2.getText());
				temp2.setCourse(course2.getText());
				temp2.setDueDate(dueDate2.getText());
				temp2.setPoints(Integer.parseInt(points2.getText()));
				temp2.setWeightedPoints(0);
				assignments.remove(current);
				assignments.add(temp1);
				assignments.add(temp2);
				Home(assignments, stage);
			}
			
		});
		
		rootSplit.getChildren().add(headerAdd);
		rootSplit.getChildren().add(cornerWeight);
		rootSplit.getChildren().add(title);
		rootSplit.getChildren().add(exit);
		rootSplit.getChildren().add(body);
		rootSplit.getChildren().add(divider1);
		rootSplit.getChildren().add(divider2);
		rootSplit.getChildren().add(name1);
		rootSplit.getChildren().add(course1);
		rootSplit.getChildren().add(dueDate1);
		rootSplit.getChildren().add(points1);
		rootSplit.getChildren().add(setImportant1);
		rootSplit.getChildren().add(setUnimportant1);
		rootSplit.getChildren().add(name2);
		rootSplit.getChildren().add(course2);
		rootSplit.getChildren().add(dueDate2);
		rootSplit.getChildren().add(points2);
		rootSplit.getChildren().add(setImportant2);
		rootSplit.getChildren().add(setUnimportant2);
		
//		rootSplit.getChildren().add(name);
//		rootSplit.getChildren().add(course);
//		rootSplit.getChildren().add(dueDate);
//		rootSplit.getChildren().add(points);
//		rootSplit.getChildren().add(setImportant);
//		rootSplit.getChildren().add(setUnimportant);
		
		rootSplit.getChildren().add(add);
		
		stage.setScene(SplitAssignment);
		stage.show();
	}//end method SplitAssignment
}//end class Main
