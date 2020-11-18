package com.odsinada.ing.controllers;


import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.odsinada.ing.model.Address;
import com.odsinada.ing.model.UserDetails;
import com.odsinada.ing.repository.UserDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("test_provider")
@PactFolder("pacts")
public class UserDetailsProviderTest {

    @LocalServerPort
    private int port;

    @MockBean
    private UserDetailsRepository userDetailsRepository;

    @BeforeEach
    void setupTestTarget(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("test GET user details")
    public void shouldTestGetUserDetails() {
        UserDetails existingUserDetails = UserDetails.builder()
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
        when(userDetailsRepository.findById(1L)).thenReturn(Optional.of(existingUserDetails));
    }

    @State("test PUT user details")
    public void shouldTestPutUserDetails() {
        UserDetails existingUserDetails = UserDetails.builder()
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
        when(userDetailsRepository.findById(1L)).thenReturn(Optional.of(existingUserDetails));
    }

}
