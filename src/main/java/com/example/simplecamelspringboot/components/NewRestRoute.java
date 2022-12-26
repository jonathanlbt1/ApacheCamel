package com.example.simplecamelspringboot.components;

import com.example.simplecamelspringboot.beans.NameAddress;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class NewRestRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("jetty")
                .host("0.0.0.0")
                .port(8080)
                .bindingMode(RestBindingMode.json)
                .enableCORS(true);

        rest("masterclass")
                .produces("application/json")
                .post("nameAddress").type(NameAddress.class).route()
                .routeId("newRestRouteId")
                .log(LoggingLevel.INFO, String.valueOf(simple("${body}")))
//                .process(new InboundMessageProcessor())
//                .log(LoggingLevel.INFO, "Transformed body: ${body}")
//                .convertBodyTo(String.class)
//                .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n");
//                .to("activemq:queue:nameaddressqueue?exchangePattern=InOnly")
//                .to("jpa:" + InboundNameAddress.class.getName());
                .to("direct:persistMessage")
                .wireTap("seda:sendToQueue")

                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .transform().simple("Message Processed: ${body}")
                .endRest();



        from("direct:persistMessage")
                .routeId("persistMessageRouteId")
                .to("jpa:"+ NameAddress.class.getName());

        from("seda:sendToQueue")
                .routeId("sendToQueueRouteId")
                .to("activemq:queue:nameaddressqueue");

    }

}
