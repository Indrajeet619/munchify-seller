package com.bantczak.munchifyseller;

public class FirebaseConstants {
    /**
     * -> (sellers) -> {listof Seller}
     * id: H4t3y2c
     * email: bantczak@gmail.com
     * getId()
     * getEmail()
     * */
    public static final String SELLER_IDS = "sellers";

    /**
     * -> (all-postings) -> {listof Posting}
     * */
    public static final String ALL_POSTINGS = "all-postings";

    /**
     * -> (postings-by-seller) -> (seller-id) -> {listof Posting}
     * */
    public static final String POSTINGS_BY_SELLER = "postings-by-seller";
}
