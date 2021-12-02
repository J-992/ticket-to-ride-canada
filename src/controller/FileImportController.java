package controller;

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

import model.CardColour;
import model.City;
import model.Route;
import model.Ticket;

public class FileImportController {

	public static HashMap<String, City> citiesOnBoard = new HashMap<>();
	public static ArrayList<City> listOfCities = new ArrayList<>();
	public static ArrayList<Route> listOfRoutes = new ArrayList<>();
	public static final Stack<Ticket> pileOfTickets = new Stack<>();

	public static void execute() {
		citiesFileImport();
		routesFileImport();
		ticketsFileImport();
	}

	// import the cities first
	public static void citiesFileImport() {

		// ArrayList<City> listOfCities = new ArrayList<>();

		String cityName;
		int pointX;
		int pointY;

		try {

			Scanner input = new Scanner(new File("allFiles/cities.txt"));
			input.useDelimiter(",");

			while (input.hasNextLine()) {

				cityName = input.nextLine();
				pointX = input.nextInt();
				pointY = input.nextInt();

				City extractedCity = new City(cityName, new Point(pointX, pointY));
				listOfCities.add(extractedCity);
				citiesOnBoard.put(cityName, extractedCity);

			}

			input.close();

		} catch (FileNotFoundException error) {
			error.printStackTrace();
		}
	}

	public static void routesFileImport() {

		// ArrayList<Route> listOfRoutes = new ArrayList<>();

		String city1;
		String city2;
		int lengthOfRoute;
		String colour;
		Point endPoint;
		boolean goesBothWays;

		try {

			Scanner input = new Scanner(new File("allFiles/routes.txt"));
			input.useDelimiter(",");

			while (input.hasNextLine()) {

				city1 = input.nextLine();
				city2 = input.nextLine();
				lengthOfRoute = input.nextInt();

				colour = input.nextLine(); // gets the colour through a string
				CardColour cardColour = CardColour.valueOf(colour);

				endPoint = new Point(input.nextInt(), input.nextInt());
				// parseboolean checks if the string given contains "True",
				// (literally the word "True" like in the text file given) case-sensitive
				goesBothWays = Boolean.parseBoolean(input.nextLine());

				listOfRoutes.add(new Route(citiesOnBoard.get(city1), citiesOnBoard.get(city2), lengthOfRoute,
						cardColour, endPoint, goesBothWays));

			}

			input.close();

		} catch (FileNotFoundException error) {
			error.printStackTrace();
		}
	}

	public static void ticketsFileImport() {

		// final Stack<Ticket> pileOfTickets = new Stack<>();

		Scanner input;
		try {
			input = new Scanner(new File("allFiles/tickets.txt"));
			input.useDelimiter(",");

			while (input.hasNextLine()) {
				String city1 = input.nextLine();
				String city2 = input.nextLine();
				int value = input.nextInt();

				Ticket ticket = new Ticket(citiesOnBoard.get(city1), citiesOnBoard.get(city2), value, false);
				pileOfTickets.push(ticket);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
