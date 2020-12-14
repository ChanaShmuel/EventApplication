package myApp.events;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventsStatistics {
    public static HashMap<String, Integer> eventsTypeCounter = new HashMap<>();
    public static HashMap<String, Integer> eventsWordsCounter = new HashMap<>();
    public static HashMap<String, Integer> eventsTypeInLast60sec = new HashMap<>();
    public static HashMap<String, Integer> eventsDataInLast60sec = new HashMap<>();
    public static TreeMap<String, Event> eventsLast60sec = new TreeMap<>();
    public static final int lastSec = 60;
    public static final int ONE_SEC = 1000; // 1000ms for 1sec
    public static int counter = 0;

    public static void addEvent(Event event){
        String eventType = event.getEventType();
        String eventData = event.getData();
        String curTimeStamp = currentTime();
        String timeFormat = curTimeStamp + "." + counter++; // create unique key

        eventsTypeCounter.merge(eventType, 1, Integer::sum);
        eventsWordsCounter.merge(eventData, 1, Integer::sum);
        eventsLast60sec.put(timeFormat, event); // add event to map
        eventsTypeInLast60sec .merge(eventType, 1, Integer::sum);
        eventsDataInLast60sec.merge(eventData, 1, Integer::sum);
    }


    public static void removeEventsThatMoreThan_x_Sec()
    {
        try {
            checkOldEvents2Delete(); // check and try delete event(s)
        } catch (Exception e) {
            // System.out.println(e);
        }
    }

    private static void checkOldEvents2Delete()
    {
        String key;
        Event event;
        String curTimeStamp = currentTime();

        // Get a set of the entries
        Set set = eventsLast60sec.entrySet();

        // Get an iterator
        Iterator it = set.iterator();

        // loop over map to found which events need remove
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry)it.next();
            key = (String)me.getKey();
            event=(Event)me.getValue();
            if (checkTimeDiff(key,curTimeStamp))
                eventsLast60sec.remove(key);
                if(eventsTypeInLast60sec.get(event.getEventType())>1){
                    int tmp=eventsTypeInLast60sec.get(event.getEventType());
                    eventsTypeInLast60sec.put(event.getEventType(),tmp-1);
                }else{
                    eventsTypeInLast60sec.remove(event.getEventType());
                }

            if( eventsDataInLast60sec.get(event.getData())>1){
                int tmp= eventsDataInLast60sec.get(event.getData());
                eventsDataInLast60sec.put(event.getData(),tmp-1);
            }else{
                eventsDataInLast60sec.remove(event.getData());
            }
        }
    }

    // from string to int timestamp
    private static boolean checkTimeDiff(String timeCheck, String curTimeStamp)
    {
        int timeCheckInt = (int)(Double.parseDouble(timeCheck)); // convert & remove unique key
        int timePlusLastSec = convertStringToIntTimestamp(String.valueOf(timeCheckInt));
        int curTimeInt = (int)(Double.parseDouble(curTimeStamp));
        return ((timePlusLastSec - curTimeInt) <= 0); // calculate diff
    }

    // convert string timestamp to int timestamp
    // by getting string timestamp convert it back to timestamp plus adding last sec
    // and finally return int of the result
    private static int convertStringToIntTimestamp(String time)
    {
        String newTimeRes = ""; // new time result
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
            Date parsedDate = dateFormat.parse(time);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime() + lastSec * ONE_SEC);
            newTimeRes = new SimpleDateFormat("MMddHHmmss").format(timestamp);
        } catch(Exception e) {
            //System.out.println(e);
        }

        return Integer.parseInt(newTimeRes);
    }

    // simple method to return current time stamp
    private static String currentTime()
    {
        return new SimpleDateFormat("MMddHHmmss").format(new Timestamp(System.currentTimeMillis()));
    }
}
