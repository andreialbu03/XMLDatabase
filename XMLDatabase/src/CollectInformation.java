/**
 *  Purpose:  Class responsible for gathering user data, entering the data and saving it into an XML file, and searching for data from the file
 *  Author:  Andrei Albu
 *  Created On:  28 October 2020
 *  Modified By: Andrei Albu
 *  Modified On:  29 October 2020
 */

// Required import statements
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import nu.xom.*;

public class CollectInformation 
{
	// Private static fields of the CollectInfomation class
	private static Element root;
	private static Document doc;
	private static int optionSelected;
	
	// Method that allows the user to enter data
	public static void enterData()
	{
		// Scanner object is created and a boolean variable enterMoreData is set to true
		Scanner in = new Scanner(System.in);
		boolean enterMoreData = true;

		// While enterMoreData is true, allow the user to enter more data
		while (enterMoreData)
		{
			// Calls helper method to add the data and asks the user if they want to enter more data
			addData();
			System.out.println("Do you wish to enter more data?");
			System.out.println("Enter yes or no (exact spelling)");
			String userInput = in.nextLine();
			
			// If the user input stored in userInput does not equal to "yes", enterMoreData is set to false 
			if (!userInput.equals("yes"))
			{
				enterMoreData = false;
			}
		}
		
		// Helper method to save the file is called and scanner object is closed
		save();
		//in.close();
	}
	
	// Helper method to add the data to the elements
	private static void addData()
	{
		// Scanner object is declared and initialized
		Scanner in = new Scanner(System.in);

		// The respective elements are created
		Element classmate = new Element("classmate");
		Element name = new Element("name");
		Element address = new Element("address");
		Element email = new Element("email");
		
		// Prompts the user to enter the data for the respective elements and adds the values to the elements
		System.out.println("Enter the classmate's name:");		
		name.appendChild(in.nextLine());
		System.out.println("Enter the classmate's address:");
		address.appendChild(in.nextLine());
		System.out.println("Enter the classmate's email address:");
		email.appendChild(in.nextLine());
		
		// Creates the element hierarchy 
		root.appendChild(classmate);
		classmate.appendChild(name);
		classmate.appendChild(address);
		classmate.appendChild(email);	
	}
	
	// Helper method that saves the data to a file on the hard drive
	private static void save()
	{
		// Writing the file is stored in a try-catch statement to catch run-time errors
		try 
		{
			// FileWriter object is created to create the file and a BufferedWriter object is created to write the data in the file 
			FileWriter xmlfile = new FileWriter("myfile.xml");
			BufferedWriter writer = new BufferedWriter(xmlfile);
			writer.write(doc.toXML());
			writer.close();
		} 
		catch (IOException e) 
		{
			// If run-time exception happens, print the error message
			System.out.println("Error: " + e);
		}
	}
	
	// Method that searches the existing data for a desired value that is passed to the method as parameter 
	public static void searchData(String searchFor) 
	{
		// Elements object is created that contains all the elements of the root node as well the boolean variable notFound is set true
		Elements classmates = root.getChildElements();
		boolean notFound = true;
		
		// For loop is used to cycle through all the elements in the Elements object
		for (int i=0; i < classmates.size(); i++)
		{
			// Checks to see if sub element "name" matches the string searchFor
			if (searchFor.equals(classmates.get(i).getFirstChildElement("name").getValue()))
			{
				// Displays all data for that entry and sets notFound to false
				System.out.println(classmates.get(i).toXML());
				notFound = false;
			} 
			
			// Checks to see if sub element "address" matches the string searchFor
			else if (searchFor.equals(classmates.get(i).getFirstChildElement("address").getValue()))
			{
				// Displays all data for that entry and sets notFound to false
				System.out.println(classmates.get(i).toXML());
				notFound = false;
			}
			
			// Checks to see if sub element "email" matches the string searchFor
			else if (searchFor.equals(classmates.get(i).getFirstChildElement("email").getValue()))
			{
				// Displays all data for that entry and sets notFound to false
				System.out.println(classmates.get(i).toXML());
				notFound = false;
			}
			
			// If none of the sub elements contain the string searchFor, prompts the user it does not exist in the data
			else if ((i == classmates.size() - 1) && notFound)
			{
				System.out.println("This item does not exit!");
			}
		}
	}
	
