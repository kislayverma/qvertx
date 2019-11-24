package io.qvertx.service.handler.common;

import io.qvertx.service.handler.HandlerConstants;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * This io.qvertx.handler maintains a request id header across request-response.
 *
 * @see
 * <a href="https://github.com/cyngn/vertx-util/blob/master/src/main/java/com/cyngn/vertx/web/handler/RequestIdResponseHandler.java">cyngn/vertx-util</a>
 * @author Kislay Verma
 */
public class RequestContextCreationHandler implements Handler<RoutingContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestContextCreationHandler.class);

    @Override
    public void handle(RoutingContext routingContext) {
        Map<String, String> headers = new HashMap<>();
        routingContext.request().headers().entries().forEach(entry -> {
            headers.put(entry.getKey(), entry.getValue());
        });
//        ContextHelper.setHeadersInContextInfo(headers);
//        ContextInfo ctx = new ContextInfo();
//        ctx = Context.getContextInfo();
//        routingContext.put(HandlerConstants.CONTEXT_INFO, ctx);
//        MDC.put(HandlerConstants.REQUEST_CORRELATION_ID_HEADER_NAME, ctx.getMyntraRequestId());

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

