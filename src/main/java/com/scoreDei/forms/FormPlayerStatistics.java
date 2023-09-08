package com.scoreDei.forms;

public class FormPlayerStatistics {

    private String name;
    private int goals;
    private float meanGoals;
    private int maxGoalsInMatch;
    private int maxGoalsInMatchNumber;

    public FormPlayerStatistics(String name, int goals, float meanGoals, int maxGoalsInMatch, int maxGoalsInMatchNumber) {
        this.name = name;
        this.goals = goals;
        this.meanGoals = meanGoals;
        this.maxGoalsInMatch = maxGoalsInMatch;
        this.maxGoalsInMatchNumber = maxGoalsInMatchNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public float getMeanGoals() {
        return meanGoals;
    }

    public void setMeanGoals(float meanGoals) {
        this.meanGoals = meanGoals;
    }

    public int getMaxGoalsInMatch() {
        return maxGoalsInMatch;
    }

    public void setMaxGoalsInMatch(int maxGoalsInMatch) {
        this.maxGoalsInMatch = maxGoalsInMatch;
    }

    public int getMaxGoalsInMatchNumber() {
        return maxGoalsInMatchNumber;
    }

    public void setMaxGoalsInMatchNumber(int maxGoalsInMatchNumber) {
        this.maxGoalsInMatchNumber = maxGoalsInMatchNumber;
    }
}
