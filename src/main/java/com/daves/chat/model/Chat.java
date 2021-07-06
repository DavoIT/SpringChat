package com.daves.chat.model;

import javax.persistence.*;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    @Column(name = "participant_id1", nullable = false)
    private Long participantId1;

    @Column(name = "participant_id2", nullable = false)
    private Long participantId2;

    public Chat() {
    }

    public Chat(Long participantId1, Long participantId2) {
        this.participantId1 = participantId1;
        this.participantId2 = participantId2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParticipantId1() {
        return participantId1;
    }

    public void setParticipantId1(Long participantId1) {
        this.participantId1 = participantId1;
    }

    public Long getParticipantId2() {
        return participantId2;
    }

    public void setParticipantId2(Long participantId2) {
        this.participantId2 = participantId2;
    }
}
