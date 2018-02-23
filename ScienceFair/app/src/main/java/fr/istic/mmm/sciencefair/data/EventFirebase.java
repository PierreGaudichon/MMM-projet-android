package fr.istic.mmm.sciencefair.data;

public class EventFirebase {
    private String recordid;
    private int nbVotes;
    private float rating;
    private int remaining;

    public EventFirebase() { }

    public EventFirebase(String recordid, int nbVotes, float rating, int remaining) {
        this.recordid = recordid;
        this.nbVotes = nbVotes;
        this.rating = rating;
        this.remaining = remaining;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public int getNbVotes() {
        return nbVotes;
    }

    public void setNbVotes(int nbVotes) {
        this.nbVotes = nbVotes;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
}
