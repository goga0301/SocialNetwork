package com.example.SocialNetwork.mapper;


import com.example.SocialNetwork.dto.CommentRequest;
import com.example.SocialNetwork.dto.CommentResponse;
import com.example.SocialNetwork.model.Comment;
import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {


    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post",source = "post")
    public abstract Comment map(CommentRequest commentRequest, User user, Post post);


    @Mapping(target = "userName",source = "user.username")
    @Mapping(target = "postName",source = "post.postName")
    @Mapping(target = "postId",source = "post.postId")
    public abstract CommentResponse mapToDto(Comment comment);

}
