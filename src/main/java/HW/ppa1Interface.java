package HW;

import java.util.Scanner;
import java.sql.*;

public class ppa1Interface {

	public static void main(String[] args) {
		// Initialize function object and scanner
		ppa1Function ppa1 = new ppa1Function();
		Scanner in = new Scanner(System.in);
		//init sql connection vars
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		boolean connected = false;

		// Welcome Statement
		System.out.println("Welcome to the PPA1 Function Interface. Please select a function or terminate the program.\n");

		try {
    	Class.forName("org.postgresql.Driver");
    }
		catch (ClassNotFoundException e) {
    	e.printStackTrace();
			System.out.println("Could not find SQL connection driver.");
    }

		try{
			connection = DriverManager.getConnection("jdbc:postgresql://192.168.99.100:5432/ppa2db", "postgres", "password");
			statement = connection.createStatement();
			connected = true;
		}
		catch(SQLException e){
			e.printStackTrace();
			System.out.println("Could not connect to the database. Terminating program.");
		}

		// Loop to control interface interaction
		int selection;
		if(connected)
			selection = 0;
		else
			selection = 5;

		do {
			if(selection != 5){
				System.out.println("Please enter the number for which function you would like to use.");
				System.out.println("1. Split the Tip - Database Enabled");
				System.out.println("2. Shortest Distance");
				System.out.println("3. Retirement Age");
				System.out.println("4. Body Mass Index - Database Enabled");
				System.out.println("5. Exit Program");
			}

			try{
				selection = in.nextInt();
			}
			catch(Exception e){
				e.printStackTrace();
				selection = 5;
			}


			if (selection == 1) {
				System.out.println("\nYou have selected Split the Tip");

				//output previous satabase entries for split the tip
				System.out.println("These are the previous entries for this program:");
				try{
					rs = statement.executeQuery("SELECT * FROM splitTheTip");
					while (rs.next()) {
						System.out.println("TimeStamp: " + rs.getString("createdAt") + ", Dinner Amount: " + rs.getDouble("dinnerAmount")
						+ ", Guests: " + rs.getInt("guests") + ", Cost Per Guest: " + rs.getDouble("costPerGuest")
						+ ", Remainder: " + rs.getDouble("remainder"));
					}
				}
				catch(SQLException e){
					e.printStackTrace();
					System.out.println("Could not connect to Split the Tip table in the database.");
				}

				System.out.print("\nEnter the total bill: ");
				double bill = in.nextDouble();
				System.out.print("Enter the number of guests: ");
				int guests = in.nextInt();
				double[] ret = ppa1.splitTheTipDB(bill, guests, connection);

				// decipher return
				if (ret[0] == -1)
					System.out.println("There cannot be zero guests.\n");
				else
					System.out.println("Each person pays $" + ret[0] + " and must unequally split a remainder of $" + ret[1] + ".\n");
			} else if (selection == 2) {
				System.out.println("\nYou have selected Shortest Distance.");
				System.out.print("x1: ");
				double x1 = in.nextDouble();
				System.out.print("y1: ");
				double y1 = in.nextDouble();
				System.out.print("x2: ");
				double x2 = in.nextDouble();
				System.out.print("y2: ");
				double y2 = in.nextDouble();
				double ret = ppa1.shortestDistance(x1, y1, x2, y2);
				System.out.println("Shortest distance between (" + x1 + ", " + y1 + ") and (" + x2 + ", " + y2 + ") is "
						+ ret + ".\n");
			} else if (selection == 3) {
				System.out.println("\nYou have selected Retirement Age Calculator.");
				System.out.print("Enter your current age: ");
				int age = in.nextInt();
				System.out.print("Enter your annual salary: ");
				double sal = in.nextDouble();
				System.out.print("Enter the percentage saved (as a percent not decimal): ");
				double percent = in.nextDouble();
				System.out.print("Enter your savings goal: ");
				double goal = in.nextDouble();
				double ret = ppa1.retirementAge(age, sal, percent, goal);

				// decipher return
				if (ret == -1)
					System.out.println("Your age must be above 16 or below 100.\n");
				else if (ret == -2)
					System.out.println("Your salary must be above $0.00.\n");
				else if (ret == -3)
					System.out.println("You must save something to be eligable to retire.\n");
				else if (ret == -4)
					System.out.println("You cannot save negative dollars.\n");
				else if (ret == 101)
					System.out.println("You will die before you meet your retirement goal at this rate.\n");
				else
					System.out.println("You will meet your retirement goal at the age of " + ret + ".\n");

			} else if (selection == 4) {
				System.out.println("\nYou have selected Body Mass Index.");

				//output previous satabase entries for bmi
				System.out.println("These are the previous entries for this program:");
				try{
					rs = statement.executeQuery("SELECT * FROM bodymass");
					while (rs.next()) {
						System.out.println("TimeStamp: " + rs.getString("createdAt") + ", Feet: " + rs.getInt("feet")
						+ ", Inches: " + rs.getInt("inches") + ", Weight: " + rs.getDouble("weight")
						+ ", Bmi: " + rs.getDouble("bmi") + ", Bodytype: " + rs.getString("bodytype"));
					}
				}
				catch(SQLException e){
					e.printStackTrace();
					System.out.println("Could not connect to Bodymass table in the database.");
				}

				System.out.print("\nEnter your height in feet: ");
				int feet = in.nextInt();
				System.out.print("Enter your height in inches: ");
				int inches = in.nextInt();
				System.out.print("Enter your weight in pounds: ");
				double lbs = in.nextDouble();
				String ret = ppa1.bodymassDB(feet, inches, lbs, connection);

				// Parse return string
				if (ret.equals("weightless"))
					System.out.println("You must enter a weight over or equal to 30 pounds.\n");
				else if (ret.equals("heightless"))
					System.out.println("You must enter a height over or equal to 2 feet.\n");
				else {
					int parseIndex = ret.indexOf('|');
					System.out.println("Your BMI is " + ret.substring(parseIndex + 1) + " which is considered "
							+ ret.substring(0, parseIndex) + ".\n");
				}

			} else if (selection != 5) {
				System.out.println("Please enter a valid option.\n");
			}

		} while (selection != 5);

		System.out.println("\nGoodbye!");

		in.close(); // close scanner
	}
}
