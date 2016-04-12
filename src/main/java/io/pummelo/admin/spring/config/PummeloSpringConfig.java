package io.pummelo.admin.spring.config;

import io.pummelo.admin.config.PummeloConfiguration;
import io.pummelo.admin.config.PummeloConfigurationFactoryBean;
import io.pummelo.admin.exception.PummeloException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by PummeloDeveloper on 16/4/10.
 */
@Configuration
@ImportResource("classpath*:spring/applicationContext.xml")
@ComponentScan("io.pummelo.admin")
@EnableWebMvc
public class PummeloSpringConfig {

    @Bean
    public PummeloConfigurationFactoryBean pummeloConfigurationFactoryBean() {
        return new PummeloConfigurationFactoryBean();
    }

    @Bean
    @Autowired
    public FreeMarkerViewResolver viewResolver(ServletContext servletContext, ApplicationContext springContext) {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setServletContext(servletContext);
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setApplicationContext(springContext);
        resolver.setViewClass(FreeMarkerView.class);
        return resolver;
    }

    @Bean
    @Autowired
    public FreeMarkerConfigurer freeMarkerConfigurer(
            PummeloConfiguration configuration, ServletContext servletContext) throws IOException {
        String path = configuration.getString("pummelo.template.directory", "/freemarker/templates");

        String templateDirectoryPath = null;
        try {
            templateDirectoryPath = servletContext.getRealPath(path);
        } catch (Exception e) {}
        if (templateDirectoryPath == null) {
            URL resourceURL = getClass().getClassLoader().getResource(path);
            if (resourceURL != null && "file".equalsIgnoreCase(resourceURL.getProtocol())) {
                templateDirectoryPath = resourceURL.getFile();
            }
        }
        if (templateDirectoryPath == null) {
            templateDirectoryPath = servletContext.getRealPath("/");
        }
        File templateDirectory = new File(templateDirectoryPath);
        if (!templateDirectory.exists()) {
            throw new PummeloException(String.format(
                    "pummelo initialize freemarker error, `pummelo.template.directory` %s not found", templateDirectoryPath));
        }
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        freemarker.template.Configuration freeMarkeronfiguration =
                new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_22);
        freeMarkeronfiguration.setDefaultEncoding("UTF-8");
        freeMarkeronfiguration.setDirectoryForTemplateLoading(templateDirectory);
        configurer.setConfiguration(freeMarkeronfiguration);
        return configurer;
    }

}