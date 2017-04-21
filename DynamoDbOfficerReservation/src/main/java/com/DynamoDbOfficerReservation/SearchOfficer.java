package com.DynamoDbOfficerReservation;

import java.util.*;

public class SearchOfficer {
	static Scanner kb = new Scanner(System.in);
	static String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

	public SearchOfficer() {
		boolean multiple = false;
		Menus menu = new Menus();
		int searchBy = menu.searchMenu();
		GetOfficer retrieve = new GetOfficer();
		if (searchBy == 1) {
			System.out.println("\n  Search Officer By Last Name");
			String theName = searchByLName();
			if (theName.charAt(0) != '%') {
				try {
					retrieve.getOfficerByLName(multiple, theName);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		} else if (searchBy == 2) {
			System.out.println("\n  Search Officer By Gender");
			int theGender = searchByGender();
			if (theGender != 3) {
				try {
					retrieve.getOfficerByGender(multiple, theGender);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		} else if (searchBy == 3) {
			System.out.println("\n  Search Officer By Department");
			int theDepartment = searchByDepartment();
			if (theDepartment != 4) {
				try {
					retrieve.getOfficerByDepartment(multiple, theDepartment);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		} else if (searchBy == 4) {
			System.out.println("\n  Search Officer By Day Of The Week");
			int theDay = searchByDayOfWeek();
			if (theDay != 8) {
				try {
					retrieve.getOfficerByDayOfWeek(multiple, theDay);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		} else if (searchBy == 5) {
			System.out.println("\n  Search Officer By Time Frame");
			int[] theTime = searchByTimeFrame();
			if (theTime[0] != -1 && theTime[1] != -1) {
				try {
					retrieve.getOfficerByTimeFrame(multiple, theTime);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		} else if (searchBy == 6) {
			System.out.println("\n  Search Officer By Multiple");
			int[] theSearches = searchByMultiple();
			String theName = null;
			int theGender = 3;
			int theDepartment = 4;
			int theDay = 8;
			int[] theTime = { -1, -1 };
			if (theSearches[0] != -1) {
				if (theSearches[0] == 1) {
					theName = searchByLName();
					if (theName.charAt(0) == '%') {
						return;
					}
				}
				if (theSearches[1] == 1) {
					theGender = searchByGender();
					if (theGender == 3) {
						return;
					}
				}
				if (theSearches[2] == 1) {
					theDepartment = searchByDepartment();
					if (theDepartment == 4) {
						return;
					}
				}
				if (theSearches[3] == 1) {
					theDay = searchByDayOfWeek();
					if (theDay == 8) {
						return;
					}
				}
				if (theSearches[4] == 1) {
					theTime = searchByTimeFrame();
					if (theTime[0] == -1 && theTime[1] == -1) {
						return;
					}
				}
				try {
					retrieve.getOfficerByMultiple(theName, theGender, theDepartment, theDay, theTime);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		} else {
			System.out.println("GoodBye!\n");
			return;
		}
		if (retrieve.matches != null) {
			if (!retrieve.matches.isEmpty()) {
				retrieve.printMatches();
			}
			System.out.println(retrieve.matches.size() + " match(es) found\n");
		}
	}

	public String searchByLName() {
		String lastName;
		System.out.print("Enter Officer's Last Name (or % to exit): ");
		lastName = kb.next();
		if (lastName.charAt(0) == '%') {
			System.out.println("GoodBye!\n");
			return lastName;
		}
		return lastName;
	}

	public int[] searchByMultiple() {
		boolean flag = true;
		while (flag) {
			int[] multipleSearch = { 0, 0, 0, 0, 0 };
			String userSelection = "", searchBy = "";
			System.out.print("Search By Last name (y for yes, % to exit, or press any other key for NO)? ");
			userSelection = kb.next();
			if (userSelection.equalsIgnoreCase("y")) {
				multipleSearch[0] = 1;
				userSelection = "n";
				searchBy = searchBy.concat("Last name, ");
			} else if (userSelection.charAt(0) == '%') {
				System.out.println("GoodBye!\n");
				multipleSearch[0] = -1;
				return multipleSearch;
			}
			System.out.print("Search By Gender (y for yes, % to exit, or press any other key for NO)? ");
			userSelection = kb.next();
			if (userSelection.equalsIgnoreCase("y")) {
				multipleSearch[1] = 1;
				userSelection = "n";
				searchBy = searchBy.concat("Gender, ");
			} else if (userSelection.charAt(0) == '%') {
				System.out.println("GoodBye!\n");
				multipleSearch[0] = -1;
				return multipleSearch;
			}
			System.out.print("Search By Department (y for yes, % to exit, or press any other key for NO)");
			userSelection = kb.next();
			if (userSelection.equalsIgnoreCase("y")) {
				multipleSearch[2] = 1;
				userSelection = "n";
				searchBy = searchBy.concat("Department, ");
			} else if (userSelection.charAt(0) == '%') {
				System.out.println("GoodBye!\n");
				multipleSearch[0] = -1;
				return multipleSearch;
			}
			System.out.print("Search By Day Of The Week (y for yes, % to exit, or press any other key for NO)");
			userSelection = kb.next();
			if (userSelection.equalsIgnoreCase("y")) {
				multipleSearch[3] = 1;
				userSelection = "n";
				searchBy = searchBy.concat("Day Of The Week, ");
			} else if (userSelection.charAt(0) == '%') {
				System.out.println("GoodBye!\n");
				multipleSearch[0] = -1;
				return multipleSearch;
			}
			System.out.print("Search By Time Frame (y for yes, % to exit, or press any other key for NO)");
			userSelection = kb.next();
			if (userSelection.equalsIgnoreCase("y")) {
				searchBy = searchBy.concat("Time Frame ");
				multipleSearch[4] = 1;
				userSelection = "n";
			} else if (userSelection.charAt(0) == '%') {
				System.out.println("GoodBye!\n");
				multipleSearch[0] = -1;
				return multipleSearch;
			}
			if (searchBy.length() == 0) {
				System.out.println("User Did Not Select Any Search Options");
			} else {
				System.out.println("\nYou Would Like To Search By: \n" + searchBy);
			}
			System.out.print("Correct (y or press any key to repeat): ");
			userSelection = kb.next();
			if (userSelection.equalsIgnoreCase("y")) {
				return multipleSearch;
			}
			System.out.println();
		}
		int[] tmp = { 0 };
		return tmp;
	}

	public int searchByGender() {
		int userSelection;
		while (true) {
			try {
				System.out.println("1. Female");
				System.out.println("2. Male");
				System.out.println("3. Exit");
				System.out.print("Select An Option: ");
				userSelection = kb.nextInt();
				if (userSelection < 1 || userSelection > 3) {
					System.out.println("Incorrect Selection!");
					System.out.println("Please Enter 1, 2, or 3");
				} else if (userSelection == 3) {
					System.out.println("GoodBye!\n");
					return userSelection;
				} else {
					return userSelection;
				}
			} catch (InputMismatchException e) {
				kb.nextLine();
				System.out.println("Incorrect Selection!");
				System.out.println("Please Enter 1, 2, or 3");
			}
		}
	}

	public int searchByDepartment() {
		int userSelection;
		while (true) {
			try {
				System.out.println("1. State");
				System.out.println("2. County");
				System.out.println("3. Local");
				System.out.println("4. Exit");
				System.out.print("Select An Option: ");
				userSelection = kb.nextInt();
				if (userSelection < 1 || userSelection > 4) {
					System.out.println("Incorrect Selection!");
					System.out.println("Please Enter 1, 2, 3, or 4");
				} else if (userSelection == 4) {
					System.out.println("GoodBye!\n");
					return userSelection;
				} else {
					return userSelection;
				}
			} catch (InputMismatchException e) {
				kb.nextLine();
				System.out.println("Incorrect Selection!");
				System.out.println("Please Enter 1, 2, 3, or 4");
			}
		}
	}

	public int searchByDayOfWeek() {
		int userSelection;
		while (true) {
			try {
				System.out.println("1. Monday");
				System.out.println("2. Tuesday");
				System.out.println("3. Wednesday");
				System.out.println("4. Thursday");
				System.out.println("5. Friday");
				System.out.println("6. Saturday");
				System.out.println("7. Sunday");
				System.out.println("8. Exit");
				System.out.print("Select An Option: ");
				userSelection = kb.nextInt();
				if (userSelection < 1 || userSelection > 8) {
					System.out.println("Incorrect Selection!");
					System.out.println("Please Enter 1, 2, 3, 4, 5, 6, 7, or 8");
				} else if (userSelection == 8) {
					System.out.println("GoodBye!\n");
					return userSelection;
				} else {
					return userSelection;
				}
			} catch (InputMismatchException e) {
				kb.nextLine();
				System.out.println("Incorrect Selection!");
				System.out.println("Please Enter 1, 2, 3, 4, 5, 6, 7, or 8");
			}
		}
	}

	public int[] searchByTimeFrame() {
		int[] array = new int[2];
		System.out.println("Enter Time Frame (24-Hour Format)");
		System.out.println("E.g. 10:35pm = 2235 or 9:23am = 0923");
		boolean flag = true;
		while (flag) {
			while (flag) {
				try {
					System.out.print("Start Time (or -1 to exit): ");
					array[0] = kb.nextInt();
					if (array[0] == -1) {
						System.out.println("GoodBye!\n");
						return array;
					} else if (array[0] < 0 || array[0] > 2400) {
						System.out.println("Incorrect Selection!");
						System.out.println("Time Has To Be Between 0000 and 2400");
					} else {
						flag = false;
					}
				} catch (InputMismatchException e) {
					kb.nextLine();
					System.out.println("Incorrect Selection!");
					System.out.println("Time Has To Be Between 0000 and 2400");
				}
			}
			flag = true;
			while (flag) {
				try {
					System.out.print("End Time (or -1 to exit): ");
					array[1] = kb.nextInt();
					if (array[1] == -1) {
						System.out.println("GoodBye!\n");
						return array;
					} else if (array[1] < 0 || array[1] > 2400) {
						System.out.println("Incorrect Selection!");
						System.out.println("Time Has To Be Between 0000 and 2400");
					} else {
						flag = false;
					}
				} catch (InputMismatchException e) {
					kb.nextLine();
					System.out.println("Incorrect Selection!");
					System.out.println("Time Has To Be Between 0000 and 2400");
				}
			}
			if (array[0] == array[1]) {
				System.out.println("Start Time And End Time Cannot Be Equal");
				flag = true;
			} else if (array[0] == 2400 && array[1] == 0) {
				System.out.println("Invalid Time Frame!");
				System.out.println("Cannot search from " + array[0] + " to " + array[1] + ".");
				flag = true;
			}
		}
		return array;
	}
}
