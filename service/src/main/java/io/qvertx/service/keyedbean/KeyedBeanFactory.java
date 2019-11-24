package io.qvertx.service.keyedbean;

import javax.annotation.PostConstruct;

/**
 * This is the base interface for implementing a factory which returns the intended type of {@link KeyedBean} based
 * on the input key.
 * @author Kislay Verma
 * @param <T> The specific KeyedBean implementation to be returned
 */
public interface KeyedBeanFactory<T extends KeyedBean> {

    public void init();

    public T getBean(String key) throws RuntimeException;
}
