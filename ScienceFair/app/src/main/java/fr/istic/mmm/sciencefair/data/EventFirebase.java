package fr.istic.mmm.sciencefair.data;

public class EventFirebase {
    public String recordid;
    public int nbVotes;
    public float rating;
    public int remaining;

    public EventFirebase() { }

    public EventFirebase(String recordid, int nbVotes, float rating, int remaining) {
        this.recordid = recordid;
        this.nbVotes = nbVotes;
        this.rating = rating;
        this.remaining = remaining;
    }
}
