import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class APPs {
	private Connection conDB;
	private String url;

	private int custID;
	private String custName;
	private String city;
	private String cat;
	private String title;
	private int year;
	private String club;
	private double total;

	public void APPS() {

	}

	public boolean findCust() {
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.
		ResultSet answers = null; // A cursor.

		boolean result = false; // Return.

		queryText = "SELECT * " + "FROM yrb_customer " + "WHERE cid = " + custID;
		try {
			querySt = conDB.prepareStatement(queryText);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}
		try {6
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}
		try {
			if (answers.next()) {
				result = true;
				custName = answers.getString("name");
				city = answers.getString("city");
			} else {
				custName = null;
				city = null;
				result = false;
			}
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in cursor.");
			System.out.println(e.toString());
			System.exit(0);
		}
		// Close the cursor.
		try {
			answers.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing cursor.\n");
			System.out.println(e.toString());
			System.exit(0);
		}

		// We're done with the handle.
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		return result;
	}

	public boolean update(String field, String value) {
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.

		queryText = "UPDATE yrb_customer" + " SET " + field + " = " + "'" + value + "'" + " WHERE cid = " + custID;
		try {
			querySt = conDB.prepareStatement(queryText);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}

		try {
			int a = querySt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}

		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		return true;
	}

	public boolean showCat() {
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.
		ResultSet answers = null; // A cursor.

		boolean result = false;

		queryText = "SELECT * " + "FROM yrb_category ";
		try {
			querySt = conDB.prepareStatement(queryText);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}
		try {
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}
		try {
			while (answers.next()) {
				System.out.println(answers.getString("cat"));
			}
			result = true;
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in cursor.");
			System.out.println(e.toString());
			System.exit(0);
		}
		return result;
	}

	public boolean chooseBook() {
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.
		ResultSet answers = null; // A cursor.

		boolean result = false;

		queryText = "SELECT title, year, language, weight FROM yrb_book WHERE " + "title = " + "'" + title + "'"
				+ " and cat = " + "'" + cat + "'";
		try {
			querySt = conDB.prepareStatement(queryText);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}
		try {
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("cat or title DNE, please try again");
			System.out.println();
			answers = null;
			result = false;
		}
		try {
			while (answers.next()) {
				title = answers.getString("title");
				year = answers.getInt("year");
				String language = answers.getString("language");
				int weight = answers.getInt("weight");
				System.out.println(
						"TITLE : " + title + " YEAR : " + year + " LANGUAGE : " + language + " WEIGHT : " + weight);
				result = true;
			}

		} catch (SQLException e) {
			System.out.println("SQL#1 failed in cursor.");
			System.out.println(e.toString());
			System.exit(0);
		}
		return result;
	}

	public double getMin() {
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.
		ResultSet answers = null; // A cursor.

		double result = 0.0;

		queryText = "select * from yrb_offer where price = (" + "select min(T.price) as min_price from "
				+ "(select o.club, o.title, o.year, o.price " + "from yrb_member m, yrb_offer o "
				+ "where o.club = m.club and m.cid = " + custID + " and o.title = " + "'" + title + "'"
				+ " and o.year = " + year + ") T)";

		try {
			querySt = conDB.prepareStatement(queryText);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}
		try {
			answers = querySt.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}
		try {
			if (answers.next()) {
				result = answers.getDouble("price");
				club = answers.getString("club");
			}

		} catch (SQLException e) {
			System.out.println("SQL#1 failed in cursor.");
			System.out.println(e.toString());
			System.exit(0);
		}
		return result;
	}

	public boolean setPurchase(String time, int q) {
		String queryText = ""; // The SQL text.
		PreparedStatement querySt = null; // The query handle.

		queryText = "insert into yrb_purchase values " + "(" + custID + ", " + "'" + club + "'" + ", " + "'" + title
				+ "'" + ", " + year + ", " + "'" + time + "'" + ", " + q + ")";

		try {
			querySt = conDB.prepareStatement(queryText);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}

		try {
			int a = querySt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute");
			System.out.println(e.toString());
			System.exit(0);
		}
		try {
			querySt.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing the handle.\n");
			System.out.println(e.toString());
			System.exit(0);
		}
		return true;
	}

	public static void main(String[] args) {
		APPs app = new APPs();
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.exit(0);
		}
		app.url = "jdbc:db2:c3421a";
		try {
			app.conDB = DriverManager.getConnection(app.url);
		} catch (SQLException e) {
			System.out.print("\nSQL: database connection error.\n");
			System.out.println(e.toString());
			System.exit(0);
		}

		// main program
		boolean check = false;
		// find the customer
		while (!check) {
			System.out.println("Please enter customer ID (an integer) :");
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();
			app.custID = Integer.parseInt(input);
			if (!app.findCust()) {
				System.out.println();
				System.out.println("Customer DNE, please try again.");
				System.out.println();
			} else {
				check = true;
				System.out.println();
				System.out.println("Customer Information :");
				System.out.println("SID : " + app.custID);
				System.out.println("Name : " + app.custName);
				System.out.println("City : " + app.city);
				System.out.println();
			}
		}
		// choose updates
		check = false;
		while (!check) {
			System.out.println("Would you like to update your information ? (y/n)");
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();
			if (input.equals("n")) {
				check = true;
				System.out.println();
				System.out.println("No changes made, lets move on.");
				System.out.println();
			} else if (input.equals("y")) {
				System.out.println("Please enter a field to be updated :");
				System.out.println("Fields : name city");
				Scanner sc2 = new Scanner(System.in);
				String input2 = sc2.nextLine();
				System.out.println();
				System.out.println("Please enter the new " + input2 + " :");
				Scanner sc3 = new Scanner(System.in);
				String input3 = sc3.nextLine();
				System.out.println();
				app.update(input2, input3);
				System.out.println("Update of " + input2 + " with " + input3 + " success!");
				if (input2.equals("name")) {
					app.custName = input3;
				} else {
					app.city = input3;
				}
				System.out.println("New Customer Information :");
				System.out.println("SID : " + app.custID);
				System.out.println("Name : " + app.custName);
				System.out.println("City : " + app.city);

			}
		}
		// show all cats, select a cat
		check = false;
		while (!check) {
			System.out.println();
			System.out.println("Please select a category :");
			app.showCat();
			System.out.println();
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();
			app.cat = input;
			System.out.println();
			System.out.println("Your choice of category : " + app.cat);

			// choose title
			System.out.println();
			System.out.println("Please enter book title :");
			Scanner sc2 = new Scanner(System.in);
			String input2 = sc.nextLine();
			app.title = input2;
			System.out.println();
			System.out.println("Your choice of title : " + app.title);
			System.out.println();
			if (app.chooseBook()) {
				check = true;
			} else {
				System.out.println("cat or title DNE, please try again");
				check = false;
			}

		}
		// choose book, give price
		check = false;
		while (!check) {
			System.out.println();
			System.out.println("Please enter the year of the book which you want to choose : ");
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();
			app.year = Integer.parseInt(input);
			System.out.println();
			System.out.println("Your final choice is : ");
			System.out.println("" + app.title + " " + app.year);
			System.out.println();
			double minPrice = app.getMin();
			System.out.println("Here is the minimam price of the book : " + minPrice);
			System.out.println("Please enter quantity : ");
			Scanner sc2 = new Scanner(System.in);
			String input2 = sc2.nextLine();
			int quantity = Integer.parseInt(input2);
			app.total = minPrice * quantity;
			System.out.println();
			System.out.println("Payment will be : " + app.total);
			System.out.println("Do you wish to purchase ? (y/n)");
			Scanner sc3 = new Scanner(System.in);
			String input3 = sc3.nextLine();
			if (input3.equals("n")) {
				check = false;
				System.out.println("Have a Nice day !");
			} else {
				Date date = new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String t = ft.format(date);
				app.setPurchase(t, quantity);
				check = true;
				System.out.println();
				System.out.println("Purchase success!");
			}
		}

	}
}