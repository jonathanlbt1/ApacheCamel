package com.example.simplecamelspringboot;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CamelSpringBootTest
@SpringBootTest
@UseAdviceWith
public class LegacyFileRouteTest {

    @Autowired
    CamelContext context;

    @EndpointInject("mock:result")
    protected MockEndpoint mockEndpoint;

    @Autowired
    ProducerTemplate producerTemplate;

    @Test
    public void testFileMove() throws Exception {

        //Setup of the mock
        String expectedBody = "OutboundNameAddress(name=Jonathan, address= 111  Toronto  ON  0183832)";
        mockEndpoint.expectedBodiesReceived(expectedBody);
        mockEndpoint.expectedMinimumMessageCount(1);

        //Tweak the route definition
        AdviceWith.adviceWith(context, "legacyFileMoveRouteId", routeBuilder -> {
            routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
        });

        //Start the context and validate that the mock is asserted
        context.start();
        mockEndpoint.assertIsSatisfied();
    }

    @Test
    public void testFileMoveByMockingFromEndpoint() throws Exception {
        String expectedBody = "OutboundNameAddress(name=Jonathan, address= 111  Toronto  ON  0183832)";
        mockEndpoint.expectedBodiesReceived(expectedBody);
        mockEndpoint.expectedMinimumMessageCount(1);

        AdviceWith.adviceWith(context, "legacyFileMoveRouteId", routeBuilder -> {
            routeBuilder.replaceFromWith("direct:mockStart");
            routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
        });

        context.start();
        producerTemplate.sendBody("direct:mockStart","name, house_number, city, state, postal_code\nJonathan, 111, Toronto, ON, 0183832");
        mockEndpoint.assertIsSatisfied();
    }
}
