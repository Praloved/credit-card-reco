package org.project.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.project.model.ranking.context.UserContext;
import org.project.model.request.CreditCardRequest;
import org.project.model.response.CreditCardDocument;
import org.project.provider.IDataProvider;

import java.util.List;

@Singleton
public class CreditCardService {

    final IDataProvider<CreditCardRequest, CreditCardDocument> creditCardDataProvider;

    @Inject
    public CreditCardService(
            @Named("esDataProvider") IDataProvider<CreditCardRequest, CreditCardDocument> creditCardDataProvider
    ) {
        this.creditCardDataProvider = creditCardDataProvider;
    }

    public List<CreditCardDocument> getCreditCards(UserContext userContext) {
        return creditCardDataProvider.getData(generateCreditCardRequest(userContext));
    }

    private CreditCardRequest generateCreditCardRequest(UserContext userContext) {
        List<String> top5SpendingCategories = (List<String>) userContext
                .getUserFeatures().get("top5SpendingCategories").getFeatureValue();
        return CreditCardRequest.builder()
                .categories(top5SpendingCategories.stream().map(String::toLowerCase).toList())
                .build();
    }
}
