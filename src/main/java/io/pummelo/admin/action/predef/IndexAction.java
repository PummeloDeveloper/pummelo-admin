package io.pummelo.admin.action.predef;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.pummelo.admin.action.Action;
import io.pummelo.admin.web.PummeloWebService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by PummeloDeveloper on 16/4/12.
 */
public class IndexAction implements Action {

    private Template template;

    private PummeloWebService webService;

    IndexAction(Template template, PummeloWebService webService) {
        this.template = template;
        this.webService = webService;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }
        try {
            template.process(webService.createView(null), response.getWriter());
        } catch (TemplateException e) {
            throw new ServletException(e);
        }
    }
}
