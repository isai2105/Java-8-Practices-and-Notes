package java8.notes.dateAndTime;

public class NewDateAndTimeNotes {

	/*
	 * IMPORTANT NOTE 1
	 * 
	 * The DateFormat isn’t thread-safe. 
	 * This means that if two threads try to parse a date using the same 
	 * formatter at the same time, you may receive unpredictable results.
	 * */
	
	/*
	 * IMPORTANT NOTE 2
	 * 
	 * Java8 integrates many of the Joda-Time features in the "java.time" 
	 * package.
	 * */
	
	/*
	 * IMPORTANT NOTE 3
	 * 
	 * LocalDate
	 * 
	 * immutable object, representing just a plain date without the time 
	 * of the day.
	 * 
	 * Note: WITHOUT THE TIME INFO
	 * */
	
	/*
	 * IMPORTANT NOTE 4
	 * 
	 * Instant: a date and time for MACHINES (not for humans)
	 * 
	 * represents the number of seconds passed since the Unix epoch time, 
	 * set by convention to midnight of January 1, 1970 UTC.
	 * 
		It consists of a number of seconds and nanoseconds. 
		As a consequence, it doesn’t provide any ability to
		handle units of time that are meaningful to humans (year, month, day, minute, etc).
		
		But you can work with Instant by using Duration & Period classes.
	 * */
	
	/*
	 * IMPORTANT NOTE 5
	 * 
	 * Duration
	 * 
	 * Amount of time measured in seconds and eventually nanoseconds
	 * The above is the reason why Duration does not get along with LocalDate,
	 * but it gets along with LocalTime & LocalDateTime.
	 * 
		Duration d1 = Duration.between(time1, time2);
		Duration d1 = Duration.between(dateTime1, dateTime2);
		Duration d2 = Duration.between(instant1, instant2);
	 * 
	 * */
	
	/*
	 * IMPORTANT NOTE 6
	 * 
	 * Period
	 * 
	 * To find out the difference between two dates.
	 * This gets along with LocalDate.
	 * 
		Period tenDays = Period.between(LocalDate.of(2014, 3, 8),
		LocalDate.of(2014, 3, 18));
	 * 
	 * */
	
	/*
	 * IMPORTANT NOTE 7
	 * 
	 * These objects are immutable, to allow Thread Safety.
	 * When "converting" those, by adding, subtracting .. we are creating new instances.
	 * 
	 * Meaning, the following code has no effect:
		date.withYear(2011);
	 * Because we are not assigning the new instance to a variable
	 * */
	
	/*
	 * IMPORTANT NOTE 8
	 * 
	 * TemporalAdjuster
	 * 
	 * To perform advanced operations such as adjusting a date to:
	 * next Sunday, next working date or the last day of the month.
	 * 
	 * You can create your own TemporalAdjuster by implementing the interface,
	 * in case non of the predefined adjusters fits your needs (for example, skip
	 * holidays based on a file, etc...)
	 * 
	 * 
	 * */
	
	/* 
	 * IMPORTANT NOTE 9
	 * 
	 * Dealing with TimeZones.
	 * 
	 * The new java.time.ZoneId class is the replacement for the old 
	 * java.util.TimeZone class.
	 * 
	 * ZoneId romeZone = ZoneId.of("Europe/Rome");
	 * The region IDs are all in the format “{area}/{city}”
	 * 
	 * You can also convert an old Time-Zone object to a ZoneId 
	 * by using the new method toZoneId: 
	 * ZoneId zoneId = TimeZone.getDefault().toZoneId();
	 * */
	
	/*
	 * IMPORTANT NOTE 10
	 *
	 * The Fixed Offset:
	 * Does not work with Daylight savings .. so its use is
	 * not recommended for the majority of scenarios.
	 * 
	 ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
	 * */
	
	/*
	 * IMPORTANT NOTE 11
	 * 
	 * Using alternative calendar systems:
	 * 
	 * four additional calendar systems are provided in Java 8: 
	 * ThaiBuddhistDate, MinguoDate, JapaneseDate, and Hijrah-Date (Islamic, most complex one, based on Lunar months).
	 * 
	 * It’s recommended to use LocalDate throughout your application,
	 * and only use ChronoLocalDate when need to localize the input or output
	 * of your program.
	 */
	
	/*
	 * IMPORTANT NOTE 12
	 * 
	 * Diff with previous Java Date API
	 * 
	 * 1. Java8 has immutable date-time objects (Thread Safe)
	 * 2. Two diff time representations for humans and machines
	 * 3. It is more robust and has less flaws (design, mutability, defaults, offsets, etc...)
	 * 4. With the new API, you can use calendar systems different from the ISO-8601 standard system.
	 */
}
