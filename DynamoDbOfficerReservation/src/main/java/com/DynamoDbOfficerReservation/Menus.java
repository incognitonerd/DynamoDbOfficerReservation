package com.DynamoDbOfficerReservation;

import java.util.*;

public class Menus {
	static Scanner kb = new Scanner(System.in);

	public int mainMenu() {
		int userSelection = 0;
		while (true) {
			try {
				System.out.println("       Main Menu");
				System.out.println("1: Add officer");
				System.out.println("2: Get officer's information");
				System.out.println("3. Delete Officer");
				System.out.println("4. Edit Officer");
				System.out.println("5. Display database");
				System.out.println("6. Exit");
				System.out.print("Select An Option: ");
				userSelection = kb.nextInt();
				if (userSelection < 1 || userSelection > 6) {
					System.out.println("Incorrect Selection!");
					System.out.println("Please Enter 1, 2, 3, 4, 5, or 6\n");
				} else {
					return userSelection;
				}
			} catch (InputMismatchException e) {
				kb.nextLine();
				System.out.println("Incorrect Selection!");
				System.out.println("Please Enter 1, 2, 3, 4, 5, or 6\n");
			}
		}
	}

	public int searchMenu() {
		int userSelection = 0;
		while (true) {
			try {
				System.out.println("\n       Search Options");
				System.out.println("1: Search Officer By Last Name");
				System.out.println("2: Search Officer By Gender");
				System.out.println("3: Search Officer By Department");
				System.out.println("4. Search Officer By Day Of The Week");
				System.out.println("5. Search Officer By Time Frame");
				System.out.println("6. Search Officer By Multiple");
				System.out.println("7. Exit");
				System.out.print("Select An Option: ");
				userSelection = kb.nextInt();
				if (userSelection < 1 || userSelection > 7) {
					System.out.println("Invalid Selection!");
					System.out.println("Please Enter 1, 2, 3, 4, 5, 6, or 7");
				} else {
					return userSelection;
				}
			} catch (InputMismatchException e) {
				kb.nextLine();
				System.out.println("Incorrect Selection!");
				System.out.println("Please Enter 1, 2, 3, 4, 5, 6, or 7");
			}
		}
	}

	public boolean addOfficerMenu() {
		int userSelection = 0;
		while (true) {
			try {
				System.out.println("\n\tAdd Another Officer");
				System.out.println("1. Yes");
				System.out.println("2. No");
				System.out.print("Would you like to add another officer: ");
				userSelection = kb.nextInt();
				if (userSelection < 1 || userSelection > 2) {
					System.out.println("Incorrect Selection!");
					System.out.println("Please Enter 1 or 2\n");
				} else if (userSelection == 1) {
					System.out.println("");
					return true;
				} else {
					System.out.println("");
					return false;
				}
			} catch (InputMismatchException e) {
				kb.nextLine();
				System.out.println("Incorrect Selection!");
				System.out.println("Please Enter 1 or 2\n");
			}
		}
	}
}
