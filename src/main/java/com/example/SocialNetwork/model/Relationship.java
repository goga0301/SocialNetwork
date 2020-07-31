package com.example.SocialNetwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationshipId;
    @NotNull
    private Long userOneId;
    @NotNull
    private Long userTwoId;

    @NotNull
    private Integer status;

//    Status Codes:
//    Code 1 - Send Friend request
//    Code 2 - Friend request accepted
//    Code 3 - Friend request declined
//    Code 4 - Blocked

    @NotNull
    private Long actionUserId;

}
