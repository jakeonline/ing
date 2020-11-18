package com.odsinada.ing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    Long id;
    String title;
    String firstName;
    String lastName;
    String gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    Address address;

    public void updateFrom(UserDetails source) {
        this.setTitle(source.getTitle());
        this.setFirstName(source.getFirstName());
        this.setLastName(source.getLastName());
        this.setGender(source.getGender());
        this.getAddress().setStreet(source.getAddress().getStreet());
        this.getAddress().setCity(source.getAddress().getCity());
        this.getAddress().setState(source.getAddress().getState());
        this.getAddress().setPostCode(source.getAddress().getPostCode());
    }
}
