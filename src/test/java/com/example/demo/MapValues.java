package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class MapValues {

    private String[] keys;

    public static MapValues of(String... keys) {
        MapValues mapValues = new MapValues();
        mapValues.keys = keys;

        return mapValues;
    }

    public Map<String, Object> with(Object... values) {
        if (keys.length != values.length) {
            throw new IllegalArgumentException("keys length is not equals values length");
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0, len = keys.length; i < len; i++) {
            map.put(keys[i], values[i]);
        }

        return map;
    }
}
