package com.example.SocialNetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String postName;
    private String description;
    private String userName;
    private Integer likeCount;
    private Integer commentCount;

}
