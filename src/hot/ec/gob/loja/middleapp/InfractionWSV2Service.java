
package ec.gob.loja.middleapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
//@WebServiceClient(name = "InfractionWSV2Service", targetNamespace = "http://middleapp.loja.gob.ec/", wsdlLocation = "http://190.214.31.163:8084/middleApp-1.0-SNAPSHOT/InfractionWSV2?wsdl")
@WebServiceClient(name = "InfractionWSV2Service", targetNamespace = "http://middleapp.loja.gob.ec/", wsdlLocation = "file:/opt/wsdl/InfractionWSV2.wsdl")
public class InfractionWSV2Service
    extends Service
{

    private final static URL INFRACTIONWSV2SERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(ec.gob.loja.middleapp.InfractionWSV2Service.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = ec.gob.loja.middleapp.InfractionWSV2Service.class.getResource(".");
//            url = new URL(baseUrl, "http://10.200.12.106:8084/middleApp-1.0-SNAPSHOT/InfractionWSV2?wsdl");
            url = new URL(baseUrl, "file:/opt/wsdl/InfractionWSV2.wsdl");
//            URL clone_url = new URL(url.toString());
//            HttpURLConnection clone_urlconnection = (HttpURLConnection) clone_url.openConnection();
//            // TimeOut settings
//            clone_urlconnection.setConnectTimeout(10000);
//            clone_urlconnection.setReadTimeout(10000);
//            url = clone_urlconnection.getURL();
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://10.200.12.106:8084/middleApp-1.0-SNAPSHOT/InfractionWSV2?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        } catch (IOException e) {
			e.printStackTrace();
		}
        INFRACTIONWSV2SERVICE_WSDL_LOCATION = url;
    }

    public InfractionWSV2Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public InfractionWSV2Service() {
        super(INFRACTIONWSV2SERVICE_WSDL_LOCATION, new QName("http://middleapp.loja.gob.ec/", "InfractionWSV2Service"));
    }

    /**
     * 
     * @return
     *     returns InfractionWSV2
     */
    @WebEndpoint(name = "InfractionWSV2Port")
    public InfractionWSV2 getInfractionWSV2Port() {
        return super.getPort(new QName("http://middleapp.loja.gob.ec/", "InfractionWSV2Port"), InfractionWSV2.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns InfractionWSV2
     */
    @WebEndpoint(name = "InfractionWSV2Port")
    public InfractionWSV2 getInfractionWSV2Port(WebServiceFeature... features) {
        return super.getPort(new QName("http://middleapp.loja.gob.ec/", "InfractionWSV2Port"), InfractionWSV2.class, features);
    }

}
