package com.bantczak.munchifyseller.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class FoodPosting {
    public String id;
    public String title;
    public double oldPrice;
    public double newPrice; // newPrice must be <= oldPrice
    public String sellerId;
    public String sellerName;
//    public String imageURL;
    // Include time of creation
    // expiry time

    public FoodPosting() {
        // Required for Firebase
    }

    public FoodPosting setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
        return this;
    }

    public FoodPosting setNewPrice(double newPrice) {
        this.newPrice = newPrice;
        return this;
    }

    public FoodPosting setId(String id) {
        this.id = id;
        return this;
    }

    public FoodPosting setTitle(String title) {
        this.title = title;
        return this;
    }

    public FoodPosting setSellerId(String sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public FoodPosting setSellerName(String sellerName) {
        this.sellerName = sellerName;
        return this;
    }

    public String getId() {
        return id;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public String getTitle() {
        return title;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        result.put("oldPrice", oldPrice);
        result.put("newPrice", newPrice);
        result.put("sellerId", sellerId);
        result.put("sellerName", sellerName);

        return result;
    }
}
