package com.odsinada.ing.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
public class UserDetailsConsumerTest {

    @Test
    @PactTestFor(pactMethod = "putUserDetailsPact")
    public void putUser(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Put(mockServer.getUrl() + "/api/userdetails/1")
                .bodyString("{\n" +
                        "  \"title\": \"Mrs\",\n" +
                        "  \"firstName\": \"Kulafu\",\n" +
                        "  \"lastName\": \"Kenzoflu\",\n" +
                        "  \"gender\": \"Female\",\n" +
                        "  \"address\": {\n" +
                        "    \"street\": \"91 Google Street\",\n" +
                        "    \"city\": \"Alexandria\",\n" +
                        "    \"state\": \"VIC\",\n" +
                        "    \"postCode\": 2067\n" +
                        "  }\n" +
                        "}", ContentType.APPLICATION_JSON)
                .execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(204);
    }

    @Test
    @PactTestFor(pactMethod = "getUserDetailsPact")
    public void getUser(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/api/userdetails/1").execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
    }

    @Pact(provider = "test_provider", consumer = "test_consumer")
    public RequestResponsePact getUserDetailsPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test GET user details")
                    .uponReceiving("GET request")
                    .path("/api/userdetails/1")
                    .method("GET")
                .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body("{\n" +
                            "    \"title\": \"Mr\",\n" +
                            "    \"firstName\": \"Jacob\",\n" +
                            "    \"lastName\": \"Odsinada\",\n" +
                            "    \"gender\": \"Male\",\n" +
                            "    \"address\": {\n" +
                            "        \"street\": \"12345 Google Street\",\n" +
                            "        \"city\": \"Sydney\",\n" +
                            "        \"state\": \"NSW\",\n" +
                            "        \"postCode\": 2061\n" +
                            "    }\n" +
                            "}")
                .toPact();

    }

    @Pact(provider = "test_provider", consumer = "test_consumer")
    public RequestResponsePact putUserDetailsPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test PUT user details")
                .uponReceiving("PUT REQUEST")
                .method("PUT")
                .path("/api/userdetails/1")
                .headers(headers)
                .body("{\n" +
                        "  \"title\": \"Mrs\",\n" +
                        "  \"firstName\": \"Kulafu\",\n" +
                        "  \"lastName\": \"Kenzoflu\",\n" +
                        "  \"gender\": \"Female\",\n" +
                        "  \"address\": {\n" +
                        "    \"street\": \"91 Google Street\",\n" +
                        "    \"city\": \"Alexandria\",\n" +
                        "    \"state\": \"VIC\",\n" +
                        "    \"postCode\": 2067\n" +
                        "  }\n" +
                        "}")
                .willRespondWith()
                .status(204)
                .toPact();

    }
}
