package org.project.model.ranking;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoredCard<T> {
    private ScoreMeta scoreMeta;
    private double score;
    private T card;
}
