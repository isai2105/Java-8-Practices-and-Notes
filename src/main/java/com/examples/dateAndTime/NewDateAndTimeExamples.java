package com.examples.dateAndTime;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class NewDateAndTimeExamples {

	public static void main(String[] args) {
		/* 1. LocalDate */
		LocalDate();
		/* 2. LocalTime */
		LocalTime();
		/* 3. LocalDateTime */
		LocalDateTime();
		/* 4. Instant */
		Instant();
		/* 5. Duration */
		Duration();
		/* 6. Period */
		Period();
		/* 7. TemporalAdjusters */
		TemporalAdjusters();
		customTemporalAdjuster();
		/* 8. Printing & Parsing date-time objects */
		printingAndParsing();
		creatingACustomFormatter();
		/* 9. Applying time zone to a point in time */
		applyATimezoneToATime();
		fixedOffset();
		usingAlternativeCalendarSystems();
	}
	
	/* 1. LocalDate */
	private static void LocalDate() {
		/* 1. Factory Methods */
		// Factory method from parameters
		LocalDate date0 = LocalDate.of(2014, 3, 18);
		// Factory method for current Date
		LocalDate today = LocalDate.now();
		// Factory method from parsing (an overloaded method allows specifying the format)
		LocalDate date1 = LocalDate.parse("2014-03-18");
		/* 2.Accessing the fields */
		// accessing those with available methods
		int year = date0.getYear();
		Month month = date0.getMonth();
		int day = date0.getDayOfMonth();
		DayOfWeek dow = date0.getDayOfWeek();
		int len = date0.lengthOfMonth();
		boolean leap = date0.isLeapYear(); 
		// access using ChronoField
		int year2 = date0.get(ChronoField.YEAR);
		int month2 = date0.get(ChronoField.MONTH_OF_YEAR);
		int day2 = date0.get(ChronoField.DAY_OF_MONTH);
	}
	
	/* 2. LocalTime */
	private static void LocalTime() {
		/* 1. Factory Methods */
		// Factory method from parameters
		LocalTime time0 = LocalTime.of(13, 45, 20);
		// Factory method for current Time
		LocalTime time1 = LocalTime.now();
		// Factory method from parsing (an overloaded method allows specifying the format)
		LocalTime time2 = LocalTime.parse("13:45:20");
		/* 2.Accessing the fields */
		// accessing those with available methods
		int hour = time0.getHour();
		int minute = time0.getMinute();
		int second = time0.getSecond();
		// access using ChronoField
		time0.get(ChronoField.HOUR_OF_DAY);
		time0.get(ChronoField.MINUTE_OF_HOUR);
		time0.get(ChronoField.SECOND_OF_MINUTE);
	}
	
	/* 3. LocalDateTime */
	private static void LocalDateTime() {
		LocalDate date = LocalDate.of(2014, 3, 18);
		LocalTime time = LocalTime.of(13, 45, 20);
		/* 1. Factory Methods */
		// - Specifying all the fields
		LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20);
		// - Combining LocalDate & LocalTime
		LocalDateTime dt2 = LocalDateTime.of(date, time);
		// - From LocalDate, specifying the LocalTime
		LocalDateTime dt3 = date.atTime(13, 45, 20);
		LocalDateTime dt4 = date.atTime(time);
		// - From LocalTime, specifying the LocalDate
		LocalDateTime dt5 = time.atDate(date);
		/* 2. Extracting the LocalDate or LocalTime from LocalDateTime */
		LocalDate date1 = dt1.toLocalDate();
		LocalTime time1 = dt1.toLocalTime();
	}
	
	/* 4. Instant */
	private static void Instant() {
		Instant.ofEpochSecond(3); // 3 seconds after Unix Epoch time (January 1, 1970 UTC.)
		Instant.ofEpochSecond(3, 0); // 3 seconds with nano adjustment of 0
		// One billion nanoseconds (1 second) after 2 seconds
		Instant.ofEpochSecond(2, 1_000_000_000);
		// One billion nanoseconds (1 second) before 4 seconds
		Instant.ofEpochSecond(4, -1_000_000_000); 
	}
	
	/* 5. Duration */
	private static void Duration() {
		/* Instantiation */
		// - By using the "between" method
		// Duration d1 = Duration.between(time1, time2);
		// Duration d2 = Duration.between(dateTime1, dateTime2);
		// Duration d3 = Duration.between(instant1, instant2);
	}
	
	/* 6. Period */
	private static void Period() {
		/* Instantiation */
		// - By using the "between" method
		Period tenDays0 = Period.between(LocalDate.of(2014, 3, 8),
				LocalDate.of(2014, 3, 18));
		// - By using the "of" method
		Period tenDays1 = Period.ofDays(10);
		Period threeWeeks = Period.ofWeeks(3);
		Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
		// there are other factory methods as well
	}
	
	/* 7. TemporalAdjusters */
	private static void TemporalAdjusters() {
		LocalDate date1 = LocalDate.of(2014, 3, 18);
		LocalDate date2 = date1.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
		LocalDate date3 = date2.with(TemporalAdjusters.lastDayOfMonth());
	}
	
	private static void customTemporalAdjuster() {
		// Custom Temporal Adjuster by implementing the interface (recommended way)
		LocalDate date1 = LocalDate.now();
		LocalDate nextWorkingDate = date1.with(new NextWorkingDayCustomTemporalAdjuster());
		System.out.println("Next Working Date from now: " + nextWorkingDate);
		
		// Custom Temporal Adjuster by using a Lambda Expression
		// For Lambda Expressions, it is recommended to use the "ofDateAdjuster(" method.
		TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(temporal -> {
			DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
			int dayToAdd = 1;
			if (dow == DayOfWeek.FRIDAY)
				dayToAdd = 3;
			if (dow == DayOfWeek.SATURDAY)
				dayToAdd = 2;
			return temporal.plus(dayToAdd, ChronoUnit.DAYS);
		});
		System.out.println("Next Working Date from now (with Lambda): " + date1.with(nextWorkingDay));	
	}
	
	/* 8. Printing & Parsing date-time objects */
	private static void printingAndParsing() {
		// 1. Format using predefined static instances (Thread Safe - immutable)
		LocalDate date = LocalDate.of(2014, 3, 18);
		String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
		String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate date1 = LocalDate.parse("20140318",
				DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate date2 = LocalDate.parse("2014-03-18",
				DateTimeFormatter.ISO_LOCAL_DATE);
		// 2. Format from a pattern
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date3 = LocalDate.of(2014, 3, 18);
		// convert to string
		String formattedDate = date1.format(formatter);
		// convert back to date
		LocalDate date4 = LocalDate.parse(formattedDate, formatter);
		// 3. Creating a LOCALIZED DateTimeFormatter
		DateTimeFormatter italianFormatter =
				DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
		LocalDate date5 = LocalDate.of(2014, 3, 18);
		String formattedDate1 = date.format(italianFormatter); // 18. marzo 2014
		LocalDate date6 = LocalDate.parse(formattedDate1, italianFormatter);
	}
	
	private static void creatingACustomFormatter() {
		// we are manually recreating the Italian Formatter used in the
		// previous example
		DateTimeFormatter italianFormatter = new DateTimeFormatterBuilder()
				.appendText(ChronoField.DAY_OF_MONTH)
				.appendLiteral(". ")
				.appendText(ChronoField.MONTH_OF_YEAR)
				.appendLiteral(" ")
				.appendText(ChronoField.YEAR)
				.parseCaseInsensitive()
				.toFormatter(Locale.ITALIAN);
	}
	
	/* 9. Applying time zone to a point in time */
	private static void applyATimezoneToATime() {
		ZoneId romeZone = ZoneId.of("Europe/Rome");
		
		LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
		ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
		
		LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
		ZonedDateTime zdt2 = dateTime.atZone(romeZone);
		
		Instant instant = Instant.now();
		ZonedDateTime zdt3 = instant.atZone(romeZone);
		
		// You can also convert an Instant to a LocalDateTime by 
		// using a ZoneId:
		Instant instant2 = Instant.now();
		LocalDateTime timeFromInstant = LocalDateTime.ofInstant(instant2, romeZone);
	}
	
	private static void fixedOffset() {
		ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
		LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
		OffsetDateTime dateTimeInNewYork = OffsetDateTime.of(dateTime, newYorkOffset);
	}
	
	private static void usingAlternativeCalendarSystems() {
		// creating an instance of one of these classes
		// out of a LocalDate.
		LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
		JapaneseDate japaneseDate = JapaneseDate.from(date);
		// you can explicitly create a calendar system for a specific Locale 
		// and create an instance of a date for that Locale.
		Chronology japaneseChronology = Chronology.ofLocale(Locale.JAPAN);
		ChronoLocalDate now = japaneseChronology.dateNow();
	}
	
}
