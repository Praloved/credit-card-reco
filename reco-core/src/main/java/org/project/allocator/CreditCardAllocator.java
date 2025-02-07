package org.project.allocator;

import com.google.inject.Singleton;
import org.project.model.ranking.ScoredCard;
import org.project.model.response.CreditCardDocument;

import java.util.List;

@Singleton
public class CreditCardAllocator {

    public List<ScoredCard<CreditCardDocument>> allocate(List<ScoredCard<CreditCardDocument>> scoredCards, int count) {
        return scoredCards.subList(0, count);
    }
}
