package com.daves.chat.controller;

import com.daves.chat.exception.UserAlreadyExistsException;
import com.daves.chat.repository.UserRepository;
import com.daves.chat.exception.UserNotFoundException;
import com.daves.chat.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/all-users")
    public List<User> getAllUsers(@RequestParam Long id) {
        return userRepository.getAllUsers(id);
    }

    @GetMapping("/login")
    public User loginWithUsername(@RequestParam String username) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        } else {
            return user;
        }
    }

    @PostMapping("/sign-up")
    public User signUpWithUsername(@RequestBody String username) {
        //TODO Have to get rid of Quotation marks in username without .replaceAll
        String s = username.replaceAll("\"", "");
        User user = userRepository.getUserByUsername(s);
        if (user == null) {
            return userRepository.save(new User(s));
        } else {
            throw new UserAlreadyExistsException(s);
        }
    }

    @GetMapping("/users")
    public User getUser(@RequestParam Long id) {
        Optional<User> u = userRepository.findById(id);
        if (!u.isPresent()) {
            throw new UserNotFoundException(id.toString());
        } else {
            return u.get();
        }
    }

    @DeleteMapping("/users")
    public void deleteUser(@RequestParam Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        User newUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/users")
    public void updateUser(@RequestBody User user) {
        User existingUser = userRepository.getById(user.getId());
        existingUser.setUsername(user.getUsername());
        userRepository.save(existingUser);
    }
}
