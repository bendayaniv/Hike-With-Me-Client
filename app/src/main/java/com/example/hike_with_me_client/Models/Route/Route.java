package com.example.hike_with_me_client.Models.Route;

import com.example.hike_with_me_client.Models.Objects.Location;
import com.example.hike_with_me_client.Models.Objects.point;

import java.util.Date;

public class Route extends point {

    private String _id;
    private String _name;
    private String _description;
    private String _difficultyLevel;
    private String _length;
    private String _imageUrl;

    public Route() {}

    public Route(Location location, String type, String id, String name, String description, String difficultyLevel, String length, String imageUrl) {
        super(location, type);
        this._id = id;
        this._name = name;
        this._description = description;
        this._difficultyLevel = difficultyLevel;
        this._length = length;
        this._imageUrl = imageUrl;
    }

    public String getId() {
        return _id;
    }

    public Route setId(String id) {
        this._id = id;
        return this;
    }

    public String getName() {
        return _name;
    }

    public Route setName(String name) {
        this._name = name;
        return this;
    }

    public String getDescription() {
        return _description;
    }

    public Route setDescription(String description) {
        this._description = description;
        return this;
    }

    public String getDifficultyLevel() {
        return _difficultyLevel;
    }

    public Route setDifficultyLevel(String difficultyLevel) {
        this._difficultyLevel = difficultyLevel;
        return this;
    }

    public String getLength() {
        return _length;
    }

    public Route setLength(String length) {
        this._length = length;
        return this;
    }

    public String getImageUrl() {
        return _imageUrl;
    }

    public Route setImageUrl(String imageUrl) {
        this._imageUrl = imageUrl;
        return this;
    }

    @Override
    public String toString() {
        return "Route{" +
                "_id='" + _id + '\'' +
                ", _name='" + _name + '\'' +
                ", _description='" + _description + '\'' +
                ", _difficultyLevel='" + _difficultyLevel + '\'' +
                ", _length='" + _length + '\'' +
                ", location=" + getLocation() +
                ", type='" + getType() + '\'' +
                ", _imageUrl='" + _imageUrl + '\'' +
                '}';
    }
}
