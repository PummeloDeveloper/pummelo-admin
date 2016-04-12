package io.pummelo.admin.action;

import io.pummelo.admin.action.predef.ActionPredef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PummeloDeveloper on 16/4/12.
 */
@Service
public class PummeloActionService {

    private final Map<String, Action> actions = new HashMap<>();

    private final String contextPath;

    @Autowired
    public PummeloActionService(ServletContext servletContext, ApplicationContext springContext) throws IOException {
        contextPath = servletContext.getContextPath();
        new ActionPredef().define(this, springContext);
    }

    public void add(String path, Action action) {
        assert path != null && action != null && !actions.containsKey(path);
        synchronized (actions) {
            actions.put(path, action);
        }
    }

    public Action get(String uri) {
        if (uri == null || uri.length() < contextPath.length()) {
            return null;
        }
        String path = uri.substring(contextPath.length());
        if (path.length() <= 0) {
            path = "INDEX";
        }
        return actions.get(path);
    }
}
