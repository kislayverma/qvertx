package io.qvertx.service.context;

import java.io.Serializable;
import java.util.Optional;

public class Context implements Serializable {

    private static final ThreadLocal<InvocationContext> threadLocalContextInfo = new ThreadLocal<>();

    public static void setContextInfo(InvocationContext contextInfo) {
        threadLocalContextInfo.set(contextInfo);
    }

    /**
     * @return contextInfo for the current thread
     */
    public static Optional<InvocationContext> getContextInfo() {
        return (threadLocalContextInfo.get() != null) ?
            Optional.ofNullable(threadLocalContextInfo.get()) : Optional.empty();
    }
}
