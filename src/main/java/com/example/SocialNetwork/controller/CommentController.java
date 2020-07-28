package com.example.SocialNetwork.controller;

import com.example.SocialNetwork.dto.CommentRequest;
import com.example.SocialNetwork.dto.CommentResponse;
import com.example.SocialNetwork.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentRequest commentRequest){
        commentService.save(commentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentForPost(@PathVariable Long postId){
        return status(HttpStatus.OK).body(commentService.getAllCommentForPost(postId));
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentResponse>> getAllCommentByUsername(@PathVariable String userName){
        return status(HttpStatus.OK).body(commentService.getAllCommentByUsername(userName));
    }

}
