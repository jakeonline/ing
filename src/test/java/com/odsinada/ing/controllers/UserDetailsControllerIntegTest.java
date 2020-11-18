package com.odsinada.ing.controllers;

import com.odsinada.ing.IngApplication;
import com.odsinada.ing.model.Address;
import com.odsinada.ing.model.UserDetails;
import com.odsinada.ing.repository.UserDetailsRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {IngApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserDetailsControllerIntegTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserDetailsRepository userDetailsRepository;


    @BeforeEach
    public void setup(){
        RestAssured.baseURI = "http://localhost:" + port + "/api/userdetails";
    }

    @Test
    @Sql({"/test-schema.sql", "/test-data.sql"})
    public void shouldGetUserDetails(){
        // arrange
        RequestSpecification request = given()
                .contentType("application/json")
                .auth().basic("user", "userpwd");

        // act
        Response response = request.get("/1");

        // assert
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @Sql({"/test-schema.sql", "/test-data.sql"})
    public void shouldUpdateUserDetails() throws JSONException {
        // arrange
        UserDetails original = userDetailsRepository.findById(1).get();
        UserDetails expectedOriginal = UserDetails.builder()
                .id(1L)
                .title("Mr")
                .firstName("Jacob")
                .lastName("Odsinada")
                .gender("Male")
                .address(Address.builder()
                        .id(1L)
                        .street("12345 Google Street")
                        .city("Sydney")
                        .state("NSW")
                        .postCode(2061)
                        .build())
                .build();
        assertThat(original).isEqualTo(expectedOriginal);

        UserDetails expectedUpdated = UserDetails.builder()
                .id(1L)
                .title("Mrs")
                .firstName("Kulafu")
                .lastName("Kenzoflu")
                .gender("Female")
                .address(Address.builder()
                        .id(1L)
                        .street("91 Google Street")
                        .city("Alexandria")
                        .state("VIC")
                        .postCode(2067)
                        .build())
                .build();

        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "Mrs");
        requestParams.put("firstName", "Kulafu");
        requestParams.put("lastName", "Kenzoflu");
        requestParams.put("gender", "Female");

        JSONObject addressParams = new JSONObject();
        addressParams.put("street", "91 Google Street");
        addressParams.put("city", "Alexandria");
        addressParams.put("state", "VIC");
        addressParams.put("postCode", 2067);

        requestParams.put("address", addressParams);

        RequestSpecification request = given()
                .contentType("application/json")
                .auth().basic("admin", "adminpwd")
                .body(requestParams.toString());

        // act
        Response response = request.put("/1");

        // assert
        assertThat(response.getStatusCode()).isEqualTo(204);
        UserDetails updated = userDetailsRepository.findById(1).get();
        assertThat(updated).isEqualTo(expectedUpdated);
    }

    @Test
    @Sql({"/test-schema.sql", "/test-data.sql"})
    public void shouldDenyUpdatingUserDetailsForNonAdminUser() throws JSONException {
        // arrange
        RequestSpecification request = given()
                .contentType("application/json")
                .auth().basic("user", "userpwd");

        // act
        Response response = request.put("/1");

        // assert
        assertThat(response.getStatusCode()).isEqualTo(403);
    }


}
