package com.pjt.petmily.domain.chat.dto;

import com.pjt.petmily.domain.chat.entity.ChatMessage;
import com.pjt.petmily.domain.chat.entity.ChatRoom;
import com.pjt.petmily.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ChatHistoryDto {

    private String roomId;
    private List<ParticipantDto> participants;
    private String latestMessage;  // Here, change the type to String
    private LocalDateTime createdAt;
    private Integer unreadMessageCount;

    @Getter
    @Setter
    public static class ParticipantDto {
        private Long userId;
        private String userEmail;
        private String userNickname;
        private String userProfile;
        private String userRing;

        public static ParticipantDto fromEntity(User user) {
            ParticipantDto participantDto = new ParticipantDto();
            participantDto.setUserId(user.getUserId());
            participantDto.setUserEmail(user.getUserEmail());
            participantDto.setUserNickname(user.getUserNickname());
            participantDto.setUserProfile(user.getUserProfileImg());
            participantDto.setUserRing(String.valueOf(user.getUserRing()));
            return participantDto;
        }
    }

    public static ChatHistoryDto fromEntity(ChatRoom chatRoom) {
        ChatHistoryDto chatHistoryDto = new ChatHistoryDto();
        chatHistoryDto.setRoomId(chatRoom.getRoomId());
        chatHistoryDto.setParticipants(chatRoom.getParticipants().stream()
                .map(ParticipantDto::fromEntity)
                .collect(Collectors.toList()));
        ChatMessage latestMessage = chatRoom.getLatestMessage();
        if (latestMessage != null) {
            chatHistoryDto.setLatestMessage(latestMessage.getMessage());
            chatHistoryDto.setCreatedAt(latestMessage.getCreatedAt());  // Step 2: Set the createdAt value
        } else {
            chatHistoryDto.setLatestMessage(null);
            chatHistoryDto.setCreatedAt(null);
        }
        chatHistoryDto.setUnreadMessageCount(chatRoom.getUnreadMessageCount());
        return chatHistoryDto;
    }
}
