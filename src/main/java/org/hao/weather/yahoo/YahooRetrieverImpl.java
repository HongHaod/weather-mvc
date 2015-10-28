package org.hao.weather.yahoo;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class YahooRetrieverImpl implements YahooRetriever {

    private static Logger log = Logger.getLogger(YahooRetrieverImpl.class);

    /* (non-Javadoc)
     * @see org.hao.weather.yahoo.YahooRetriever#retrieve(java.lang.String)
     */
    @Override
    public final InputStream retrieve(final String locationCode) throws Exception  {
        log.info("Retrieving Weather Data");
        String url = YAHOOAPI_WEATHER_URL + locationCode + "&" + TEMPARATURE_CELSIUS_CODE;
        // Use this if you need to connect via a corporate proxy
        String proxyHost = "internetproxy";
        int proxyPort = 3128;
        SocketAddress addr = new InetSocketAddress(proxyHost, proxyPort);
        Proxy httpProxy = new Proxy(Proxy.Type.HTTP, addr);
//        URLConnection conn = new URL(url).openConnection(httpProxy);
         URLConnection conn = new URL(url).openConnection();
        return conn.getInputStream();
    }
}
