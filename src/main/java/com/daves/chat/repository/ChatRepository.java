package com.daves.chat.repository;

import com.daves.chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @NonNull
    public List<Chat> findAll();

    public void deleteById(@NonNull Long id);

    @Query("SELECT c FROM Chat c WHERE (c.participantId1 = ?1 AND c.participantId2 = ?2) or (c.participantId1 = ?2 AND c.participantId2 = ?1)")
    public Chat getChatWithParticipants(Long id1, Long id2);

    @Query(value = "SELECT c FROM Chat c WHERE c.participantId1 = ?1 or c.participantId2 = ?1")
    public List<Chat> getChatsWithParticipant(Long id);
}
