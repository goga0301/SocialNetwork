package com.example.SocialNetwork.repository;

import com.example.SocialNetwork.model.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    Relationship findByUserOneIdAndUserTwoId(Long userOneId,Long userTwoId);

    Relationship findByUserTwoIdAndUserOneId(Long userId, Long userId1);

    List<Relationship> findAllByUserTwoIdAndStatusAndActionUserIdNot(Long userTwoId, Integer status,Long actionUserId);

    List<Relationship> findAllByUserOneIdAndStatusAndActionUserIdNot(Long userTwoId, Integer status,Long actionUserId);

    List<Relationship> findAllByActionUserIdAndStatus(Long userId,Integer id);

    List<Relationship> findAllByUserTwoIdAndStatus(Long userId, Integer status);

    List<Relationship> findAllByUserOneIdAndStatus(Long userId, Integer status);


}
