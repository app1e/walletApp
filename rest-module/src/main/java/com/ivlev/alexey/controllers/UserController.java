package com.ivlev.alexey.controllers;

import com.ivlev.alexey.models.User;
import com.ivlev.alexey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by aivlev on 1/29/16.
 */
@RestController
@RequestMapping(value = "/wallet/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @RequestMapping(
            value = "/users",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity getAll(){

        return new ResponseEntity(userRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity(userRepository.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
     public @ResponseBody ResponseEntity existByEmail(@RequestParam(value = "email") String email) {
        return new ResponseEntity(userRepository.existByEmail(email), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/users",
            method = RequestMethod.POST)
    public @ResponseBody ResponseEntity addUser(@RequestBody User payload){
        if(payload == null){
            throw new IllegalArgumentException();
        } else {
            return new ResponseEntity(userRepository.save(new User(payload.getEmail(), payload.getFullName())), HttpStatus.OK);
        }

    }

}
