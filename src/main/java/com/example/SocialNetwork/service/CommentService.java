package com.example.SocialNetwork.service;


import com.example.SocialNetwork.dto.CommentRequest;
import com.example.SocialNetwork.dto.CommentResponse;
import com.example.SocialNetwork.exception.PostNotFoundException;
import com.example.SocialNetwork.mapper.CommentMapper;
import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.repository.CommentRepository;
import com.example.SocialNetwork.repository.PostRepository;
import com.example.SocialNetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private UserRepository userRepository;

    public void save(CommentRequest commentRequest){
        User currentUser = authService.getCurrentUser();
        Post post = postRepository.findById(commentRequest.getPostId()).orElseThrow(() -> new PostNotFoundException("Post not found by Id : " + commentRequest.getPostId()));
        commentRepository.save(commentMapper.map(commentRequest,currentUser,post));

    }

    public List<CommentResponse> getAllCommentForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post not found by Id : "+postId));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());

    }

    public List<CommentResponse> getAllCommentByUsername(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User Not Found by Username : "+userName));
        return commentRepository.findByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());

    }
}
