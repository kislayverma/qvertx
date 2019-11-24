package io.qvertx.service.routes;

import io.qvertx.service.keyedbean.KeyedBean;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 *
 * @author Kislay Verma
 */
public interface IRouteConfigurer extends KeyedBean {
    Router setRouteHandlers(Router router, Vertx vertx);
}
