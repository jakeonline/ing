package com.odsinada.ing.controllers;

import com.odsinada.ing.model.UserDetails;
import com.odsinada.ing.repository.UserDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsControllerTest {
    private UserDetailsController controller;
    @Mock
    private UserDetailsRepository userDetailsRepository;
    @Mock
    private UserDetails userDetailInDb;
    @Mock
    private UserDetails userDetailsArg;

    @BeforeEach
    public void setup(){
        controller = new UserDetailsController(userDetailsRepository);
    }

    @Test
    public void shouldReturnExistingUserDetails(){
        // arrange
        Optional<UserDetails> optUserDetailInDb = Optional.of(userDetailInDb);
        when(userDetailsRepository.findById(12345L)).thenReturn(optUserDetailInDb);
        // act
        UserDetails userDetails = controller.getUserDetails("12345");

        // assert
        assertThat(userDetails).isEqualTo(userDetailInDb);
    }

    @Test
    public void shouldReturnNotFoundForInexistentUserDetails(){
        // arrange
        Optional<UserDetails> optUserDetailInDb = Optional.ofNullable(null);
        when(userDetailsRepository.findById(67890)).thenReturn(optUserDetailInDb);

        // act
        Throwable thrown = catchThrowable(() -> controller.getUserDetails("67890"));

        // assert
        assertThat(thrown).isInstanceOf(ResponseStatusException.class);
        ResponseStatusException exc = (ResponseStatusException) thrown;
        assertThat(exc.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldReturnBadRequestForIncorrectUserId(){
        // arrange
        // act
        Throwable thrown = catchThrowable(() -> controller.getUserDetails("notAnInteger"));

        // assert
        assertThat(thrown).isInstanceOf(ResponseStatusException.class);
        ResponseStatusException exc = (ResponseStatusException) thrown;
        assertThat(exc.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exc.getReason()).isEqualTo("User id should be numeric");
    }

    @Test
    public void shouldUpdateExistingUserDetails(){
        // arrange
        Optional<UserDetails> optUserDetailInDb = Optional.of(userDetailInDb);
        when(userDetailsRepository.findById(12345L)).thenReturn(optUserDetailInDb);

        // act
        controller.putUserDetails("12345", userDetailsArg);

        // assert
        verify(userDetailInDb).updateFrom(userDetailsArg);
        verify(userDetailsRepository).save(userDetailInDb);
    }
}
