package com.DynamoDbOfficerReservation;

import com.amazonaws.auth.profile.*;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.services.dynamodbv2.document.*;
import java.util.*;
import org.json.simple.*;

public class DeleteOfficer {
	DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(new ProfileCredentialsProvider()));
	static String officerTableName = "Officers";
	static TreeSet<Officers> matches;
	static Scanner kb = new Scanner(System.in);

	public DeleteOfficer() {
		System.out.println("\n  Delete Officer By Id");
		String offId = getOfficerId();
		if (offId.charAt(0) != '%') {
			try {
				deleteOfficerById(offId);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public String getOfficerId() {
		String id;
		System.out.print("Enter Officer's Id (or % to exit): ");
		id = kb.next();
		if (id.charAt(0) == '%') {
			System.out.println("GoodBye!\n");
			return id;
		}
		return id;
	}

	public void deleteOfficerById(String theId) {
		matches = new TreeSet();
		Table table = dynamoDB.getTable(officerTableName);
		ItemCollection<ScanOutcome> items = table.scan(null, null, null, null);
		Iterator<Item> iterator = items.iterator();
		TreeSet<Officers> tree = new TreeSet<>();
		while (iterator.hasNext()) {
			Officers tmp = createOfficerObject(iterator.next().toJSONPretty());
			if (tmp != null) {
				tree.add(tmp);
			}
		}
		Iterator<Officers> it = tree.iterator();
		Officers temp;
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getId() != null) {
				if (temp.getId().equalsIgnoreCase(theId)) {
					matches.add(temp);
				}
			}
		}
		if (matches.size() > 0) {
			table.deleteItem("Id", theId);
			System.out.println(theId + " has been deleted.");
		} else {
			System.out.println(theId + " is not in the database.");
		}
		System.out.println();
	}

	public Officers createOfficerObject(String s) {
		Object obj1 = JSONValue.parse(s);
		JSONObject test1 = (JSONObject) obj1;
		Officers officer = null;
		try {
			officer = new Officers((String) test1.get("Id"), (String) test1.get("FirstName"), (String) test1.get("LastName"), (String) test1.get("Gender"),
					(String) test1.get("Department"), (String) test1.get("Schedule"));
		} catch (Exception e) {
		}
		return officer;
	}
}
