package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.util.DurationCalculator;
import org.example.util.MedianCalculator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    private static final String TICKETS_FILE_NAME = "tickets.json";

    public static void main( String[] args ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input input = objectMapper.readValue(new File(TICKETS_FILE_NAME), Input.class);

        List<Ticket> matchingTickets = filterTicketsByCities(input);

        printShortestTicketForEachCarrier(matchingTickets);
        printDifferenceBtwnAvgPriceAndMedian(matchingTickets);
    }

    public static void printDifferenceBtwnAvgPriceAndMedian(List<Ticket> tickets) {
        double avgPrice = tickets.stream()
                .mapToInt(Ticket::getPrice)
                .average()
                .getAsDouble();

        int medianPrice = MedianCalculator.calculateMedian(tickets);

        System.out.println("\n");
        System.out.println("Average price - " + avgPrice);
        System.out.println("Median price - " + medianPrice);
        System.out.println("Difference - " + (avgPrice - medianPrice));
    }

    public static void printShortestTicketForEachCarrier(List<Ticket> tickets) {
        Map<String, Ticket> map = tickets.stream()
                .reduce(new HashMap<>(), App::putIfFirstOrFaster, (((stringTicketHashMap, stringTicketHashMap2) -> stringTicketHashMap2)));

        map.keySet().forEach(key -> System.out.println(key + "\n" + map.get(key)));
    }

    public static HashMap<String, Ticket> putIfFirstOrFaster(HashMap<String, Ticket> stringTicketMap, Ticket ticket) {
        if (!stringTicketMap.containsKey(ticket.getCarrier())) {
            stringTicketMap.put(ticket.getCarrier(), ticket);
        } else {
            if (DurationCalculator.getFlightDurationMinutes(stringTicketMap.get(ticket.getCarrier())) >
                    DurationCalculator.getFlightDurationMinutes(ticket)) {
                stringTicketMap.put(ticket.getCarrier(), ticket);
            }
        }
        return stringTicketMap;
    }

    public static List<Ticket> filterTicketsByCities(Input input) {
        return input.getTickets().stream()
                .filter(ticket -> ticket.getOriginName().equals("Владивосток")
                        && ticket.getDestinationName().equals("Тель-Авив"))
                .collect(Collectors.toList());
    }
}
