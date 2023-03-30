# Update 1
- **ICS file now gets automatically downloaded**
  - For now, you can manually input your calendar link in the CreateData class within the downloadCurrentCalendar function parameter
  - To get the calendar link on Moodle, go to Calendar > Export Calendar > Get Calendar URL![2023-03-29_21-12](https://user-images.githubusercontent.com/120692984/228710269-24d96e25-1db1-418a-9da4-9723319356e7.png)
  - The calendar will automatically update every time the program is ran, as well as the data file (With no duplicates)
  - When a new assignment is detected from the new calendar, the program prompts the user for data on that assignment just like it would when first importing
- **Added a function that updates the data file before exiting**
  - This is so that if the user changes an attribute for an assignment (ex: making an assignment important, chaning due date, etc...), that change will stay updated in the data file. 
- **Class object removed**
  - There was no reason to have a class object, it just made things more difficult. I condensed everything into the AssignmentObject ArrayList. This should hopefully make sorting the assignments easier. 

## What's left
As far as the backend goes:
- Implement a function that sorts the assignments in ascending order of points
- Implement a function to the AssignmentObject class that calulates the weighted points by taking into consideration due date, importance, etc.
- Implement a function to make an assignment important and update its points value accordingly
- Implement a function to split assignments (I don't know how I'm going to implement this)





# CSC 360 Project
This is a demonstration of the project. Mainly all this does right now is take in data from the ICS file, parse that data, convert that to a data file (data.txt),
then convert that data file into Java objects that can be used. Converting the data to a data file allows the program to read from the data file instead of the ICS file everytime. This is useful since the program won't have to parse the data every time its ran and it can save user inputted data, like points or importance for an assignment. I implemented very rough command line options that can be used to search
through the classes and assignments to find out their relevant information. 
## In order to use this...
You have to put the ICS file into the folder with the Java files. The data file will automatically be created if one is not already found. Later, it would be good to implement a file search popup so that the user can select the ICS file. 

![2023-03-21_21-26](https://user-images.githubusercontent.com/120692984/226786005-b026204d-fec2-415d-827c-d6e163b080f9.png)

After running the program, if no data is detected, it will start parsing through the ICS file to create the data file. Here it will find every assignment then ask the user to input how many points that assignment is worth. Now, whenever the program is launched, it will read from the data file and not the ICS file. 

![2023-03-21_21-29](https://user-images.githubusercontent.com/120692984/226786411-ee00d938-6db7-4fed-b6e5-7631b7ab361b.png)

After that, it will read the data from the data file and turn them into Java objects. The class objects are shown and the user can select a class to look through by typing in the corresponding number. 

![2023-03-21_21-31](https://user-images.githubusercontent.com/120692984/226786707-f642eff3-a932-46b0-9f19-2f7407afe37e.png)

It will then show the assignments in that class

![2023-03-21_21-32](https://user-images.githubusercontent.com/120692984/226786794-66564e87-cef1-40f4-8523-dcd9d97ab285.png)

Here you can see the name, due date, days left (In the negative since it was due last month), and the points worth (as previously inputted).

![2023-03-21_21-33](https://user-images.githubusercontent.com/120692984/226786942-ad82d077-551d-4bfb-b959-e7d2c9c9eb69.png)

From here you can go back to look at the other classes and assignments.

## How to update data
For now, in order to update the data you have to delete the data file and replace the existing ICS file. 

## What's left
This part converts all the data into objects with some basic functions. From here, we just need to add the rest of the required functions, such 
as assigning an overall point value to each assignment based on due date, importance, etc., splitting up an assignment, letting the user create their
own assignment, sorting and displaying assignments based on their overall importance, updating the data file, implementing a GUI, and probably more 
that I can't think of right now. This code most definitely will change a ton when we start to implement the GUI, but that's a problem for later. 
