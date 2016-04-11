package io.pummelo.admin.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.FactoryBean;

import java.net.URL;

/**
 * Created by PummeloDeveloper on 16/4/10.
 */
public class PummeloConfigurationFactoryBean implements FactoryBean<PummeloConfiguration> {

    private static PummeloConfiguration configuration = createPummeloConfiguration();

    @Override
    public PummeloConfiguration getObject() throws Exception {
        return configuration;
    }

    @Override
    public Class<?> getObjectType() {
        return PummeloConfiguration.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private static PummeloConfiguration createPummeloConfiguration() {
        Config config = null;
        URL configURL = PummeloConfigurationFactoryBean.class.getClassLoader().getResource("./pummelo/application.conf");
        if (configURL != null) {
            config = ConfigFactory.parseURL(configURL);
        }
        if (config == null) {
            config = ConfigFactory.empty();
        }
        return new PummeloConfiguration(config);
    }
}
