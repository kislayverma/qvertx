package io.qvertx.service.routes;

import io.qvertx.service.keyedbean.ContextAwareKeyedBeanFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author Kislay Verma
 */
@Component(value = "routeConfigurerFactory")
public class RouteConfigurerFactory extends ContextAwareKeyedBeanFactory<IRouteConfigurer> {
    public List<IRouteConfigurer> getAll() {
        return this.getBeans();
    }

    @Override
    protected Class getKeyedBeanClass() {
        return IRouteConfigurer.class;
    }
}
