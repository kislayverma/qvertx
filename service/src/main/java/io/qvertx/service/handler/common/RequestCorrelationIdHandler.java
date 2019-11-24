package io.qvertx.service.handler.common;

import io.qvertx.service.handler.HandlerConstants;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.util.UUID;

/**
 * This io.qvertx.handler maintains a request id header across request-response.
 *
 * @see
 * <a href="https://github.com/cyngn/vertx-util/blob/master/src/main/java/com/cyngn/vertx/web/handler/RequestIdResponseHandler.java">cyngn/vertx-util</a>
 * @author Kislay Verma
 */
 class RequestCorrelationIdHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext routingContext) {
        String incomingRequestId = routingContext.request().getHeader(HandlerConstants.REQUEST_CORRELATION_ID_HEADER_NAME);

        // Create new request id if the request doesnt have one
        if (incomingRequestId == null || incomingRequestId.isEmpty()) {
            incomingRequestId = UUID.randomUUID().toString();
            routingContext.put(HandlerConstants.REQUEST_CORRELATION_ID_HEADER_NAME, incomingRequestId);
        }

        routingContext.addHeadersEndHandler(a -> {
            // grab the header from request and add to response if present
            String requestId = routingContext.request().getHeader(HandlerConstants.REQUEST_CORRELATION_ID_HEADER_NAME);
            if (requestId != null && !requestId.isEmpty()) {
                routingContext.response().putHeader(HandlerConstants.REQUEST_CORRELATION_ID_HEADER_NAME, requestId);
            }
            String contentType = routingContext.request().getHeader(HandlerConstants.ACCEPT_HEADER_NAME);
            if (contentType != null && !contentType.isEmpty()) {
                routingContext.response().putHeader(HandlerConstants.CONTENT_TYPE_HEADER_NAME, contentType);
            }
        });
        routingContext.next();
    }
}
