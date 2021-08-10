package com.daves.chat.repository;

import com.daves.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.id != ?1 ORDER BY id ASC")
    public List<User> getAllUsers(Long id);

    public void deleteById(@NonNull Long id);

    @Query(value = "SELECT u FROM User u WHERE u.username = ?1")
    public User getUserByUsername(String username);

    @Query(value = "update User u set u.username = :username where u.id = :id")
    public void updateUsername(@Param("id") Long id, @Param("username") String username);
}
