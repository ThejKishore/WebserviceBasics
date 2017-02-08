package example;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.HeaderList;
import com.sun.xml.ws.developer.JAXWSProperties;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceContext;

/**
 * Created by ThejKishore on 2/7/2017.
 */
@WebService(
        portName = "HelloWorldPort",
        serviceName = "HelloWorld",
        targetNamespace = "http://example/wsdl",
        endpointInterface = "example.HelloWorldWs"
)
@HandlerChain(file="handlers.xml")
public class HelloWorld implements HelloWorldWs{


  @Resource
  WebServiceContext context;

  private static final QName MYHEADER = new QName("name");

  @WebMethod
  public String sayHelloWorldFrom(String from) {
    String result = "Hello, world, from " + from;
    System.out.println(result);
    return result;
  }
  public static void main(String[] argv) {
    Object implementor = new HelloWorld ();
    String address = "http://localhost:9000/HelloWorld";
    Endpoint.publish(address, implementor);
  }
}
