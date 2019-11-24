package io.qvertx.service.handler.common;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

/**
 * This io.qvertx.handler handles heartbeat requests to move a service in/out of load balancer.
 * @author Kislay Verma
 */
public class HeartbeatHandler implements Handler<RoutingContext> {
    private static boolean HEARTBEAT = false;

    private static final String HEARTBEAT_QUERY_PARAM_NAME = "beat";
    private static final int OK_RESPONSE_CODE = 200;
    private static final int GONE_RESPONSE_CODE = 410;
    private static final int BAD_REQUEST_RESPONSE_CODE = 400;

    @Override
    public void handle(RoutingContext routingContext) {
        if (HttpMethod.GET == routingContext.request().method()) {
            handleHeartbeatFetch(routingContext);
        } else if (HttpMethod.PUT == routingContext.request().method()) {
            handleHeartbeatUpdate(routingContext);
        }
    }

    private void handleHeartbeatFetch(RoutingContext routingContext) {
        if (HEARTBEAT) {
            routingContext.response().setStatusCode(OK_RESPONSE_CODE).end();
        } else {
            routingContext.response().setStatusCode(GONE_RESPONSE_CODE).end();
        }
    }

    private void handleHeartbeatUpdate(RoutingContext routingContext) {
        try {
            boolean heartbeat = Boolean.valueOf(routingContext.request().params().get(HEARTBEAT_QUERY_PARAM_NAME));
            HEARTBEAT = heartbeat;
            routingContext.response().setStatusCode(OK_RESPONSE_CODE).end();
        } catch (Exception ex) {
            routingContext.response().setStatusCode(BAD_REQUEST_RESPONSE_CODE).end();
        }
    }
}
