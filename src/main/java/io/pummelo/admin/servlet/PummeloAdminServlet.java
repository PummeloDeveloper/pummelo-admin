package io.pummelo.admin.servlet;

import io.pummelo.admin.action.Action;
import io.pummelo.admin.action.PummeloActionService;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by PummeloDeveloper on 16/4/10.
 */
public class PummeloAdminServlet extends HttpServlet {

    private final PummeloActionService actionService;

    public PummeloAdminServlet(ApplicationContext springContext) {
        actionService = springContext.getBean(PummeloActionService.class);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Action action = actionService.get(uri);
        if (action == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        action.service(request, response);
    }
}
