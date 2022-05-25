package com.darshan.graphqlsample.utils;

import lombok.RequiredArgsConstructor;


public enum MetricsConstants {
    MONTHLY(Constants.CRITERIA_MONTHLY);

    private MetricsConstants(String label){
        this.label = label;
    }

    public final String label;

    public static MetricsConstants valueOfLabel(String label) {
        for (MetricsConstants metricsConstant : values()) {
            if (metricsConstant.label.equals(label)) {
                return metricsConstant;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.label;
    }
}


