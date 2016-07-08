package com.demo.tracker.common;

import java.util.HashMap;
import java.util.Map;

//Class that holds a reference to each model

public class ModelLocator {

    public enum Tag {
        JOURNEY_ITEM,
        LOCATION_ITEM
    }

    private static Map<Tag, Object> showcase = new HashMap<>();

    private ModelLocator() {
    }

    public static void register(Tag tag, Object object) {
        showcase.put(tag, object);
    }

    public static Object get(Tag tag) {
        return showcase.get(tag);
    }
}
