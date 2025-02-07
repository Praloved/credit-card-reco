package org.project.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Singleton;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.project.banking.model.UserTransactions;
import org.project.banking.service.UserDataLoader;
import org.project.model.ranking.ScoredCard;
import org.project.model.ranking.context.UserContext;
import org.project.model.response.CardSummary;
import org.project.model.response.CreditCardDocument;
import org.project.model.response.CreditCardRecommendationResponse;
import org.project.service.CreditCardService;
import org.project.utils.UserContextCreator;
import org.project.workflow.CreditCardRecommendationWorkFlow;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;

@Path("/v1/users")
@Timed
@Slf4j
@Singleton
public class CreditCardRecommendationsResource {

    private final UserDataLoader userDataLoader;
    private final UserContextCreator userContextCreator;
    private final CreditCardService creditCardService;
    private final CreditCardRecommendationWorkFlow creditCardRecommendationWorkFlow;

    @Inject
    public CreditCardRecommendationsResource(
            UserDataLoader userDataLoader,
            UserContextCreator userContextCreator,
            CreditCardService creditCardService,
            CreditCardRecommendationWorkFlow creditCardRecommendationWorkFlow
    ) {
        this.userDataLoader = userDataLoader;
        this.userContextCreator = userContextCreator;
        this.creditCardService = creditCardService;
        this.creditCardRecommendationWorkFlow = creditCardRecommendationWorkFlow;
    }

    @Path("/{userId}/creditCardSuggestions")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response recommendCards(@PathParam("userId") String userId,
                                   @FormDataParam("count") int count,
                                   @FormDataParam("accountStatement") InputStream inputStream) {
        try {
            // Generate User's financial userTransactions
            UserTransactions userTransactions = userDataLoader.processUserData(userId, inputStream);

            // get UserContext out of userTransactions
            UserContext userContext = userContextCreator.buildUserContext(userTransactions);

            // get relevant creditCard
            List<CreditCardDocument> creditCardDocuments = creditCardService.getCreditCards(userContext);

            // scoring of credit cards
            List<ScoredCard<CreditCardDocument>> scoredCards = creditCardRecommendationWorkFlow.getRecommendedCards(userContext, creditCardDocuments, count);

            CreditCardRecommendationResponse creditCardRecommendationResponse = buildResponse(userTransactions, scoredCards);
            // Return the processed object
            return Response.ok(creditCardRecommendationResponse).build();

        } catch (Exception e) {
            log.error("Error during file processing for user: {}", userId, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to process the statement").build();
        }
    }

    private CreditCardRecommendationResponse buildResponse(UserTransactions userTransactions,
                                                           List<ScoredCard<CreditCardDocument>> scoredCards) {
        List<CardSummary> cardSummaries = scoredCards.stream().sorted(Comparator.comparingDouble(ScoredCard::getScore))
                .map(a -> new CardSummary(a.getCard().getName())).toList();
        return CreditCardRecommendationResponse.builder()
                .userId(userTransactions.getUserId())
                .cards(cardSummaries)
                .build();
    }

}
