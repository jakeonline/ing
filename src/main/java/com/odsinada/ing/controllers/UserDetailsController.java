package com.odsinada.ing.controllers;

import com.odsinada.ing.model.UserDetails;
import com.odsinada.ing.repository.UserDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserDetailsController {

    private UserDetailsRepository userDetailsRepository;

    public UserDetailsController(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @GetMapping("/userdetails/{idPathVar}")
    public UserDetails getUserDetails(@PathVariable String idPathVar) {
        long id;
        try {
            id = Long.valueOf(idPathVar);
        } catch (NumberFormatException nfexc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id should be numeric", nfexc);
        }
        Optional<UserDetails> userDetails = userDetailsRepository.findById(id);

        return userDetails.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }

    @PutMapping("/userdetails/{idPathVar}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void putUserDetails(@PathVariable String idPathVar, @RequestBody UserDetails userDetailsArg) {

        UserDetails userDetailsInDb = getUserDetails(idPathVar);

        userDetailsInDb.updateFrom(userDetailsArg);
        userDetailsRepository.save(userDetailsInDb);
    }

}
