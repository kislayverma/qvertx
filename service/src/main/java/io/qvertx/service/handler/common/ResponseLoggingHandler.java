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
public class ResponseLoggingHandler implements Handler<RoutingContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseLoggingHandler.class);

    @Override
    public void handle(RoutingContext routingContext) {

        routingContext.addBodyEndHandler(a -> {
            String requestId = routingContext.request().getHeader(HandlerConstants.REQUEST_CORRELATION_ID_HEADER_NAME);
            StringBuilder responseLogBuilder = new StringBuilder("\n------------------------------------------\nOutgoing Response\n------------------------------------------")
                .append("\nRequest Id : ").append(requestId == null || requestId.isEmpty() ? "None" : requestId)
                .append("\nStatus : ").append(routingContext.response().getStatusCode())
                .append("\nHeaders : ").append(routingContext.response().headers().entries())
                .append("\nPayload : ").append(routingContext.response())
                .append("\n------------------------------------------");
            LOGGER.info(responseLogBuilder.toString());
        });
        routingContext.next();
    }
}

