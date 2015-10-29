/*
 * =====================================================================
 * Copyright (c) NAB 2015
 * =====================================================================
 * (C) 2015 National Australia Bank Ltd [All rights reserved]. This
 * product and related documentation are protected by copyright
 * restricting its use, copying, distribution, and decompilation. No
 * part of this product or related documentation may be reproduced in
 * any form by any means without prior written authorization of
 * National Australia Bank Ltd. No part of this product can be sold,
 * leased, hired out, licensed or circulated in any way without the
 * written authorization of National Australia Bank Ltd. Unless
 * otherwise arranged, third parties may not have access to this
 * product or related documents.
 * =====================================================================
 */
package org.hao.weather.yahoo;

public class YahooServer {
    private String serviceUrl;
    private String proxyHost;
    private int proxyPort;
    private String responseSchemaUrl;
    
    public final String getServiceUrl() {
        return serviceUrl;
    }
    public final void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
    public final String getProxyHost() {
        return proxyHost;
    }
    public final void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }
    public final int getProxyPort() {
        return proxyPort;
    }
    
    public final void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }
    
    public String getResponseSchemaUrl() {
        return responseSchemaUrl;
    }
    
    public void setResponseSchemaUrl(String responseSchemaUrl) {
        this.responseSchemaUrl = responseSchemaUrl;
    }
}
