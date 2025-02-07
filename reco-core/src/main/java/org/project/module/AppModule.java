package org.project.module;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.project.config.AppConfiguration;
import org.project.model.request.CreditCardRequest;
import org.project.model.response.CreditCardDocument;
import org.project.provider.IDataProvider;
import org.project.provider.impl.ElasticSearchDataProvider;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

@Slf4j
public class AppModule extends DropwizardAwareModule<AppConfiguration> {

    @Provides
    @Singleton
    private ElasticsearchClient getElasticsearchClient() {
        AppConfiguration configuration = configuration();
        return new ElasticsearchClient(new RestClientTransport(
                RestClient.builder(HttpHost.create(configuration.getElasticSearchConfig().toString())).build(),
                new JacksonJsonpMapper(environment().getObjectMapper())
        ));
    }

    @Override
    protected void configure() {
        bind(MultiPartFeature.class);
        bind(new TypeLiteral<IDataProvider<CreditCardRequest,
                CreditCardDocument>>() {
        })
                .annotatedWith(Names.named("esDataProvider"))
                .to(ElasticSearchDataProvider.class);

    }

}
