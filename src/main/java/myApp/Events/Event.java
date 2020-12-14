package myApp.Events;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {
    @JsonProperty("event_type")
    private String eventType;

    private String data;

    private int timestamp;

    public Event()
    {
    }

    public Event(String e, String d, int t){
        this.eventType=e;
        this.data=d;
        this.timestamp=t;
    }

    public String getEventType() {
        return eventType;
    }

    public String getData() {
        return data;
    }

    public int getTimestamp() {
        return timestamp;
    }
}