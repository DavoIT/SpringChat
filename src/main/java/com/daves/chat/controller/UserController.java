package com.daves.chat.controller;

import com.daves.chat.exception.IncorrectKeyUsedException;
import com.daves.chat.exception.UserAlreadyExistsException;
import com.daves.chat.repository.UserRepopo;
import com.daves.chat.exception.UserNotFoundException;
import com.daves.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserRepopo userRep;

    @GetMapping("/all-users")
    public List<User> getAllUsers(@RequestParam Long id) {
        return userRep.getAllUsers(id);
    }

    @GetMapping("/login")
    public User loginWithUsername(@RequestParam String username) {
        User user = userRep.getUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        } else {
            return user;
        }
    }

    @PostMapping("/sign-up")
    public User signUpWithUsername(@RequestBody User signUpRequestBody) {
        String username = signUpRequestBody.getUsername();
        User user = userRep.getUserByUsername(username);
        if (user == null) {
            if (username == null || username.isEmpty()) {
                throw new IncorrectKeyUsedException();
            }
            return userRep.save(new User(username));
        } else {
            throw new UserAlreadyExistsException(username);
        }
    }

    @GetMapping("/users")
    public User getUser(@RequestParam Long id) {
        Optional<User> u = userRep.findById(id);
        if (!u.isPresent()) {
            throw new UserNotFoundException(id.toString());
        } else {
            return u.get();
        }
    }

    @DeleteMapping("/users")
    public void deleteUser(@RequestParam Long id) {
        if (!userRep.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRep.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        User newUser = userRep.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/users")
    public void updateUser(@RequestBody User user) {
        User existingUser = userRep.getById(user.getId());
        existingUser.setUsername(user.getUsername());
        userRep.save(existingUser);
    }
}
