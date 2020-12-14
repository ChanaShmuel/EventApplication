package myApp.Rest;

import myApp.Events.EventsStatistics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class EventController {
        @GetMapping("/event")
        public Map show() {
            Map<String, Object> data = new TreeMap<>();

            data.put("1. event per type:", EventsStatistics.eventsTypeCounter);
            data.put("2. words per event:", EventsStatistics.eventsWordsCounter);
            data.put("3. last 60 sec event:", EventsStatistics.eventsLast60sec);

            return data;
        }
}