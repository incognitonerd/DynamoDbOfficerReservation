package com.DynamoDbOfficerReservation;

public class RunApp {
	public static void main(String[] args) throws Exception {
		boolean flag = true;
		while (flag) {
			Menus menu = new Menus();
			int userSelection = menu.mainMenu();
			if (userSelection == 1) {
				EnterData data = new EnterData();
				data.enterOfficerInfo();
			} else if (userSelection == 2) {
				new SearchOfficer();
			} else if (userSelection == 3) {
				new DeleteOfficer();
			} else if (userSelection == 4) {
				new EditOfficer();
			} else if (userSelection == 5) {
				DisplayDatabase db = new DisplayDatabase();
				db.display();
			} else {
				System.out.println("GoodBye!\n");
				return;
			}
		}
	}
}
