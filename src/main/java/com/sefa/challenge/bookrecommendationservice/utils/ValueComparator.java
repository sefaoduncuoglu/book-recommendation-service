package com.sefa.challenge.bookrecommendationservice.utils;

import java.util.Comparator;
import java.util.Map;

/**
 * Comparator to sort a HashMap by its value:
 * http://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-java
 */
public class ValueComparator implements Comparator<Long> {
    private final Map<Long, Double> base;

    public ValueComparator(Map<Long, Double> base) {
        this.base = base;
    }

    public int compare(Long a, Long b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}
