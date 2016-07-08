package com.demo.tracker.common;

import java.util.ArrayList;
import java.util.List;

public class Const {

    public static final String API_ENDPOINT = "https://www.wunelliuat.com/testingtalent";
    public static final String API_JOURNEYS = "/journeys";
    public static final String API_LOCATION = "/user/location";
    public static final String USERNAME = "WunelliTestUser";
    public static final String PASSWORD = "resUtseTillenuW";

    public enum BundleKey {
        COORDINATE,
        ROUTE,
        LOCALITY
    }

    private Const() {
    }
}
