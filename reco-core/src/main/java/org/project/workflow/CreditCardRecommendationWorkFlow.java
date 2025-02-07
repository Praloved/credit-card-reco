package org.project.workflow;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.project.allocator.CreditCardAllocator;
import org.project.model.ranking.ScoredCard;
import org.project.model.ranking.context.UserContext;
import org.project.model.response.CreditCardDocument;
import org.project.scorer.CreditCardScorer;

import java.util.List;

@Slf4j
@Singleton
public class CreditCardRecommendationWorkFlow {

    private final CreditCardScorer creditCardScorer;
    private final CreditCardAllocator creditCardAllocator;

    @Inject
    public CreditCardRecommendationWorkFlow(CreditCardScorer creditCardScorer,
                                            CreditCardAllocator creditCardAllocator) {
        this.creditCardScorer = creditCardScorer;
        this.creditCardAllocator = creditCardAllocator;
    }

    public List<ScoredCard<CreditCardDocument>> getRecommendedCards(UserContext userContext,
                                                                    List<CreditCardDocument> creditCardDocuments,
                                                                    int count) {
        List<ScoredCard<CreditCardDocument>> scoredCards = creditCardScorer.getScoredCards(userContext, creditCardDocuments);
        List<ScoredCard<CreditCardDocument>> allocatedCards = creditCardAllocator.allocate(scoredCards, count);
        log.info("allocated cards count: {}", allocatedCards.size());
        return allocatedCards;
    }
}
