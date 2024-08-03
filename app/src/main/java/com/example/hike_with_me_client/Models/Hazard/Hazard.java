package com.example.hike_with_me_client.Models.Hazard;

import androidx.annotation.NonNull;

import com.example.hike_with_me_client.Models.Objects.Location;
import com.example.hike_with_me_client.Models.Objects.point;
import com.example.hike_with_me_client.Utils.Constants;

public class Hazard extends point {

    private String _id;
    private Constants.HazardType _hazardType;
    private String _description;
    private Constants.Level _severity;
    private String _reporterId;
    private String _routeName;

    public Hazard() {}

    public Hazard(Location location, String pointType, String id, Constants.HazardType hazardType, String description, Constants.Level severity, String reporterName, String routeName) {
        super(location, pointType);
        this._id = id;
        this._hazardType = hazardType;
        this._description = description;
        this._severity = severity;
        this._reporterId = reporterName;
        this._routeName = routeName;
    }

    public String getId() {
        return _id;
    }

    public Hazard setId(String id) {
        this._id = id;
        return this;
    }

    public Constants.HazardType getHazardType() {
        return _hazardType;
    }

    public Hazard setHazardType(Constants.HazardType hazardType) {
        this._hazardType = hazardType;
        return this;
    }

    public String getDescription() {
        return _description;
    }

    public Hazard setDescription(String description) {
        this._description = description;
        return this;
    }

    public Constants.Level getSeverity() {
        return _severity;
    }

    public Hazard setSeverity(Constants.Level severity) {
        this._severity = severity;
        return this;
    }

    public String getReporterId() {
        return _reporterId;
    }

    public Hazard setReporterId(String reporterName) {
        this._reporterId = reporterName;
        return this;
    }

    public String getRouteName() {
        return _routeName;
    }

    public Hazard setRouteName(String routeName) {
        this._routeName = routeName;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Hazard{" +
                "_id='" + _id + '\'' +
                ", _hazardType=" + _hazardType +
                ", _description='" + _description + '\'' +
                ", _severity=" + _severity +
                ", _reporterId='" + _reporterId + '\'' +
                ", _routeName='" + _routeName + '\'' +
                ", location=" + getLocation() +
                ", pointType='" + getType() + '\'' +
                '}';
    }
}
