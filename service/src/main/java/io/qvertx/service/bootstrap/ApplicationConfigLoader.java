package io.qvertx.service.bootstrap;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.io.IOException;

/**
 * @author Kislay Verma
 */
public class ApplicationConfigLoader {
    private static final String DEFAULT_APP_CONFIG_FILE_PATH = "conf/application-config.yaml";
    private JsonObject props;

    public ApplicationConfigLoader(Vertx vertx) throws IOException {
        ConfigStoreOptions store = new ConfigStoreOptions().setType("file").setFormat("yaml")
            .setConfig(new JsonObject().put("path", DEFAULT_APP_CONFIG_FILE_PATH));

        ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(store));

        retriever.getConfig(result -> {
            if (result.failed()) {
                throw new IllegalStateException("Could not load application config", result.cause());
            }
            props = result.result();
        });
    }

    public JsonObject getProperties() {
        return props;
    }
}
