package com.example.hike_with_me_client.Recommendation;

import androidx.annotation.NonNull;

public class Recommendation {

    private String id;
    private int rate;
    private String description;
    private String reporterName;
    private String routeName;

    public Recommendation() {}

    public Recommendation(String id, int rate, String description, String reporterName, String routeName) {
        this.id = id;
        this.rate = rate;
        this.description = description;
        this.reporterName = reporterName;
        this.routeName = routeName;
    }

    public String getId() {
        return id;
    }

    public Recommendation setId(String id) {
        this.id = id;
        return this;
    }

    public int getRate() {
        return rate;
    }

    public Recommendation setRate(int rate) {
        this.rate = rate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Recommendation setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getReporterName() {
        return reporterName;
    }

    public Recommendation setReporterName(String reporterName) {
        this.reporterName = reporterName;
        return this;
    }

    public String getRouteName() {
        return routeName;
    }

    public Recommendation setRouteName(String routeName) {
        this.routeName = routeName;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Recommendation{" +
                "id='" + id + '\'' +
                ", rate=" + rate +
                ", description='" + description + '\'' +
                ", reporterName='" + reporterName + '\'' +
                ", routeName='" + routeName + '\'' +
                '}';
    }
}
