package io.pummelo.admin.servlet;

import io.pummelo.admin.exception.PummeloException;
import io.pummelo.admin.spring.config.PummeloSpringConfig;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;

/**
 * Created by PummeloDeveloper on 16/4/10.
 */
public class PummeloAdminServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        initLog4j();
        WebApplicationContext springContext = createSpringContext(servletContext);
        addSpringContextLoaderListener(servletContext, springContext);
        addCustomSpringWebMvcServlet(servletContext, springContext);
        addPummeloServlet(servletContext, springContext);
        addInternalResourceServlet(servletContext);
    }

    private void initLog4j() {
        URL configURL = getClass().getClassLoader().getResource("./log4j/log4j.xml");
        if (configURL != null) {
            try (InputStream input = configURL.openStream()) {
                Configurator.initialize(new XmlConfiguration(new ConfigurationSource(input, configURL)));
            } catch (IOException e) {
                throw new PummeloException("pummelo log4j initialize error", e);
            }
        }
    }

    private WebApplicationContext createSpringContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext springContext =
                new AnnotationConfigWebApplicationContext();
        springContext.setServletContext(servletContext);
        springContext.register(PummeloSpringConfig.class);
        springContext.refresh();
        return springContext;
    }

    private void addSpringContextLoaderListener(ServletContext servletContext, WebApplicationContext springContext) {
        servletContext.addListener(new ContextLoaderListener(springContext));
    }

    private void addCustomSpringWebMvcServlet(ServletContext servletContext, WebApplicationContext springContext) {
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet(
                "DispatcherServlet", new DispatcherServlet(springContext));
        servletRegistration.setAsyncSupported(true);
        servletRegistration.addMapping("/web/*");
    }

    private void addPummeloServlet(ServletContext servletContext, WebApplicationContext springContext) {
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet(
                "PummeloServlet", new PummeloAdminServlet(springContext));
        servletRegistration.addMapping("/");
        servletRegistration.addMapping("/api/*");
    }

    private void addInternalResourceServlet(ServletContext servletContext) {
        ServletRegistration registration = servletContext.getServletRegistration("default");
        registration.addMapping("/*");
    }
}
