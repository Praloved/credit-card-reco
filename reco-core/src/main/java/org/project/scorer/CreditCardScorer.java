package org.project.scorer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.project.model.CreditCardModelContext;
import org.project.model.ranking.ScoreMeta;
import org.project.model.ranking.ScoredCard;
import org.project.model.ranking.context.UserContext;
import org.project.model.response.CreditCardDocument;
import org.project.ranking.CreditCardWeightedModel;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CreditCardScorer {

    private final CreditCardWeightedModel creditCardWeightedModel;

    @Inject
    public CreditCardScorer(CreditCardWeightedModel creditCardWeightedModel) {
        this.creditCardWeightedModel = creditCardWeightedModel;
    }

    public List<ScoredCard<CreditCardDocument>> getScoredCards(UserContext userContext, List<CreditCardDocument> creditCards) {
        return creditCards.stream().map(cc -> getScoredCard(userContext, cc)).collect(Collectors.toList());
    }

    public ScoredCard<CreditCardDocument> getScoredCard(UserContext userContext, CreditCardDocument card) {
        CreditCardModelContext context = new CreditCardModelContext(userContext, card);
        double score = creditCardWeightedModel.score(context);
        return ScoredCard.<CreditCardDocument>builder()
                .card(card)
                .score(score)
                .scoreMeta(ScoreMeta.builder()
                        .modelName(creditCardWeightedModel.getModelName())
                        .modelVersion(creditCardWeightedModel.getModelVersion()).build())
                .build();
    }

}
