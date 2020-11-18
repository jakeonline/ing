package com.odsinada.ing.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDetailsTest {

    @Test
    public void shouldUpdateNonIdFields(){
        // arrange
        UserDetails destination = UserDetails.builder()
                .id(1L)
                .title("mr")
                .firstName("john")
                .lastName("doe")
                .gender("male")
                .address(Address.builder()
                        .id(90L)
                        .street("main street")
                        .city("north sydney")
                        .state("nsw")
                        .postCode(2060)
                        .build())
                .build();
        UserDetails source = UserDetails.builder()
                .id(2L)
                .title("mrs")
                .firstName("jane")
                .lastName("smith")
                .gender("female")
                .address(Address.builder()
                        .id(80L)
                        .street("branch street")
                        .city("western sydney")
                        .state("vic")
                        .postCode(2050)
                        .build())
                .build();

        // act
        destination.updateFrom(source);

        // assert
        assertThat(destination.getId()).isNotEqualTo(source.getId());

        assertThat(destination.getTitle()).isEqualTo(source.getTitle());
        assertThat(destination.getFirstName()).isEqualTo(source.getFirstName());
        assertThat(destination.getLastName()).isEqualTo(source.getLastName());
        assertThat(destination.getGender()).isEqualTo(source.getGender());

        assertThat(destination.getAddress().getId()).isNotEqualTo(source.getAddress().getId());

        assertThat(destination.getAddress().getStreet()).isEqualTo(source.getAddress().getStreet());
        assertThat(destination.getAddress().getCity()).isEqualTo(source.getAddress().getCity());
        assertThat(destination.getAddress().getState()).isEqualTo(source.getAddress().getState());
        assertThat(destination.getAddress().getPostCode()).isEqualTo(source.getAddress().getPostCode());
    }
}
