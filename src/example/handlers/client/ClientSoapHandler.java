package example.handlers.client;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

/**
 * Created by ThejKishore on 2/7/2017.
 */
public class ClientSoapHandler implements SOAPHandler<SOAPMessageContext>{
    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        //Outgoing response
        if (outboundProperty.booleanValue()) {

            SOAPMessage message = smc.getMessage();
            try {

                SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.addHeader();

                //add a soap header, name as "mac address"
                QName qname = new QName("http://example/wsdl","name");
                SOAPHeaderElement soapHeaderElement = header.addHeaderElement(qname);
                soapHeaderElement.setTextContent("value");
                System.out.println("Outgoing Request");
                message.writeTo(System.out);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

        } else {
            try {
                //This handler does nothing with the response from the Web Service so
                //we just print out the SOAP message.
                SOAPMessage message = smc.getMessage();
                System.out.println("Incoming Response");
                message.writeTo(System.out);
                System.out.println("");

            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
        return outboundProperty;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }
}
