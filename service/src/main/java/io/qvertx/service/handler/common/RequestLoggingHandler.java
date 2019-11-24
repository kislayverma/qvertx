package io.qvertx.service.handler.common;

import io.qvertx.service.handler.HandlerConstants;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This io.qvertx.handler logs incoming request and its outgoing response in a pretty format.
 *
 * @author Kislay Verma
 */
public class RequestLoggingHandler implements Handler<RoutingContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggingHandler.class);

    @Override
    public void handle(RoutingContext routingContext) {
        String incomingRequestId = routingContext.request().getHeader(HandlerConstants.REQUEST_CORRELATION_ID_HEADER_NAME);
        StringBuilder requestLogBuilder = new StringBuilder("\n------------------------------------------\nIncoming Request\n------------------------------------------")
            .append("\nRequest Id : ").append(incomingRequestId == null || incomingRequestId.isEmpty() ? "None" : incomingRequestId)
            .append("\nAddress : ").append(routingContext.request().absoluteURI())
            .append("\nHeaders : ").append(routingContext.request().headers().entries())
            .append("\nPayload : ").append(routingContext.getBodyAsString())
            .append("\n------------------------------------------");
        LOGGER.info(requestLogBuilder.toString());

        routingContext.next();
    }
}

