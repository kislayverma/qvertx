package io.qvertx.service.profiler;

public class IProfDataHandle {
    private final long startTime;
    private final String contextName;
    public IProfDataHandle(long startTime, String contextName) {
        this.startTime = startTime;
        this.contextName = contextName;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getContextName() {
        return contextName;
    }
}
