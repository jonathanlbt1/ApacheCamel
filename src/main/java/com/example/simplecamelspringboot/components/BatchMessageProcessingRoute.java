package com.example.simplecamelspringboot.components;

import com.example.simplecamelspringboot.beans.NameAddress;
import com.example.simplecamelspringboot.processor.NameAddressProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class BatchMessageProcessingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:batch?period=60000")
                .routeId("batchMessageRouteId")
                .to("jpa:"+ NameAddress.class.getName()+"?namedQuery=fetchAllRows")
                .split(body())
                .log(LoggingLevel.INFO, "Read Row: ${body}")
                .process(new NameAddressProcessor())
                .convertBodyTo(String.class)
                .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
                .toD("jpa:"+ NameAddress.class.getName()+"?nativeQuery=DELETE FROM NAME_ADDRESS where id = ${header.consumedId}&useExecuteUpdate=true")
                .end();

    }
}
