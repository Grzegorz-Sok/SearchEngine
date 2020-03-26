package simpleSearchEngine;

import interfaces.IndexEntry;

public class Index implements IndexEntry {

    private String id;
    private double score;
    private int occurence = 1;


    public Index(String id) {
        this.id = id;
    }

    public Index() { }

    public int getOccurence() {
        return occurence;
    }

    public void increase(){this.occurence++;}

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }
}
