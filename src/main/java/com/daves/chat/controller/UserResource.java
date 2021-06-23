package com.daves.chat.controller;

import com.daves.chat.dao.UserDaoService;
import com.daves.chat.exception.UserNotFoundException;
import com.daves.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    @Autowired
    private final UserDaoService service = new UserDaoService();

    @GetMapping("/say-hello")
    public String sayHello() {
        return "Barlus, lav es?!";
    }

    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/users")
    public User getUser(@RequestParam Integer id) {
        User user = service.get(id);
        if (user == null) {
            throw new UserNotFoundException("id: " + id);
        }
        return service.get(id);
    }

    @DeleteMapping("/users")
    public void deleteUser(@RequestParam Integer id) {
        User user = service.deleteById(id);
        if (user == null) {
            throw new UserNotFoundException("id: " + id);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        User newUser = service.add(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


}
