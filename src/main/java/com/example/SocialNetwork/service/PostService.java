package com.example.SocialNetwork.service;

import com.example.SocialNetwork.dto.PostRequest;
import com.example.SocialNetwork.dto.PostResponse;
import com.example.SocialNetwork.exception.PostNotFoundException;
import com.example.SocialNetwork.mapper.PostMapper;
import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.model.User;
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
public class PostService {

    private final PostRepository postRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    public void save(PostRequest postRequest) {
        User currentUser = authService.getCurrentUser();
        postRepository.save(postMapper.map(postRequest,currentUser));
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public PostResponse getPost(Long id) {
        Post post =  postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    public List<PostResponse> getPostsByUserName(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->  new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
