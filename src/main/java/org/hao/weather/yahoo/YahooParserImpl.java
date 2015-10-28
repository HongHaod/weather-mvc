package org.hao.weather.yahoo;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;
import org.hao.weather.Weather;
import org.springframework.stereotype.Service;

/**
 *
 * @author hao
 *
 */
@Service
public class YahooParserImpl implements YahooParser {

    private static Logger log = Logger.getLogger(YahooParserImpl.class);

    @Override
    public final Weather parse(final InputStream inputStream) throws Exception {
        final Weather weather = new Weather();

        log.info("Creating XML Reader");
        final SAXReader xmlReader = createXmlReader();
        final Document doc = xmlReader.read(inputStream);

        log.info("Parsing XML Response");
        weather.setCity(doc.valueOf("/rss/channel/y:location/@city"));
        weather.setRegion(doc.valueOf("/rss/channel/y:location/@region"));
        weather.setCountry(doc.valueOf("/rss/channel/y:location/@country"));
        weather.setCondition(doc.valueOf("/rss/channel/item/y:condition/@text"));
        weather.setTemperature(doc.valueOf("/rss/channel/item/y:condition/@temp"));
        weather.setWindSpeed(doc.valueOf("/rss/channel/y:wind/@speed"));

        return weather;
    }

    /**
     * Create a reader for XML Yahoo RSS response 
     * @return SAXReader
     */
    private SAXReader createXmlReader() {
        Map<String, String> uris = new HashMap<String, String>();
        uris.put("y", "http://xml.weather.yahoo.com/ns/rss/1.0");

        DocumentFactory factory = new DocumentFactory();
        factory.setXPathNamespaceURIs(uris);

        SAXReader xmlReader = new SAXReader();
        xmlReader.setDocumentFactory(factory);
        return xmlReader;
    }
}
