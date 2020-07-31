package com.example.SocialNetwork.service;

import com.example.SocialNetwork.dto.RelationshipRequest;
import com.example.SocialNetwork.dto.RelationshipResponse;
import com.example.SocialNetwork.mapper.RelationshipMapper;
import com.example.SocialNetwork.model.Relationship;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.repository.RelationshipRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final RelationshipMapper relationshipMapper;
    private final AuthService authService;

    public void sendRequest(RelationshipRequest relationshipRequest) {
       User currentUser = authService.getCurrentUser();
       Relationship relationship = relationshipMapper.map(relationshipRequest,currentUser);
       relationshipRepository.save(relationship);

    }

    public void acceptRequest(Long userId) {
        User user = authService.getCurrentUser();
        Relationship relationship = relationshipRepository.findByUserOneIdAndUserTwoId(userId,user.getUserId());
        if(relationship.getStatus()==1){
            relationship.setActionUserId(user.getUserId());
            relationship.setStatus(2);
            relationshipRepository.save(relationship);
        }

    }
    public void delete(Long userId) {
        User user = authService.getCurrentUser();
        Relationship relationship = relationshipRepository.findByUserOneIdAndUserTwoId(userId,user.getUserId());

        if(relationship==null){
            relationship = relationshipRepository.findByUserTwoIdAndUserOneId(user.getUserId(),userId);

            relationshipRepository.delete(relationship);

        }else {

            relationshipRepository.delete(relationship);
        }


    }
    public void block(Long userId) {
        User user = authService.getCurrentUser();
        Relationship relationship = relationshipRepository.findByUserOneIdAndUserTwoId(user.getUserId(),userId);

        if(relationship==null){
            relationship = relationshipRepository.findByUserTwoIdAndUserOneId(user.getUserId(),userId);
            if(relationship==null) {
                relationship = relationshipMapper.map(new RelationshipRequest(userId, 4), user);
            }
            relationshipRepository.save(relationship);

        }else {
            relationship.setActionUserId(user.getUserId());
            relationship.setStatus(4);
            relationshipRepository.save(relationship);
        }

    }


    public List<RelationshipResponse> getAllFriend() {
        User currentUser = authService.getCurrentUser();

        List<Relationship> userOneRel = relationshipRepository.findAllByUserOneIdAndStatus(currentUser.getUserId(),2);
        List<RelationshipResponse> userOneRelRes = userOneRel.stream().map(relationshipMapper::mapToDto1).collect(toList());
        List<Relationship> userTwoRel = relationshipRepository.findAllByUserTwoIdAndStatus(currentUser.getUserId(),2);
        List<RelationshipResponse> userTwoRelRes = userTwoRel.stream().map(relationshipMapper::mapToDto).collect(toList());

        List<RelationshipResponse> relationship = new ArrayList<>();
        relationship.addAll(userOneRelRes);
        relationship.addAll(userTwoRelRes);
        return  relationship;
    }

    public List<RelationshipResponse> getAllFriendRequest() {
        User currentUser = authService.getCurrentUser();

        return relationshipRepository.findAllByUserTwoIdAndStatus(currentUser.getUserId(),1)
                .stream()
                .map(relationshipMapper::mapToDto)
                .collect(toList());
    }
    public List<RelationshipResponse> getMyActions(Integer status){
        User currentUser = authService.getCurrentUser();

        return relationshipRepository.findAllByActionUserIdAndStatus(currentUser.getUserId(),status)
                .stream()
                .map(relationshipMapper::mapToDto1)
                .collect(toList());
    }


    public List<RelationshipResponse> getAllRelationshipsByStatus(Integer status){
        User currentUser = authService.getCurrentUser();

        List<Relationship> userOneRel = relationshipRepository.findAllByUserOneIdAndStatusAndActionUserIdNot(currentUser.getUserId(),status,currentUser.getUserId());
        List<RelationshipResponse> userOneRelRes = userOneRel.stream().map(relationshipMapper::mapToDto1).collect(toList());
        List<Relationship> userTwoRel = relationshipRepository.findAllByUserTwoIdAndStatusAndActionUserIdNot(currentUser.getUserId(),status,currentUser.getUserId());
        List<RelationshipResponse> userTwoRelRes = userTwoRel.stream().map(relationshipMapper::mapToDto).collect(toList());

        List<RelationshipResponse> relationship = new ArrayList<>();
        relationship.addAll(userOneRelRes);
        relationship.addAll(userTwoRelRes);
        return  relationship;
    }



}
