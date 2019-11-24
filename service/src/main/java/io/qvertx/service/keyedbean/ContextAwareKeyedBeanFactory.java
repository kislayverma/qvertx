package io.qvertx.service.keyedbean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a Spring Context aware implementation of the{@link KeyedBeanFactory} which loads the beans of 
 * the needed type (returned by the getKeyedBeanClass() method) from the application Context.
 * 
 * @author Kislay Verma
 * @param <K> The type of Keyed Beans to be loaded
 * @see "http://opengrok.mynt.myntra.com:10000/source/xref/ordermanagement/oms-service/src/main/java/com/myntra/oms/strategy/splitting/SplittingStrategyFactory.java"
 */
public abstract class ContextAwareKeyedBeanFactory<K extends KeyedBean> 
    extends AbstractKeyedBeanFactory<K> implements ApplicationContextAware {

    private ApplicationContext context;

    abstract protected Class getKeyedBeanClass();

    @Override
    protected List<K> getBeans() {
        return new ArrayList<>(context.getBeansOfType(getKeyedBeanClass(), true, true).values());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
