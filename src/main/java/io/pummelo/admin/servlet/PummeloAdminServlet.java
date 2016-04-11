package io.pummelo.admin.servlet;

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

    private ApplicationContext springContext;

    public PummeloAdminServlet(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
