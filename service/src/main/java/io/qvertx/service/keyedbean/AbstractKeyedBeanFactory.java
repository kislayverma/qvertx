package io.qvertx.service.keyedbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for building implementations of {@link KeyedBeanFactory} interface. Out of the box, 
 * this takes care of looking up and returning the required bean to the caller, leaving only the details of how to 
 * actually locate and load the beans to the factory implementation.
 * 
 * @author Kislay Verma
 * @param <K> The types of KeyedBean to be returned
 *
 */
public abstract class AbstractKeyedBeanFactory<K extends KeyedBean> implements KeyedBeanFactory {

    private Map<String, K> keyToBeanMap;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractKeyedBeanFactory.class);

    abstract protected List<K> getBeans();

    @PostConstruct
    @Override
    public void init() {
        Map<String, K> h = new HashMap<>();

        for (K bean : getBeans()) {
            try {
                LOGGER.info("Adding bean with key :" + bean.getKey() + "-- class name :" + bean.getClass());
                h.put(bean.getKey(), bean);
            } catch (Exception e) {
                LOGGER.debug("Error initializing ContextAwareKeyedBeanFactory", e);
            }
        }
        synchronized (this.getClass()) {
            keyToBeanMap = Collections.unmodifiableMap(h);
        }
    }

    @Override
    public K getBean(String key) {
        K bean = keyToBeanMap.get(key);
        if (bean == null) {
            LOGGER.error("No bean with key " + key);
        }

        return bean;
    }
}
