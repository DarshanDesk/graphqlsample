package com.darshan.graphqlsample.utils;


public enum MetricsCriteria {
    MONTHLY(Constants.CRITERIA_MONTHLY);

    MetricsCriteria(String label){
        this.label = label;
    }

    public final String label;

    public static MetricsCriteria valueOfLabel(String label) {
        for (MetricsCriteria metricsConstant : values()) {
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


