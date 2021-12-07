package controller;

/** @author lukas bozinov
 * 
 */

/* https://stackoverflow.com/questions/1762878/how-to-check-if-string-value-is-boolean-type-in-java
 * https://stackoverflow.com/questions/64217552/what-is-a-delimiter
 * https://www.java67.com/2012/08/how-to-convert-enum-to-string-in-java.html
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import utilities.Point;
import model.CardColour;
import model.City;
import model.Route;
import model.Ticket;

public class FileImportController {

	/* A hash map contains a Key (String in this case) and a Value (City). This advanced data structure is going to help
	 * us store our data more efficiently, and in turn, run our game faster.
	 */
	public static HashMap<String, City> citiesOnBoard = new HashMap<>();
	
	/* An arrayList is an advanced data type used very similarly to a stack, in that it has infinite length
	 * and can be used as a means of efficient data storage. In our use case, we use ArrayLists to store all
	 * of our cities, and the routes between them.
	 */
	public static ArrayList<City> listOfCities = new ArrayList<>();
	public static ArrayList<Route> listOfRoutes = new ArrayList<>();
	
	/* A Stack resembles a stack of cards! You can "pop" the top value off of the stack to retrieve it, or
	 * "push" a value onto the top of the stack data type. This results in an easy way to simulate a pile of cards,
	 *  hence the 
	 */
	public static final Stack<Ticket> pileOfTickets = new Stack<>();

	//Basically our 'main' method for this class, it runs the all the methods besides itself
	public static void execute() {
		citiesFileImport();
		routesFileImport();
		ticketsFileImport();

	}
	
	
	//this method was built to replace a InputMismatchError encountered previously with some of the
	//city names
	private static String fixInputMismatchError (String string) {
        return string.replace("\n", "").replace("\r", "");
    }

	//Import the cities from cities.txt
	public static void citiesFileImport() {

		//Set variables for each parameter in City.java's constructor
		String cityName;
		int pointX;
		int pointY;

		//Implement a try/catch statement to catch the error that happens if the file in unreachable
		try {

			//Create a scanner to read out file and use the delimiter to separate the values in the list
			Scanner input = new Scanner(new File("files/cities.txt"));
			input.useDelimiter(",");

			//Set up a while loop that iterates until there are no more lines left
			while (input.hasNext()) {

				//Fill our variables with the data from cities.txt
				cityName = fixInputMismatchError(input.next());
				
				pointX = input.nextInt();
				pointY = input.nextInt();

				//Create a City object for every line of cities.txt and add them to the list of cities,
				//and then put them on the board
				City extractedCity = new City(cityName, new Point(pointX, pointY));
				listOfCities.add(extractedCity);
				citiesOnBoard.put(cityName, extractedCity);

			}

			//Close the scanner
			input.close();

		} catch (FileNotFoundException error) {
			error.printStackTrace(); //Print where the error occurred if there is an error
		}
	}

	//Import the routes from routes.txt
	public static void routesFileImport() {

		//Create variables for all the parameters in the constructor of Route.java
		String city1;
		String city2;
		int lengthOfRoute;
		String colour;
		Point endPoint;
		boolean goesBothWays;

		//Create a try/catch statement like the previous method to catch a FileNotFoundException
		try {

			//Create another scanner to read in routes.txt, separated by the comma delimiter
			Scanner input = new Scanner(new File("files/routes.txt"));
			input.useDelimiter(",");

			//Create a while loop that iterates until the final line in routes.txt
			while (input.hasNext()) {

				//Fill all of our variables using the data that we've scanned
				city1 = fixInputMismatchError(input.next());
				city2 = fixInputMismatchError(input.next());
				lengthOfRoute = input.nextInt();

				colour = input.next(); // gets the colour through a string
				CardColour cardColour = CardColour.valueOf(colour); //converts to CardColour enum type

				//create a new point, endPoint, for city2
				endPoint = new Point(input.nextInt(), input.nextInt());
		
				/* parse boolean checks if the string given contains "True",
				 * (literally the word "True" like in the text file given) case-sensitive
				 */ 
				goesBothWays = Boolean.parseBoolean(input.next());

				listOfRoutes.add(new Route(citiesOnBoard.get(city1), citiesOnBoard.get(city2), lengthOfRoute,
						cardColour, endPoint, goesBothWays));

			}
			//Close the scanner
			input.close();

		} catch (FileNotFoundException error) {
			error.printStackTrace(); //Print where an error occurred if needed
		}
	}

	//Finally, import the tickets
	public static void ticketsFileImport() {

		//Create another scanner
		Scanner input;
		
		//Use another try/catch statement
		try {
			//Scan with delimiter like last time
			input = new Scanner(new File("files/tickets.txt"));
			input.useDelimiter(",");

			//Scan until the file has no more text
			while (input.hasNext()) {
				//Create variables and fill them with data right afterwards,
				//Matching Ticket.java
				String city1 = fixInputMismatchError(input.next());
				String city2 = fixInputMismatchError(input.next());
				int value = input.nextInt();

				//Create a new Ticket object with the data scanner from tickets.txt
				Ticket ticket = new Ticket(citiesOnBoard.get(city1), citiesOnBoard.get(city2), value, false);
				pileOfTickets.push(ticket);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}