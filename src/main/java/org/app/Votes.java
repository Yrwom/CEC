package org.app;

public class Votes {
    private String eventUUID;
    private int voteYesCount;
    private int voteNoCount;

    public Votes() {
    //public Votes object to allow instance control
    }

    public String GetEventUUID() {
        return eventUUID;
    }

    public void SetEventUUID(String setEventUUID) {
        eventUUID = setEventUUID;
    }

    public int GetVoteYesCount() {
        return voteYesCount;
    }

    public void SetVoteYesCount(int setVoteYesCount) {
        voteYesCount = setVoteYesCount;
    }

    public int GetVoteNoCount() {
        return voteNoCount;
    }

    public void SetVoteNoCount(int setVoteNoCount) {
        voteNoCount = setVoteNoCount;
    }
}

