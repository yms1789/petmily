package com.pjt.petmily.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pjt.petmily.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chatroom")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roomId;

    @ManyToMany
    @JoinTable(
            name = "chatroom_user",
            joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonManagedReference
    private List<User> participants = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatMessage> messages = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "latest_message_id")
    @JsonManagedReference
    private ChatMessage latestMessage;

    @Column(name = "unread_message_count", nullable = false)
    private Integer unreadMessageCount = 0;

    public int getUnreadMessageCountForUser(String userEmail) {
        return (int) messages.stream()
                .filter(message -> !message.getWriter().equals(userEmail))  // 상대방이 보낸 메시지만 필터링
                .filter(message -> !message.isRead())  // 읽지 않은 메시지만 필터링
                .count();  // 그 결과의 갯수를 반환
    }

    public void resetUnreadMessageCount() {
        this.unreadMessageCount = 0;
    }

}
