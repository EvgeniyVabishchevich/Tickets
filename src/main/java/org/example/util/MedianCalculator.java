package org.example.util;

import org.example.Ticket;

import java.util.List;

public class MedianCalculator {
    public static int calculateMedian(List<Ticket> tickets) {
        if (tickets.size() % 2 == 0) {
            return (tickets.get(tickets.size() / 2).getPrice() + tickets.get(tickets.size() / 2 - 1).getPrice()) / 2;
        } else {
            return tickets.get(tickets.size() / 2).getPrice();
        }
    }
}
