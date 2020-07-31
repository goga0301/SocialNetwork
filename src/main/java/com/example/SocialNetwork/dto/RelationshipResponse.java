package com.example.SocialNetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipResponse {
    private Long relationshipId;
    private String userName;
    private Integer status;
    private String lastActionUsername;
}
