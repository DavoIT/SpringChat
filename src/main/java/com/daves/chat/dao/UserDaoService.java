package com.daves.chat.dao;

import com.daves.chat.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {
    private static final List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static {
        users.add(new User(1, "Davit", new Date()));
        users.add(new User(2, "Varazadat", new Date()));
        users.add(new User(3, "Lendrush", new Date()));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User add(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User get(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
