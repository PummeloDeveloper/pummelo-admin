package io.pummelo.admin.spring.config;

import io.pummelo.admin.config.PummeloConfigurationFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by PummeloDeveloper on 16/4/10.
 */
@Configuration("classpath*:spring/applicationContext.xml")
public class PummeloSpringConfig {

    @Bean
    public PummeloConfigurationFactoryBean pummeloConfigurationFactoryBean() {
        return new PummeloConfigurationFactoryBean();
    }
}
