package org.project.provider.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.project.model.request.CreditCardRequest;
import org.project.model.response.CreditCardDocument;
import org.project.model.response.Reward;
import org.project.provider.IDataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Singleton
public class ElasticSearchDataProvider implements IDataProvider<CreditCardRequest, CreditCardDocument> {

    private final ElasticsearchClient client;

    @Inject
    public ElasticSearchDataProvider(ElasticsearchClient client) {
        this.client = client;
    }

    public List<CreditCardDocument> getData(CreditCardRequest request) {
        // Build Elasticsearch search request
        SearchRequest.Builder searchBuilder = new SearchRequest.Builder().index("credit_cards");

        // Add query filters
        searchBuilder.query(qb -> qb.bool(bool -> {
            if (request.getCategories() != null) {
                List<FieldValue> categoryValues = request.getCategories().stream()
                        .map(FieldValue::of) // Convert each string to FieldValue
                        .toList();

                bool.must(term -> term.terms(tq ->
                        tq.field("suitableFor").terms(ts -> ts.value(categoryValues))
                ));
            }
            // Add additional filters as needed
            return bool;
        }));
        // Build the search request
        SearchResponse<Map<String, Object>> searchResponse = null;
        try {
            searchResponse = client.search(searchBuilder.build(), (Class<Map<String, Object>>) (Class<?>) Map.class);
        } catch (IOException e) {
            log.error("Error occured while searching elasticsearch: {}", e.getMessage(), e);
        }

        // Transform the response into custom objects
        List<CreditCardDocument> results = new ArrayList<>();
        for (Hit<Map<String, Object>> hit : searchResponse.hits().hits()) {
            Map<String, Object> source = hit.source();

            CreditCardDocument document = new CreditCardDocument();
            document.setId((String) source.get("id"));
            document.setName((String) source.get("name"));
            document.setParentCompany((String) source.get("parentCompany"));
            document.setSuitableFor((List<String>) source.get("suitableFor"));
            document.setAnnualFee((double) source.get("annualFee"));
            document.setLoungeBenefits((Boolean) source.get("loungeBenefits"));
            document.setExtraBenefits((List<String>) source.get("extraBenefits"));
            document.setPremium((Boolean) source.get("premium"));
            document.setInterestRate((double) source.get("interestRate"));
            document.setForeignTransactionFee((double) source.get("foreignTransactionFee"));
            document.setFuelSurchargeWaiver((Boolean) source.get("fuelSurchargeWaiver"));
            document.setWelcomeBonus((String) source.get("welcomeBonus"));
            document.setEligibilityCriteria((String) source.get("eligibilityCriteria"));
            document.setIssuedBy((String) source.get("issuedBy"));
            document.setMaxCashbackLimit((double) source.get("maxCashbackLimit"));

            results.add(document);
        }

        return results;
    }

}