package io.qvertx.service.handler.common;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

/**
 *
 * @author Kislay Verma
 */
public class RequestHeaderValidationHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        if (routingContext.request().getHeader("Accept") == null || "".equals(routingContext.request().getHeader("Accept"))) {
            response.setStatusCode(400).setStatusMessage("Accept header not set").end();
        } else if (routingContext.request().getHeader("Content-Type") == null || "".equals(routingContext.request().getHeader("Content-Type"))) {
            response.setStatusCode(400).setStatusMessage("Content-Type header not set").end();
        } else if (routingContext.request().getHeader("Authorization") == null || "".equals(routingContext.request().getHeader("Authorization"))) {
            response.setStatusCode(400).setStatusMessage("Authorization header not set").end();
        } else {
            routingContext.next();
        }
    }
}
