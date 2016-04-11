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
}
