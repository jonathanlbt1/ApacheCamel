package com.example.simplecamelspringboot.components;

import com.example.simplecamelspringboot.processor.NameAddressProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LegacyFileRoute extends RouteBuilder {

    Logger logger = LoggerFactory.getLogger(getClass());
    BeanIODataFormat inboundDataFormat = new BeanIODataFormat("inboundMessageBeanIOMapping.xml",
            "inputMessageStream");

    @Override
    public void configure() throws Exception {

        from("file:src/data/input?fileName=inputFile.csv")
                .routeId("legacyFileMoveRouteId")
                .split(body().tokenize("\n",1,true))
                .unmarshal(inboundDataFormat)
                    .process(new NameAddressProcessor())
                    .log(LoggingLevel.INFO, "Transformed body: ${body}")
                    .convertBodyTo(String.class)
                    .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
                .end();

    }
}