	// Method that displays all the data
	public static void displayData() 
	{
		// Try-catch statement is used to print data to catch run-time exceptions
		try 
		{
			// Serializer object is created to format the data to make it easier to read
			Serializer serializer = new Serializer(System.out);
			serializer.setIndent(4);
			serializer.setMaxLength(64);
			serializer.write(doc);
		} 
		catch (IOException ex) 
		{
			// If run-time exception happens, print the error message
			System.err.println(ex);
		}
	}
	
	// Method to display the main menu containing all the options the user has
	public static void mainMenu(File file)
	{
		// Scanner object is declared and initialized
		Scanner in = new Scanner(System.in);
		
		// Informs the user of all the possible options and stores their input in optionSelected
		System.out.println("Make sure to enter data before displaying it or searching for something in it");
		System.out.println("1. Enter data");
		System.out.println("2. Display the data");
		System.out.println("3. Search for something in the data");
		System.out.println("Please enter the number of the option you want to select (1, 2 or 3)");
		optionSelected = Integer.parseInt(in.nextLine());
		
		// A switch statement is used to decide which option to execute by comparing the user input to each case, which corresponds to a specific option
		switch(optionSelected)
		{
		
		// If the user inputs 1, the method enterData is called and then breaks out of the switch statement
		case 1:
			enterData();
			break;
			
		// If the user enters 2, the program first checks that there is a file and if there is, calls the method displayData and then breaks of switch statement
		case 2: 
			if (!file.exists())
			{
				// If the file does not exist, informs the user to enter data first and then breaks out of the switch statement
				System.out.println("There is no data entered. Please choose option 1 to enter data first.");
				break;
			}
			displayData();
			break;
			
		// If the user enters 3, the program first checks that there is a file, and executes code to search in the file for a user specified string and the breaks of switch statement
		case 3:
			if (!file.exists())
			{
				// If the file does not exist, informs the user to enter data first and then breaks out of the switch statement
				System.out.println("There is no data entered. Please choose option 1 to enter data first.");
				break;
			}
			
			// If the file exits, asks the user for the string they want to search for and stores it in searchFor, then calls searchData with searchFor as parameter
			System.out.println("What do you want to search for?\nEnter it EXACTLY how it is in the file (full name, uppercase/lowercase, etc.):");
			String searchFor = in.nextLine();
			searchData(searchFor);
			break;
		}
		
		// Scanner object is closed to not cause resource leaks
		//in.close();
	}
	
	// The entry point of the program
	public static void main(String[] args) 
	{
		// root is initialized with a parameter and File and Builder objects are created
		root = new Element("classmates");
		File file = new File("myfile.xml");
		Builder builder = new Builder();
		
		// Checks if the file already exits on the user's computer
		if (file.exists())
		{
			// Loading the data from the external file is stored in a try-catch statement to catch run-time errors
			try 
			{
				// Load the data from the external file to a document and loads the root node into the field element called root
				doc = builder.build(file);
				root = doc.getRootElement();
			} 
			catch (IOException e) 
			{
				// If run-time IOException happens, print the error message
				System.out.println("Error: " + e);
			}
			catch (ParsingException e) 
			{
				// If run-time ParsingException happens, print the error message
				System.out.println("Error: " + e);
			}
		}
		else
		{
			// If the file does not exist, creates the document with the root as the parameter
			doc = new Document(root);
		}
		
		
		boolean running = true;
		Scanner in = new Scanner(System.in);
		while (running) 
		{
			// Calls the mainMenu method with file as parameter
			mainMenu(file);
			
			System.out.println();
			System.out.println("Do you want to go back to main menu? Type yes or no.");
			String input = in.nextLine();
			while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no")) 
			{
				System.out.println("Make sure to enter yes or no.");
				input = in.nextLine();
			}
			
			if (input.equalsIgnoreCase("yes")) 
			{
				// do nothing
			}
			if (input.equalsIgnoreCase("no")) 
			{
				// break from loop
				running = false;
			}
		}
		
		System.out.println("Program exited.");
	}
}
