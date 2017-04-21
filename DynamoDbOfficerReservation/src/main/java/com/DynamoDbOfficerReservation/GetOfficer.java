package com.DynamoDbOfficerReservation;

import java.util.*;
import com.amazonaws.auth.profile.*;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.services.dynamodbv2.document.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class GetOfficer {
	static DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(new ProfileCredentialsProvider()));
	static String officerTableName = "Officers";
	static String officerFirstName;
	static String officerLastName;
	static String officerGender;
	static String officerDepartment;
	static TreeSet<Officers> matches;
	static OfficerTimes[] officerSchedule = new OfficerTimes[7];
	static String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

	public void getOfficerByLName(boolean multiple, String lastName) {
		TreeSet<Officers> officersTree = createOfficersTree(multiple);
		Iterator<Officers> it = officersTree.iterator();
		Officers temp;
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getlName() != null) {
				if (temp.getlName().equalsIgnoreCase(lastName)) {
					matches.add(temp);
				}
			}
		}
	}

	public void getOfficerByMultiple(String theName, int theGender, int theDepartment, int theDay, int[] theTime) {
		boolean multiple = false;
		if (theName != null) {
			getOfficerByLName(multiple, theName);
			multiple = true;
		}
		if (theGender != 3) {
			getOfficerByGender(multiple, theGender);
			multiple = true;
		}
		if (theDepartment != 4) {
			getOfficerByDepartment(multiple, theDepartment);
			multiple = true;
		}
		if (theDay != 8) {
			getOfficerByDayOfWeek(multiple, theDay);
			multiple = true;
		}
		if (theTime[0] != -1 && theTime[1] != -1) {
			getOfficerByTimeFrame(multiple, theTime);
			multiple = true;
		}
	}

	public void getOfficerByGender(boolean multiple, int theGender) {
		String gender;
		if (theGender == 1) {
			gender = "Female";
		} else {
			gender = "Male";
		}
		TreeSet<Officers> officersTree = createOfficersTree(multiple);
		Iterator<Officers> it = officersTree.iterator();
		Officers temp;
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getGender() != null) {
				if (temp.getGender().equalsIgnoreCase(gender)) {
					matches.add(temp);
				}
			}
		}
	}

	public void getOfficerByDepartment(boolean multiple, int theDepartment) {
		String department;
		if (theDepartment == 1) {
			department = "State";
		} else if (theDepartment == 2) {
			department = "County";
		} else {
			department = "Local";
		}
		TreeSet<Officers> officersTree = createOfficersTree(multiple);
		Iterator<Officers> it = officersTree.iterator();
		Officers temp;
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getDepartment() != null) {
				if (temp.getDepartment().equalsIgnoreCase(department)) {
					matches.add(temp);
				}
			}
		}
	}

	public void getOfficerByDayOfWeek(boolean multiple, int theDay) {
		String day = daysOfWeek[theDay - 1];
		TreeSet<Officers> officersTree = createOfficersTree(multiple);
		Iterator<Officers> it = officersTree.iterator();
		Officers temp;
		JSONParser parser = new JSONParser();
		boolean flag;
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getScheduleJson() != null) {
				try {
					Object obj1 = parser.parse(temp.getScheduleJson());
					JSONObject accessor = (JSONObject) obj1;
					flag = findDay(accessor, day);
					if (flag) {
						matches.add(temp);
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public boolean findDay(JSONObject theAccessor, String theDay) {
		int found = 0;
		for (String daysOfWeek1 : daysOfWeek) {
			JSONArray dayOfTheWeek = (JSONArray) theAccessor.get(daysOfWeek1);
			if (dayOfTheWeek != null) {
				if (daysOfWeek1.equalsIgnoreCase(theDay)) {
					String start = ((JSONObject) dayOfTheWeek.get(0)).get("Start_Time").toString();
					String end = ((JSONObject) dayOfTheWeek.get(0)).get("End_Time").toString();
					if (!start.equalsIgnoreCase("0") && !end.equalsIgnoreCase("0") || !start.equalsIgnoreCase(end)) {
						found++;
					}
				}
			}
		}
		return found > 0;
	}

	public void getOfficerByTimeFrame(boolean multiple, int[] theTime) {
		TreeSet<Officers> officersTree = createOfficersTree(multiple);
		Iterator<Officers> it = officersTree.iterator();
		Officers temp;
		JSONParser parser = new JSONParser();
		boolean flag;
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getScheduleJson() != null) {
				try {
					Object obj1 = parser.parse(temp.getScheduleJson());
					JSONObject accessor = (JSONObject) obj1;
					flag = findTimeFrame(accessor, theTime);
					if (flag) {
						matches.add(temp);
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public boolean findTimeFrame(JSONObject theAccessor, int[] theTime) {
		int found = 0, startTime = 0, endTime = 0;
		for (String daysOfWeek1 : daysOfWeek) {
			JSONArray dayOfTheWeek = (JSONArray) theAccessor.get(daysOfWeek1);
			if (dayOfTheWeek != null) {
				startTime = Integer.parseInt(((JSONObject) dayOfTheWeek.get(0)).get("Start_Time").toString());
				endTime = Integer.parseInt(((JSONObject) dayOfTheWeek.get(0)).get("End_Time").toString());
				if (startTime != endTime && startTime != theTime[1] && endTime != theTime[0]) {
					if (theTime[0] > theTime[1]) {
						if (startTime > endTime) {
							if (startTime < 2400 && 0 < endTime) {
								if (startTime <= theTime[0] && theTime[0] <= 2400 || startTime < theTime[1] && theTime[1] <= 2400 || 0 <= theTime[0]
										&& theTime[0] < endTime || 0 <= theTime[1] && theTime[1] <= endTime || 0 <= endTime && endTime <= theTime[1]) {
									found++;
								}
							}
						} else if (theTime[0] < 2400 && 0 < theTime[1]) {
							if (theTime[0] <= startTime && startTime <= 2400 || theTime[0] <= endTime && endTime <= 2400 || 0 <= startTime
									&& startTime < theTime[1] || 0 <= endTime && endTime <= theTime[1]) {
								found++;
							}
						} else {
							if (theTime[0] == 2400) {
								if (0 <= startTime && startTime < theTime[1] || 0 < endTime && endTime <= theTime[1]) {
									found++;
								}
							} else if (theTime[1] == 0) {
								if (theTime[0] < endTime && endTime <= 2400 || theTime[0] <= startTime && startTime < 2400) {
									found++;
								}
							}
						}
					} else {
						if (startTime > endTime) {
							if (startTime < 2400 && 0 < endTime) {
								if (startTime <= theTime[0] && theTime[0] < 2400 || startTime < theTime[1] && theTime[1] <= 2400 || 0 <= theTime[0]
										&& theTime[0] < endTime || 0 <= theTime[1] && theTime[1] <= endTime) {
									found++;
								}
							}
						} else if (theTime[0] <= startTime && startTime < theTime[1] || theTime[0] < endTime && endTime <= theTime[1]
								|| startTime <= theTime[0] && theTime[0] < endTime || startTime < theTime[1] && theTime[1] < startTime) {
							found++;
						}
					}
				}
			}
		}
		return found > 0;
	}

	public void printMatches() {
		Iterator<Officers> it = matches.iterator();
		Officers temp;
		System.out.println();
		while (it.hasNext()) {
			temp = it.next();
			System.out.println(temp);
		}
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

	public TreeSet<Officers> createOfficersTree(boolean multiple) {
		TreeSet<Officers> tree;
		if (!multiple) {
			Table table = dynamoDB.getTable(officerTableName);
			ItemCollection<ScanOutcome> items = table.scan(null, null, null, null);
			Iterator<Item> iterator = items.iterator();
			tree = new TreeSet<>();
			while (iterator.hasNext()) {
				Officers tmp = createOfficerObject(iterator.next().toJSONPretty());
				if (tmp != null) {
					tree.add(tmp);
				}
			}
		} else {
			tree = (TreeSet) matches.clone();
		}
		matches = new TreeSet<>();
		return tree;
	}
}
