package io.pummelo.admin.web;

import io.pummelo.admin.config.PummeloConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

/**
 * Created by PummeloDeveloper on 16/4/12.
 */
@Service
public class PummeloWebService {

    private String baseDirectory;

    @Autowired
    public PummeloWebService(PummeloConfiguration configuration, ServletContext servletContext) {
        baseDirectory = servletContext.getContextPath() + configuration.getString("pummelo.web.directory", "/pummelo/web");
    }

    public PummeloWebView createView(Object data) {
        PummeloWebView view = new PummeloWebView();
        view.setPummeloWebBaseDir(baseDirectory);
        view.setData(data);
        return view;
    }
}
