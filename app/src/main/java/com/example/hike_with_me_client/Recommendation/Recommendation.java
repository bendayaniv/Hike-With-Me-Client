package com.example.hike_with_me_client.Recommendation;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Route.Route;
import com.example.hike_with_me_client.User.User;

public class Recommendation {

    private String id;
    private int rate;
    private String description;
    private User reporter;
    private String reporterName;
    private Route route;
    private String routeName;

    public Recommendation() {}

    public Recommendation(String id, int rate, String description, /*User reporter*/String reporterName, /*Route route*/String routeName) {
        this.id = id;
        this.rate = rate;
        this.description = description;
//        this.reporter = reporter;
        this.reporterName = reporterName;
//        this.route = route;
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

//    public User getReporter() {
//        return reporter;
//    }
//
//    public Recommendation setReporter(User reporter) {
//        this.reporter = reporter;
//        return this;
//    }

    public String getReporterName() {
        return reporterName;
    }

    public Recommendation setReporterName(String reporterName) {
        this.reporterName = reporterName;
        return this;
    }

//    public Route getRoute() {
//        return route;
//    }
//
//    public Recommendation setRoute(Route route) {
//        this.route = route;
//        return this;
//    }

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
