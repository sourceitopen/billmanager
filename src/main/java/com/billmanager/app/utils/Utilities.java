package com.billmanager.app.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Utilities {


	public static DateTime convertToDateTime(String enteredDate){
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dt = formatter.parseDateTime(enteredDate);
		return dt;
	}
	public static String convertDateTimeToString(DateTime date){
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		String convertedDate = format.print(date);
		return convertedDate;
	}
}
