package org.hao.weather.yahoo;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class YahooRetrieverImpl implements YahooRetriever {

    private static Logger log = Logger.getLogger(YahooRetrieverImpl.class);

    @Autowired
    private YahooServer yahooServer;

    /*
     * (non-Javadoc)
     * 
     * @see org.hao.weather.yahoo.YahooRetriever#retrieve(java.lang.String)
     */
    @Override
    public final InputStream retrieve(final String locationCode) throws Exception {
        log.info("Retrieving Weather Data for " + locationCode);
        String url = yahooServer.getServiceUrl() + locationCode + "&" + TEMPARATURE_CELSIUS_CODE;
        URLConnection conn;
        // Use this if you need to connect via a corporate proxy
        if (!StringUtils.isEmpty(yahooServer.getProxyHost()) && yahooServer.getProxyPort() != 0) {
            SocketAddress addr = new InetSocketAddress(yahooServer.getProxyHost(), yahooServer.getProxyPort());
            Proxy httpProxy = new Proxy(Proxy.Type.HTTP, addr);
            conn = new URL(url).openConnection(httpProxy);
        } else {
            conn = new URL(url).openConnection();
        }

        return conn.getInputStream();
    }
}
