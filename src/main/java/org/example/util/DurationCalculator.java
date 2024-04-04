package org.example.util;

import org.example.Ticket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DurationCalculator {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy [H][HH]:mm");
    public static long getFlightDurationMinutes(Ticket ticket) {
        LocalDateTime departureDate = LocalDateTime.parse(ticket.getDepartureDate() + " " + ticket.getDepartureTime(),
                DATE_TIME_FORMATTER);
        LocalDateTime arrivalDate = LocalDateTime.parse(ticket.getArrivalDate() + " " + ticket.getArrivalTime(),
                DATE_TIME_FORMATTER);
        return ChronoUnit.MINUTES.between(departureDate, arrivalDate);
    }
}
