package com.example.hike_with_me_client.Hazard;

import com.example.hike_with_me_client.Utils.Constants;
import com.example.hike_with_me_client.Route.Route;
import com.example.hike_with_me_client.User.User;

public class Hazard {

    private String id;
    private Constants.HazardType type;
    private String description;
    private Constants.Level severity;
    private User reporter;
    private Route route;
    private String date;

    public Hazard() {}

    public Hazard(String id, Constants.HazardType type, String description, Constants.Level severity, User reporter, Route route, String date) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.severity = severity;
        this.reporter = reporter;
        this.route = route;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public Hazard setId(String id) {
        this.id = id;
        return this;
    }

    public Constants.HazardType getType() {
        return type;
    }

    public Hazard setType(Constants.HazardType type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Hazard setDescription(String description) {
        this.description = description;
        return this;
    }

    public Constants.Level getSeverity() {
        return severity;
    }

    public Hazard setSeverity(Constants.Level severity) {
        this.severity = severity;
        return this;
    }

    public User getReporter() {
        return reporter;
    }

    public Hazard setReporter(User reporter) {
        this.reporter = reporter;
        return this;
    }

    public Route getRoute() {
        return route;
    }

    public Hazard setRoute(Route route) {
        this.route = route;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Hazard setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "Hazard{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", severity=" + severity +
                ", reporter=" + reporter +
                ", route=" + route +
                ", date=" + date +
                '}';
    }
}
