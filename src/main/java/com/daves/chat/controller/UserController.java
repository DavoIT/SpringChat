package com.daves.chat.controller;

import com.daves.chat.exception.IncorrectKeyUsedException;
import com.daves.chat.exception.UserAlreadyExistsException;
import com.daves.chat.exception.UserNotFoundException;
import com.daves.chat.model.User;
import com.daves.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
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
    public User signUpWithUsername(@RequestBody User signUpRequestBody) {
        String username = signUpRequestBody.getUsername();
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            if (username == null || username.isEmpty()) {
                throw new IncorrectKeyUsedException();
            }
            return userRepository.save(new User(username));
        } else {
            throw new UserAlreadyExistsException(username);
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

    @GetMapping("/user")
    public User getUserById(@RequestParam Long id) {
        try {
            User u = userRepository.getById(id);
            return u;
        } catch (EntityNotFoundException e) {
            throw new UserNotFoundException(id.toString());
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
