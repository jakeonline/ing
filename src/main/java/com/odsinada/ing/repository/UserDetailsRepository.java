package com.odsinada.ing.repository;

import com.odsinada.ing.model.UserDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDetailsRepository extends CrudRepository<UserDetails, Long> {
    Optional<UserDetails> findById(long id);
}
