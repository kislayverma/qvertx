package io.qvertx.service.handler;

import io.qvertx.service.exception.BadClientException;
import io.qvertx.service.exception.BadServerException;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.util.concurrent.CompletionStage;

/**
 *
 * @author Kislay Verma
 */
public interface CustomRouteHandler extends Handler<RoutingContext> {
    CompletionStage<String> handleRequest(RoutingContext routingContext) throws BadServerException, BadClientException;
    String getProfilingKey();
}
