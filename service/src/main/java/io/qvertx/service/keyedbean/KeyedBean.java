package io.qvertx.service.keyedbean;

/**
 * This is the interface for a class that can be uniquely identified by the key provided by the getKey() method.
 * Classes that want to be hidden behind a single {@link KeyedBeanFactory} should all implement an interface 
 * which extends this and should ensure that all implementations provide different keys. 
 * 
 * @author Kislay Verma
 * @see "http://opengrok.mynt.myntra.com:10000/source/xref/ordermanagement/oms-service/src/main/java/com/myntra/oms/strategy/splitting/SplittingStrategy.java"
 * @see "http://opengrok.mynt.myntra.com:10000/source/xref/ordermanagement/oms-service/src/main/java/com/myntra/oms/strategy/splitting/SplitReleasesByLine.java"
 */
public interface KeyedBean {

    String getKey();
}
