package HW;

import java.util.Scanner;

public class ppa1Interface {

	public static void main(String[] args) {
		// Initialize function object and scanner
		ppa1Function ppa1 = new ppa1Function();
		Scanner in = new Scanner(System.in);

		// Welcome Statement
		System.out.println("Welcome to the PPA1 Function Interface. Please select a function or terminate the program.\n");

		// Loop to control interface interaction
		int selection = 0;
		do {
			System.out.println("Please enter the number for which function you would like to use.");
			System.out.println("1. Split the Tip");
			System.out.println("2. Shortest Distance");
			System.out.println("3. Retirement Age");
			System.out.println("4. Body Mass Index");
			System.out.println("5. Exit");

			try{
				selection = in.nextInt();
			}
			catch(Exception e){
				e.printStackTrace();
				selection = 5;
			}


			if (selection == 1) {
				System.out.println("\nYou have selected Split the Tip");
				System.out.print("Enter the total bill: ");
				double bill = in.nextDouble();
				System.out.print("Enter the number of guests: ");
				int guests = in.nextInt();
				double[] ret = ppa1.splitTheTip(bill, guests);

				// decipher return
				if (ret[0] == -1)
					System.out.println("There cannot be zero guests.\n");
				else
					System.out.println("Each person pays $" + ret[0] + " and must unequally split a remainder of $"
							+ ret[1] + ".\n");
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
				System.out.print("Enter your height in feet: ");
				int feet = in.nextInt();
				System.out.print("Enter your height in inches: ");
				int inches = in.nextInt();
				System.out.print("Enter your weight in pounds: ");
				double lbs = in.nextDouble();
				String ret = ppa1.bodymass(feet, inches, lbs);

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









/*
	public static void main(String[] args) {

		try {
    	Class.forName("org.postgresql.Driver");
    }
		catch (ClassNotFoundException e) {
    	e.printStackTrace();
    }

		try{
			Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.99.100:5432/ppa2db", "postgres", "password");

			ppa1Function ppa2 = new ppa1Function();
			double[] ans = ppa2.splitTheTipDB(10.00, 2, connection);
			String ret = ppa2.bodymassDB(0, 2, 192.17, connection);
			System.out.println(ret);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM bodymass");
			while (resultSet.next()) {
				System.out.println("id: " + resultSet.getInt("id") + ", feet: " + resultSet.getInt("feet")
				+ ", inches: " + resultSet.getInt("inches") + ", weight: " + resultSet.getDouble("weight")
				+ ", bmi: " + resultSet.getDouble("bmi") + ", bodytype: " + resultSet.getString("bodytype"));
			}
		}
		catch(SQLException e) {
			System.out.println("whoops");
			e.printStackTrace();
		}

	}
}
*/
