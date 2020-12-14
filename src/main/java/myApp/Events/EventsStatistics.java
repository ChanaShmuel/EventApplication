package myApp.Events;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

public class EventsStatistics {
    public static HashMap<String, Integer> eventsTypeCounter = new HashMap<>();
    public static HashMap<String, Integer> eventsWordsCounter = new HashMap<>();
    public static TreeMap<String, Event> eventsLast60sec = new TreeMap<>();
    public static int timeStamp60Start = 0;
    public static int counter = 0;

    public static void addEvent(Event event){
        String eventType = event.getEventType();
        String eventData = event.getData();
        int eventTimestamp = event.getTimestamp();

        eventsTypeCounter.merge(eventType, 1, Integer::sum);
        eventsWordsCounter.merge(eventData, 1, Integer::sum);

        // the Bonus function
        last60SecEvents(eventTimestamp,event);
    }

    // Bonus: group last 60sec events
    private static void last60SecEvents(int time, Event event)
    {
        // convert timestamp to time
        String timeFormat = convertTimestamp(time) + "." + counter;
        counter++;

        // add first event
        if (timeStamp60Start == 0) {
            timeStamp60Start = time;
            eventsLast60sec.put(timeFormat,event);

        // more than 60 sec - clean data!
        } else if ((time - 60) > timeStamp60Start) {
            eventsLast60sec.clear();
            timeStamp60Start = counter = 0;
            last60SecEvents(time,event);

        // add all the other follow 60 sec events
        } else {
            eventsLast60sec.put(timeFormat,event);
        }
    }

    private static String convertTimestamp(int timeStamp)
    {
        String dateAsText = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date(timeStamp * 1000L));

        return dateAsText;
    }
}
