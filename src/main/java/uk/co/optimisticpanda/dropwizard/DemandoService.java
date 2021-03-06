package uk.co.optimisticpanda.dropwizard;

import static com.sun.jersey.api.core.ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS;
import uk.co.optimisticpanda.dropwizard.dao.QuestionDao;
import uk.co.optimisticpanda.dropwizard.dao.QuestionEventDao;
import uk.co.optimisticpanda.dropwizard.event.GenericAtomSupport;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.migrations.MigrationsBundle;
import com.yammer.dropwizard.views.ViewBundle;

public class DemandoService extends Service<DemandoConfiguration> {
    public static void main(String[] args) throws Exception {
        new DemandoService().run(args);
    }

    @Override
    public void initialize(Bootstrap<DemandoConfiguration> bootstrap) {
        bootstrap.setName("demando");
        bootstrap.addCommand(new TestDataLoadCommand(this));
        bootstrap.addBundle(new AssetsBundle("/assets/app", "/"));
        bootstrap.addBundle(new AssetsBundle("/assets/test", "/test"));
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new MigrationsBundle<DemandoConfiguration>() {
            public DatabaseConfiguration getDatabaseConfiguration(DemandoConfiguration configuration) {
                return configuration.getDatabaseConfiguration();
            }
        });
    }

    @Override
    public void run(DemandoConfiguration configuration, Environment environment) {
        environment.addProvider(GenericAtomSupport.class);

        environment.addResource(new QuestionnaireResource());
//        environment.addResource(new QuestionResource(factory.getDao(QuestionDao.class)));
//        environment.addResource(new QuestionEventsResource(factory.getDao(QuestionEventDao.class)));
    }

}
