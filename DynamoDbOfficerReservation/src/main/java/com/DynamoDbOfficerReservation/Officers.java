package com.DynamoDbOfficerReservation;

import org.json.simple.parser.*;
import org.json.simple.*;

public class Officers implements Comparable {
	private String fName;
	private String lName;
	private String gender;
	private String Id;
	private String department;
	private String scheduleJson;
	private String schedLegible = "";
	private final String[] daysAvailable = new String[7];
	private static final String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

	public Officers(String id, String first, String last, String gender, String theDepartment, String theSchedule) {
		this.Id = id;
		this.fName = first;
		this.lName = last;
		this.gender = gender;
		this.department = theDepartment;
		this.scheduleJson = theSchedule;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String afName) {
		fName = afName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String alName) {
		lName = alName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String aGender) {
		gender = aGender;
	}

	public String getId() {
		return Id;
	}

	public void setId(String aId) {
		Id = aId;
	}

	@Override
	public String toString() {
		if (scheduleJson != null) {
			JSONParser parser = new JSONParser();
			try {
				Object obj1 = parser.parse(scheduleJson);
				JSONObject accessor = (JSONObject) obj1;
				for (String daysOfWeek1 : daysOfWeek) {
					JSONArray dayOfTheWeek = (JSONArray) accessor.get(daysOfWeek1);
					if (dayOfTheWeek != null) {
						schedLegible = schedLegible.concat("\nDay Of The Week: " + daysOfWeek1 + "\nStart Time: "
								+ ((JSONObject) dayOfTheWeek.get(0)).get("Start_Time") + "\nEnd Time: " + ((JSONObject) dayOfTheWeek.get(0)).get("End_Time"));
					}
				}
			} catch (Exception e) {
			}
		}
		return "Last name: " + lName + "\nFirst Name: " + fName + "\nGender: " + gender + "\nDepartment: " + department + "\nId: " + Id + "\nSchedule: "
				+ schedLegible + "\n";
	}

	@Override
	public int compareTo(Object o) {
		try {
			Officers compared = ((Officers) o);
			if (this.getlName().equals(compared.getlName())) {
				return this.getfName().compareTo(compared.getfName());
			}
			return this.getlName().compareTo(compared.getlName());
		} catch (Exception e) {
		}
		return -1;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getScheduleJson() {
		return scheduleJson;
	}

	public void setScheduleJson(String scheduleJson) {
		this.scheduleJson = scheduleJson;
	}
}
