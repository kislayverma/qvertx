package io.qvertx.service.verticle;

import io.qvertx.service.routes.BaseRouteConfigurer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpVerticle extends AbstractVerticle {

    private final BaseRouteConfigurer routeConfigurer;
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpVerticle.class);
    private static final int DEFAULT_PORT = 8080;
    private static final String HTTP_PORT_CONFIG_KEY = "http.port";

    public HttpVerticle(BaseRouteConfigurer routeConfigurer) {
        super();
        this.routeConfigurer = routeConfigurer;
    }

    @Override
    public void start(Future<Void> fut) {
        LOGGER.info("Starting HTTP verticle...");

        // Set up all the routes and their handlers
        Router router = routeConfigurer.setRouteHandlers(vertx);

        // Create the HTTP server
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(
                    // Retrieve the port from the configuration, default to 8080
                    config().getInteger(HTTP_PORT_CONFIG_KEY, DEFAULT_PORT),
                    result -> {
                        if (result.succeeded()) {
                            fut.complete();
                        } else {
                            fut.fail(result.cause());
                        }
                    }
                );
    }
}
