package com.example.hike_with_me_client.Hazard;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Utils.Constants;

public class Hazard {

    private String id;
    private Constants.HazardType type;
    private String description;
    private Constants.Level severity;
    private String reporterName;
    private String routeName;
    private String date;

    public Hazard() {}

    public Hazard(String id, Constants.HazardType type, String description, Constants.Level severity, String reporterName, String routeName, String date) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.severity = severity;
        this.reporterName = reporterName;
        this.routeName = routeName;
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

    public String getReporterName() {
        return reporterName;
    }

    public Hazard setReporterName(String reporterName) {
        this.reporterName = reporterName;
        return this;
    }

    public String getRouteName() {
        return routeName;
    }

    public Hazard setRouteName(String routeName) {
        this.routeName = routeName;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Hazard setDate(String date) {
        this.date = date;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Hazard{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", severity=" + severity +
                ", reporterName='" + reporterName + '\'' +
                ", routeName='" + routeName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
