package com.togetherhana.base;

import java.util.Arrays;

public enum SportsType {
    BASEBALL,
    SOCCER,
    E_SPORTS;

    public static SportsType byName(String name) {
        return Arrays.stream(SportsType.values())
                .filter(sportsType -> sportsType.name().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid sports type: " + name));
    }
}
