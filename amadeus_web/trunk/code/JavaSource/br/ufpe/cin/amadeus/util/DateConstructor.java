package br.ufpe.cin.amadeus.util;

import java.util.Calendar;
import java.util.Date;

public class DateConstructor {

	public static Date today() {
		Calendar c = Calendar.getInstance();
		return getDate(c);
	}
	
	public static Date set(int year, int month, int date) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, date);
		return getDate(c);
	}
	
	private static Date getDate(Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
}
