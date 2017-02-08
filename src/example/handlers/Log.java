package example.handlers;

import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.HeaderList;
import com.sun.xml.ws.developer.JAXWSProperties;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ThejKishore on 2/7/2017.
 */
public class Log  implements SOAPHandler<SOAPMessageContext>{

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    private static final QName MYHEADER = new QName("http://example/wsdl","name");

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        try {
            final SOAPMessage message = context.getMessage();
            final SOAPBody body = message.getSOAPBody();
            Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

            if(!outboundProperty) {
                Map<String, List<String>> map = (Map<String, List<String>>) context.get(MessageContext.HTTP_REQUEST_HEADERS);
                if (map != null && !map.isEmpty()) {
                    List<String> contentType = getHTTPHeader(map, "name");
                    if (contentType != null) {
                        StringBuffer strBuf = new StringBuffer();
                        for (String type : contentType) {
                            strBuf.append(type);
                        }
                        System.out.println("name:" + strBuf.toString());
                    }
                }

                HeaderList hl = (HeaderList) context.get(JAXWSProperties.INBOUND_HEADER_LIST_PROPERTY);
                if (hl != null) {
                    Header h = hl.get(MYHEADER, true);
                    System.out.println("Header  name" + h.getStringContent());
                } else {
                    System.out.println("Header List is null");
                }
                System.out.println("Incoming Request");
            } else {
                System.out.println("Outgoing Response");
            }

            message.writeTo(System.out);

            return true;
        } catch (SOAPException e) {
            e.printStackTrace(System.err);
            return false;
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }


    @Override
    public void close(MessageContext context) {

    }

    private List<String> getHTTPHeader( Map<String, List<String>> headers, String header){
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String name = entry.getKey();
            if(name.equalsIgnoreCase(header))
                return entry.getValue();
        }
        return null;
    }
}
