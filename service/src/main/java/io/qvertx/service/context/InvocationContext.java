package io.qvertx.service.context;

import java.io.Serializable;
import java.util.Base64;
import java.util.Locale;

public class InvocationContext implements Serializable {
    private String userId;
    private String callerId;
    private String serviceId;
    private String requestCorelationId;
    private String serverDetails;
    private AuthInfo authInfo;

    public InvocationContext() {}

    public InvocationContext(String userId, String callerId, String serviceId, String requestCorelationId,
                             String serverDetails, AuthInfo authInfo) {
        this.userId = userId;
        this.callerId = callerId;
        this.serviceId = serviceId;
        this.requestCorelationId = requestCorelationId;
        this.serverDetails = serverDetails;
        this.authInfo = authInfo;
    }

    public String getUserId() {
        return userId;
    }

    public String getCallerId() {
        return callerId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getRequestCorelationId() {
        return requestCorelationId;
    }

    public String getServerDetails() {
        return serverDetails;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }
}
