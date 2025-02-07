package org.project.model.ranking.context;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class Feature {
    private String featureName;
    private FeatureType featureType;
    private Object featureValue;

    public <T> T getFeatureValue() {
        return featureType.getFeatureValue(featureValue);
    }
}
