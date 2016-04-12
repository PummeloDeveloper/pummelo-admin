package io.pummelo.admin.action.predef;

import freemarker.template.Template;
import io.pummelo.admin.action.PummeloActionService;
import io.pummelo.admin.exception.PummeloException;
import io.pummelo.admin.resources.PummeloResourcesService;
import io.pummelo.admin.util.CharsetUtils;
import io.pummelo.admin.web.PummeloWebService;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.jar.JarEntry;

/**
 * Created by PummeloDeveloper on 16/4/12.
 */
@Service
public class ActionPredef {

    public void define(PummeloActionService actionService, ApplicationContext springContext) throws IOException {
        defineIndexAction(actionService, springContext);
    }

    private void defineIndexAction(PummeloActionService actionService, ApplicationContext springContext) throws IOException {
        PummeloResourcesService resourcesService = springContext.getBean(PummeloResourcesService.class);
        PummeloWebService webService = springContext.getBean(PummeloWebService.class);
        List<JarEntry> entries = resourcesService.getResources("freemarker/templates/index.html");
        if (entries == null || entries.size() <= 0) {
            throw new PummeloException("pummelo initialize IndexAction error, freemarker/templates/index.html not found");
        }
        try (InputStream input = resourcesService.getInputStream(entries.get(0))) {
            String templateText = IOUtils.toString(input, CharsetUtils.CHARSET_UTF_8);
            FreeMarkerConfig freeMarkerConfig = springContext.getBean(FreeMarkerConfig.class);
            Template template = new Template("index.html", templateText, freeMarkerConfig.getConfiguration());
            IndexAction action = new IndexAction(template, webService);
            actionService.add("/", action);
            actionService.add("INDEX", action);
        }
    }
}
