package io.pummelo.admin.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by PummeloDeveloper on 16/4/12.
 */
public interface Action {

    void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
