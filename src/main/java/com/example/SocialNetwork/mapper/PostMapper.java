package com.example.SocialNetwork.mapper;

import com.example.SocialNetwork.dto.PostRequest;
import com.example.SocialNetwork.dto.PostResponse;
import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.repository.CommentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;

    @Mapping(target = "createdDate",expression = "java(java.time.Instant.now())")
    @Mapping(target = "description",source = "postRequest.description")
    public abstract Post map(PostRequest postRequest, User user);


    @Mapping(target = "id",source = "postId")
    @Mapping(target = "commentCount",expression = "java(commentCount(post))")
    @Mapping(target = "userName",source = "user.username")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }
}
