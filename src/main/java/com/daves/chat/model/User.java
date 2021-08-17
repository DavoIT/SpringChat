package com.daves.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    @Size(min = 2)
    private String username;

    @ManyToMany
    @JoinTable(name = "user_chats",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    @JsonIgnore
    private List<Chat> chats;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put("id", id);
            result.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
