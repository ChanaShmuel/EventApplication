package myApp.rest;

import myApp.events.EventsStatistics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class EventController {
    @GetMapping("/event")
    public Map show() {
        EventsStatistics.removeEventsThatMoreThan_x_Sec();
        Map<String, Object> data = new TreeMap<>();

        data.put("1-event per type:/n", EventsStatistics.eventsTypeCounter);
        data.put("2-words per event:/n", EventsStatistics.eventsWordsCounter);
        data.put("3-last 60 sec eventType:/n", EventsStatistics.eventsTypeInLast60sec);
        data.put("4-last 60 sec eventData:/n", EventsStatistics.eventsDataInLast60sec);
        return data;
    }
}