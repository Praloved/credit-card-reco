package org.project.model.ranking.context;

import java.util.List;
import java.util.Map;

public enum FeatureType {


    NUMERIC(Double.class),
    STRING(String.class),
    BOOLEAN(Boolean.class),
    MAP(Map.class),
    LIST(List.class);

    private final Class<?> klass;

    <T> FeatureType(Class<T> klass) {
        this.klass = klass;
    }

    public <T> T getFeatureValue(Object featureValue) {
        return (T) klass.cast(featureValue);
    }
}
