package io.pummelo.admin.config;

import com.typesafe.config.Config;

/**
 * Created by PummeloDeveloper on 16/4/10.
 */
public class PummeloConfiguration {

    private Config config;

    PummeloConfiguration(Config config) {
        this.config = config;
    }

    public String getString(String path) {
        return null;
    }

    public String getString(String path, String defaultValue) {
        return (config.hasPath(path)) ? config.getString(path) : defaultValue;
    }
}
