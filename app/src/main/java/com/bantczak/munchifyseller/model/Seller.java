package com.bantczak.munchifyseller.model;

import android.location.Location;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Seller {
    public String id;
    public String email;
    Location location;

    public Seller() {
        // empty constructor required for Firebase
    }

    public Seller(String id, String email, Location location) {
        this.id = id;
        this.email = email;
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("email", email);
        result.put("location", location);

        return result;
    }
}
