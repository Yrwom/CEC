package org.app;

public class Event {
    private String eventName;
    private String eventCreator;
    private String startDate;
    private String endDate;
    private int maxParticipants;
    private String location;
    private String eventType;
    private String eventDescription;
    private boolean votingEnabled;
    private String createdAt;

    public Event(){



    }

    public void setTitle(String setEventName) {
            eventName= setEventName;
    }

    public void setStartDate(String setStartDate) {
        startDate = setStartDate;
    }

    public void setEndDate(String setEndDate) {
        endDate = setEndDate;
    }

    public void setMaxParticipants(int setMaxParticipants) {
        maxParticipants = setMaxParticipants;
    }

    public void setLocation(String setLocation) {
        location = setLocation;
    }

    public void setEventType(String setEventType) {
        eventType = setEventType;
    }

    public void setEventDescription(String setEventDescription) {
        eventDescription = setEventDescription;
    }

    public void setVotingStatus(boolean setVotingEnabled) {
        votingEnabled = setVotingEnabled;
    }

    public void setCreatedAt(String setCreatedAt) {
        createdAt = setCreatedAt;
    }

    public String getTitle() {
        return eventName;
    }

    public String getCreator() {
        return eventCreator;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public String getLocation() {
        return location;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public boolean getVotingStatus() {
        return votingEnabled;
    }

}
