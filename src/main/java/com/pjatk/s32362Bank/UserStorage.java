package com.pjatk.s32362Bank;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserStorage {
    private final ArrayList<User> users;

    public UserStorage() {
        users = new ArrayList<>();
    }

    public void addUser(String id, int balance) {
        users.add(new User(id, balance));
    }

    public void removeUser(String id) {
        users.removeIf(user -> user.getId().equals(id));
    }

    public User getUser(String id) {
        // Tak mi podpowiedzial IntelliJ... mam wrazenie ze to za madre :)
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }
}
