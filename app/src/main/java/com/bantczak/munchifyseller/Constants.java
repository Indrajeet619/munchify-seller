package com.bantczak.munchifyseller;

public class Constants {

    /**
     * ----- FIREBASE CONSTANTS -----
     * */

    /**
     * -> (sellers) -> {listof Seller}
     * id: H4t3y2c
     * email: bantczak@gmail.com
     * getId()
     * getEmail()
     * */
    public static final String FIREBASE_SELLER_IDS = "sellers";

    /**
     * -> (all-postings) -> {listof Posting}
     * */
    public static final String FIREBASE_ALL_POSTINGS = "all-postings";

    /**
     * -> (postings-by-seller) -> (seller-id) -> {listof Posting}
     * */
    public static final String FIREBASE_POSTINGS_BY_SELLER = "postings-by-seller";
}
