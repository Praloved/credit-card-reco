package org.project;

import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.project.config.AppConfiguration;
import org.project.module.AppModule;
import ru.vyarus.dropwizard.guice.GuiceBundle;


@Slf4j
public class CreditCardRecommendationApp extends Application<AppConfiguration> {


    public static void main(String[] args) throws Exception {
        new CreditCardRecommendationApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        GuiceBundle guiceBundle = GuiceBundle.builder()
                .modules(new AppModule())
                .enableAutoConfig("org.project.resource")  // Auto-detect resources
                .dropwizardBundles(new AssetsBundle("/assets", "/static", null, "assets"))
                .build();

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {

    }
}
