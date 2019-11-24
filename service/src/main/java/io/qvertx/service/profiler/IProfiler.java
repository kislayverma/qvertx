package io.qvertx.service.profiler;

public interface IProfiler {
    IProfDataHandle startTiming(String metricName, String contextName);

    void endTiming(IProfDataHandle handle);

    void increment(String metricName, String contextName);

    void increment(String metricName, int magnitude, String contextName);

    void increment(String metricName, int magnitude, double sampleRate, String contextName);

    void decrement(String metricName, String contextName);

    void decrement(String metricName, int magnitude, String contextName);

    void decrement(String metricName, int magnitude, double sampleRate, String contextName);
}
