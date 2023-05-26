package com.mirea.informatics.stats;

public class Stats {
    private boolean[] completed;
    private String[] testNames;

    public Stats(String[] testNames, boolean[] completed){
        this.testNames = testNames;
        this.completed = completed;
    }

    public void setCompleted(boolean completed, int index) {
        this.completed[index] = completed;
    }

    public void setTestNames(String testNames, int index){
        this.testNames[index] = testNames;
    }

    public boolean[] getCompleted() {
        return completed;
    }

    public String[] getTestNames() {
        return testNames;
    }

    public void setCompleted(boolean[] completed) {
        this.completed = completed;
    }

    public void setTestNames(String[] testNames) {
        this.testNames = testNames;
    }
}
