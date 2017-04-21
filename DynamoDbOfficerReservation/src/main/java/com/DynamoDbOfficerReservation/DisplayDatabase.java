package com.DynamoDbOfficerReservation;

import java.util.*;
import com.amazonaws.services.dynamodbv2.document.*;

public class DisplayDatabase extends GetOfficer {
	public void display() {
		int total = 0;
		matches = new TreeSet();
		String tableName = officerTableName;
		Table table = dynamoDB.getTable(tableName);
		ItemCollection<ScanOutcome> items = table.scan(null, null, null, null);
		Iterator<Item> iterator = items.iterator();
		TreeSet<Officers> tree = new TreeSet<>();
		while (iterator.hasNext()) {
			tree.add(createOfficerObject(iterator.next().toJSONPretty()));
			total++;
		}
		Iterator<Officers> it = tree.iterator();
		Officers temp;
		System.out.println();
		while (it.hasNext()) {
			temp = it.next();
			if (temp != null) {
				System.out.println(temp);
			}
		}
		System.out.println("Database has " + total + " item(s).\n");
	}
}
