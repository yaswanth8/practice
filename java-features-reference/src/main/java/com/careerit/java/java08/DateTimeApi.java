package com.careerit.java.java08;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Java 8 - java.time (JSR-310).
 *
 * All core types are immutable and thread-safe.
 *
 *   LocalDate       - date only (no time, no zone)
 *   LocalTime       - time only
 *   LocalDateTime   - date + time, no zone
 *   ZonedDateTime   - date + time + zone (use for user-facing timestamps)
 *   Instant         - machine timestamp (UTC, epoch-based) - use for logging/storage
 *   Period          - years/months/days (date-based)
 *   Duration        - seconds/nanos (time-based)
 */
public class DateTimeApi {

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate dob = LocalDate.of(1990, Month.JUNE, 15);
        Period age = Period.between(dob, today);
        System.out.println("age = " + age.getYears() + "y " + age.getMonths() + "m");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plus90Days = now.plusDays(90);
        System.out.println("now + 90d = " + plus90Days);

        ZonedDateTime nyc = ZonedDateTime.now(ZoneId.of("America/New_York"));
        ZonedDateTime ist = nyc.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
        System.out.println("NYC = " + nyc + " | IST = " + ist);

        Instant start = Instant.now();
        Instant end   = start.plus(Duration.ofMinutes(5));
        System.out.println("millis between = " + Duration.between(start, end).toMillis());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("formatted = " + now.format(fmt));

        LocalDate nextFriday = today.with(java.time.temporal.TemporalAdjusters.next(DayOfWeek.FRIDAY));
        System.out.println("next Friday = " + nextFriday);

        long daysUntilNewYear = ChronoUnit.DAYS.between(today, LocalDate.of(today.getYear() + 1, 1, 1));
        System.out.println("days until new year = " + daysUntilNewYear);

        LocalTime noon = LocalTime.NOON;
        System.out.println("noon + 90 min = " + noon.plusMinutes(90));
    }
}
