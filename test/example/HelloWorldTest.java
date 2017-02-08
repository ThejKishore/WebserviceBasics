package example;

import com.sun.javafx.binding.BidirectionalBinding;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;
import example.handlers.client.ClientSoapHandler;
import example.handlers.client.SoapHandlerResolver;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by ThejKishore on 2/7/2017.
 */

public class HelloWorldTest {

    @Test
    public void testHelloWorldViaWsInterface() throws MalformedURLException {
        HelloWorldWs helloWorldPort = getService();


        BindingProvider bindingProvider = (BindingProvider)helloWorldPort;
        bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS,
                Collections.singletonMap("name", Collections.singletonList("Value")));

//        headers = (Map<String,List<String>>)((BindingProvider)port).getResponseContext().get(MessageContext.HTTP_RESPONSE_HEADERS);
        assertEquals("Hello, world, from Thej",helloWorldPort.sayHelloWorldFrom("Thej"));
    }

    @Test
    public void testHelloWorldViaWsInterface1() throws MalformedURLException {
        HelloWorldWs helloWorldPort = getService();
        Header asd = Headers.create(new QName("http://example/wsdl","name"), "Value");
        WSBindingProvider bindingProvider = (WSBindingProvider)helloWorldPort;
        bindingProvider.setOutboundHeaders(asd);

        assertEquals("Hello, world, from Kishore",helloWorldPort.sayHelloWorldFrom("Kishore"));
    }

    @Test
    public void testHelloWorldViaWsInterface2() throws MalformedURLException {
        Service service = Service.create(
                new URL("http://localhost:9000/HelloWorld?wsdl"),
                new QName("http://example/wsdl","HelloWorld"));
        SoapHandlerResolver soapHandlerResolver = new SoapHandlerResolver();
        service.setHandlerResolver(soapHandlerResolver);

        HelloWorldWs helloWorldPort = service.getPort(HelloWorldWs.class);


        assertEquals("Hello, world, from Karuneegar",helloWorldPort.sayHelloWorldFrom("Karuneegar"));
    }

    private HelloWorldWs getService() throws MalformedURLException{
        Service service = Service.create(
                new URL("http://localhost:9000/HelloWorld?wsdl"),
                new QName("http://example/wsdl","HelloWorld"));

        /**
         * another way to add handler is commented below.
         *
         SoapHandlerResolver soapHandlerResolver = new SoapHandlerResolver();
         service.setHandlerResolver(soapHandlerResolver);

         */

        assertNotNull(service);

        HelloWorldWs helloWorldPort = service.getPort(HelloWorldWs.class);

        return helloWorldPort;
    }
}
