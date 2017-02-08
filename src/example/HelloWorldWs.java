package example;

import javax.jws.WebService;

/**
 * Created by ThejKishore on 2/7/2017.
 */
@WebService(
        portName = "HelloWorldPort",
        serviceName = "HelloWorld",
        targetNamespace = "http://example/wsdl",
        endpointInterface = "example.HelloWorld"
)
public interface HelloWorldWs {

    String sayHelloWorldFrom(String from);
}
