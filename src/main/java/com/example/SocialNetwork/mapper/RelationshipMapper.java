package com.example.SocialNetwork.mapper;

import com.example.SocialNetwork.dto.RelationshipRequest;
import com.example.SocialNetwork.dto.RelationshipResponse;
import com.example.SocialNetwork.model.Relationship;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RelationshipMapper {
    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "userOneId",source = "user.userId")
    @Mapping(target = "actionUserId",source = "user.userId")
    public abstract Relationship map(RelationshipRequest relationshipRequest, User user);

    @Mapping(target ="lastActionUsername", expression = "java(getUsername(relationship.getActionUserId()))")
    @Mapping(target = "userName",expression = "java(getUsername(relationship.getUserOneId()))")
    public abstract RelationshipResponse mapToDto(Relationship relationship);

    @Mapping(target ="lastActionUsername", expression = "java(getUsername(relationship.getActionUserId()))")
    @Mapping(target = "userName",expression = "java(getUsername(relationship.getUserTwoId()))")
    public abstract RelationshipResponse mapToDto1(Relationship relationship);
    String getUsername(Long id){
        return userRepository.findById(id).get().getUsername();
    }

}
