package io.qvertx.service.routes;

import brave.http.HttpTracing;
import brave.vertx.web.VertxWebTracing;
import io.qvertx.service.handler.common.*;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kislay Verma
 */
@Component(value = "routeConfigurer")
public class BaseRouteConfigurer {

    private Vertx vertx;
    private Router router;
    @Autowired
    private RouteConfigurerFactory routeConfigurerFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRouteConfigurer.class);
    private static final Handler<RoutingContext> HEARTBEAT_HANDLER = new HeartbeatHandler();

    public Router setRouteHandlers(Vertx vertx) {
        LOGGER.info("Configuring io.qvertx.routes and registering handlers...");

        this.vertx = vertx;
        this.router = Router.router(vertx);

//        HttpTracing tracing = ZipkinTracingFactory.getNewHttpTracing("vertx-default");
//        if (tracing != null) {
//            VertxWebTracing vertxWebTracing = VertxWebTracing.create(tracing);
//            Handler<RoutingContext> routingContextHandler = vertxWebTracing.routingContextHandler();
//            router.route()
//                  .order(-1) // applies before io.qvertx.routes
//                  .handler(routingContextHandler)
//                  .failureHandler(routingContextHandler);
//        }

        // Bodyhandler to process POST and PUT bodies
        router.route(HttpMethod.POST, "/*").handler(BodyHandler.create());
        router.route(HttpMethod.PUT, "/*").handler(BodyHandler.create());

        // Heartbeat handler
        router.route(HttpMethod.GET, "/heartbeat/").handler(HEARTBEAT_HANDLER);
        router.route(HttpMethod.PUT, "/heartbeat/").handler(HEARTBEAT_HANDLER);

        // Default handler which checks for basic request sanity and mandatory headers and
        // then passes to the next io.qvertx.handler on success
        router.route().handler(new RequestHeaderValidationHandler());

        // Handler to log total response times to statsd
        router.route().handler(new RequestContextCreationHandler());
        router.route().handler(new ResponseLoggingHandler());
        router.route().handler(new RequestLoggingHandler());

        // Load io.qvertx.routes from all custom configurers
        routeConfigurerFactory.getAll().forEach((configurer) -> {
            configurer.setRouteHandlers(router, vertx);
        });

        return router;
    }
}
