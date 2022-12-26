package com.example.simplecamelspringboot.processor;

import com.example.simplecamelspringboot.beans.NameAddress;
import com.example.simplecamelspringboot.beans.OutboundNameAddress;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class NameAddressProcessor implements Processor {

   //Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void process(Exchange exchange) throws Exception {
        NameAddress nameAddress = exchange.getIn().getBody(NameAddress.class);
        exchange.getIn().setBody(new OutboundNameAddress(nameAddress.getName(),returnFormattedAddress(nameAddress)));
        exchange.getIn().setHeader("consumedId", nameAddress.getId());
    }

    private String returnFormattedAddress(NameAddress nameAddress) {
        StringBuilder concatenatedAddress = new StringBuilder(200);
        concatenatedAddress.append(nameAddress.getHouseNumber());
        concatenatedAddress.append(" " + nameAddress.getCity() + ",");
        concatenatedAddress.append(" " + nameAddress.getState());
        concatenatedAddress.append(" " + nameAddress.getPostalCode());
        return concatenatedAddress.toString();
    }
}
