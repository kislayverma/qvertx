package io.qvertx.service.bootstrap;

import io.qvertx.service.routes.BaseRouteConfigurer;
import io.qvertx.service.verticle.HttpVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import java.io.IOException;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kislay Verma
 */
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static final String DEFAULT_SPRING_CONFIG_FILE_PATH = "spring/beans-qvertx-service.xml";
    private static final String DEFAULT_APP_CONFIG_FILE_PATH = "conf/application-config.json";
    private static final String APP_NAME_CONFIG_KEY = "app.name";
    private static final String SPRING_FILE_PATH_CONFIG_KEY = "spring.config.path";
    private static final String VERTICLE_CONFIG_KEY = "verticles";

    private Vertx vertx;
    private JsonObject applicationConfig;
    private SpringContextLoader springContextLoader;

    public Application() {
        vertx = Vertx.vertx();
        LOGGER.info("Starting config load...");
        ConfigStoreOptions store = new ConfigStoreOptions().setType("file").setFormat("json")
            .setConfig(new JsonObject().put("path", DEFAULT_APP_CONFIG_FILE_PATH));

        ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(store));

        retriever.getConfig(result -> {
            if (result.failed()) {
                LOGGER.error("Config load failed");
                throw new IllegalStateException("Could not load application config", result.cause());
            } else {
                applicationConfig = result.result();
                LOGGER.info("Application Config loaded : {}", applicationConfig.toString());

                String appName = applicationConfig.getString(APP_NAME_CONFIG_KEY);
                if (appName == null || appName.isEmpty()) {
                    throw new IllegalArgumentException("app.name not defined in application config");
                }

                springContextLoader =
                    loadSpringConfigs(DEFAULT_SPRING_CONFIG_FILE_PATH + "," +
                        (applicationConfig.getString(SPRING_FILE_PATH_CONFIG_KEY) == null ?
                            "" : applicationConfig.getString(SPRING_FILE_PATH_CONFIG_KEY)));

                deployVerticles(vertx, applicationConfig.getJsonArray(VERTICLE_CONFIG_KEY));
            }
        });
    }

    private SpringContextLoader loadSpringConfigs(String springFilesStr) {
        if (springFilesStr != null) {
            String[] springFilesArr = springFilesStr.split(",");
            if (springFilesArr.length > 0) {
                LOGGER.info("Loading spring config files : {}", springFilesStr);
                return new SpringContextLoader(springFilesArr);
            }
        }
        return  null;
    }

    // This method reads verticle definitions from the application-config file and deploys instances
    private void deployVerticles(Vertx vertx, JsonArray verticleConfigs) {
        if (verticleConfigs == null || verticleConfigs.isEmpty()) {
            // No op by default
            LOGGER.info("Not loading verticles by default");
        } else {
            LOGGER.info("Deploying verticles...");
            verticleConfigs.forEach(verticleConfig -> {
                JsonObject verticleConfigJson = (JsonObject) verticleConfig;
                LOGGER.info("Deploying verticle with config {}", verticleConfigJson.toString());
                Verticle v = new HttpVerticle((BaseRouteConfigurer)springContextLoader.getBean("routeConfigurer"));
                vertx.deployVerticle(
                    v,
                    new DeploymentOptions().setConfig(verticleConfigJson.getJsonObject("config")));
                LOGGER.info("Deployed verticle {}", verticleConfigJson.getString("name"));
            });
        }
    }

    public static void main(String[] args) throws IOException {
        LOGGER.info("Launching the application...");
        Application app = new Application();
        LOGGER.info("Application initialization complete");
    }
}
