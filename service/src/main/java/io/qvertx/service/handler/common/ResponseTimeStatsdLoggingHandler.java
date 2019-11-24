package io.qvertx.service.handler.common;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.ResponseTimeHandler;

/**
 * This io.qvertx.handler logs the total time taken by the request (time till the response is flushed to the wire)
 * to Statsd.
 * 
 * @see ResponseTimeHandler
 * @author Kislay Verma
 */
 class ResponseTimeStatsdLoggingHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
//        Profiler.setContext(routingContext.request().path());
//        IProfDataHandle profHandle = IProfiler.startTiming("api");

        routingContext.addBodyEndHandler(v -> {
//            IProfiler.endTiming(profHandle);
        });
        routingContext.next();
    }
}
