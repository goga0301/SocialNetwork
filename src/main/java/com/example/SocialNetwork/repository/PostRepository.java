package com.example.SocialNetwork.repository;

import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUser(User user);
}
