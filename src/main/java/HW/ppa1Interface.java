package HW;

import java.util.Scanner;
import java.sql.*;

public class ppa1Interface {
/*
	public static void main(String[] args) {
		//Initialize function object and scanner
		ppa1Function ppa1 = new ppa1Function();
		Scanner in = new Scanner(System.in);

		//Welcome Statement
		System.out.println("Welcome to the PPA1 Function Interface. Please select a function or terminate the program.\n");

		//Loop to control interface interaction
		int selection = 0;
		do {
			System.out.println("Please enter the number for which function you would like to use.");
			System.out.println("1. Split the Tip");
			System.out.println("2. Shortest Distance");
			System.out.println("3. Body Mass Index");
			System.out.println("4. Retirement Age");
			System.out.println("5. Exit");

			try {
				selection = in.nextInt();
			}
			catch(Exception e) {
				//exit upon input error
				selection = 5;
			}

			if(selection == 1) {
				System.out.println("\nYou have selected Split the Tip");
				System.out.print("Enter the total bill: ");
				double bill = in.nextDouble();
				System.out.print("Enter the number of guests: ");
				int guests = in.nextInt();
				double[] ret = ppa1.splitTheTip(bill, guests);

				//decipher return
				if(ret[0] == -1)
					System.out.println("There cannot be zero guests.\n");
				else
					System.out.println("Each person pays $" + ret[0] + " and must unequally split a remainder of $" + ret[1] + ".\n");
			}
			else if(selection == 2) {
				System.out.println("\nYou have selected Shortest Distance.");
				System.out.print("x1: ");
				double x1 = in.nextDouble();
				System.out.print("y1: ");
				double y1 = in.nextDouble();
				System.out.print("x2: ");
				double x2 = in.nextDouble();
				System.out.print("y2: ");
				double y2 = in.nextDouble();
				double ret = ppa1.shortestDistance(x1,y1,x2,y2);
				System.out.println("Shortest distance between (" + x1 + ", " + y1 + ") and (" + x2 + ", " + y2 + ") is " + ret + ".\n");
			}
			else if(selection == 3) {

			}
			else if(selection == 4) {

			}
			else if(selection != 5) {
				System.out.println("Please enter a valid option.\n");
			}

		}
		while(selection != 5);

		System.out.println("\nGoodbye!");

		in.close(); //close scanner
	}
*/
	public static void main(String[] args) {
			try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.99.100:5432/testdb", "postgres", "password")) {

					System.out.println("Java JDBC PostgreSQL Example");
					// When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within
					// the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver");

					System.out.println("Connected to PostgreSQL database!");
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT * FROM test_table");
					while (resultSet.next()) {
							System.out.println(resultSet.getInt("id"), resultSet.getInt("num"));
					}

			} /*catch (ClassNotFoundException e) {
					System.out.println("PostgreSQL JDBC driver not found.");
					e.printStackTrace();
			}*/ catch (SQLException e) {
					System.out.println("Connection failure.");
					e.printStackTrace();
			}
	}
}
