package com.DynamoDbOfficerReservation;

public class OfficerTimes {
	private int startTime = 0;
	private int endTime = 0;

	public OfficerTimes(int theStartTime, int theEndTime) {
		startTime = theStartTime;
		endTime = theEndTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "\nStart Time: " + startTime + "\nEnd Time: " + endTime;
	}
}
