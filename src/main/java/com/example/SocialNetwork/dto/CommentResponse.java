package com.example.SocialNetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String userName;
    private String postName;
    private String postId;
    private String text;
}
