package io.qvertx.service.handler;

import io.qvertx.service.exception.BadClientException;
import io.vertx.ext.web.RoutingContext;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kislay Verma
 */
public abstract class AbstractCustomRouteHandler implements CustomRouteHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCustomRouteHandler.class);

    @Override
    public void handle(RoutingContext routingContext) {
        setProfilerContext(routingContext);

//        IProfDataHandle profHandle = Profiler.startTiming(getProfilingKey());
//        Profiler.increment(getProfilingKey());

//        try {
            handleRequest(routingContext).thenApply(resp -> {
                LOGGER.debug("Response : " + resp);
                routingContext.response().setStatusCode(200).end(resp);

//                Profiler.endTiming(profHandle);

                return null;
            }).exceptionally(ex -> {
                if (ex instanceof BadClientException) {
                    LOGGER.error("Bad request", ex);
//                    Profiler.endTiming(profHandle);
                    routingContext.response().setStatusCode(400);
                    routingContext.response().setStatusMessage(ex.getMessage());
                    routingContext.response().end();
                } else {
                    LOGGER.error("Internal Server Error", ex);
//                    Profiler.endTiming(profHandle);
                    routingContext.response().setStatusCode(500);
                    routingContext.response().setStatusMessage(ex.getMessage());
                    routingContext.response().end();
                }
                return null;
            });
//        } catch (BadClientException ex) {
//            LOGGER.error("Bad request", ex);
//            Profiler.endTiming(profHandle);
//            routingContext.response().setStatusCode(400);
//            routingContext.response().setStatusMessage(ex.getMessage());
//            routingContext.response().end();
//        } catch (BadServerException ex) {
//            LOGGER.error("Server error", ex);
//            Profiler.endTiming(profHandle);
//            routingContext.response().setStatusCode(500);
//            routingContext.response().setStatusMessage(ex.getMessage());
//            routingContext.response().end();
//        }
    }

    private void setProfilerContext(RoutingContext routingContext) {
        String path = routingContext.request().path();
        for (Map.Entry<String, String> pathParam : routingContext.pathParams().entrySet()) {
            path = path.replaceAll("\\/" + pathParam.getValue() + "\\/", "-" + pathParam.getKey() + "-");
        }

        // This is needed because the last path param (end of the URL) may not have a trailing '/'
        // and hence may not have gotten caught in the previous loop.
        // e.g. the previous loop will not catch the 'CANCEL_ORDER' part of 
        // http://example.com/buyer/1/order/106063083659295001101/CANCEL_ORDER
        for (Map.Entry<String, String> pathParam : routingContext.pathParams().entrySet()) {
            path = path.replaceAll("\\/" + pathParam.getValue(), "-" + pathParam.getKey() + "");
        }

        // Now remove all slashes
        path = path.replaceAll("\\/", "-");

//        IProfiler.setContext(path);
    }

//    protected ContextInfo getContextInfo(RoutingContext routingContext) {
//        return (ContextInfo) routingContext.get(HandlerConstants.CONTEXT_INFO);
//    }
}
