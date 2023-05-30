package com.example.socialMedia.dto.friendRequest;

import lombok.Data;

@Data
public class FriendRequestDTO {
    private Long senderId;
    private Long receiverId;
}
