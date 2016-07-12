package com.demo.tracker.common;

import java.util.ArrayList;
import java.util.List;

public class Const {
    
    //demo details
    public static final String API_ENDPOINT = "url";   
    public static final String API_JOURNEYS = "/details";
    public static final String API_LOCATION = "/details";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public enum BundleKey {
        COORDINATE,
        ROUTE,
        LOCALITY
    }

    private Const() {
    }
}
