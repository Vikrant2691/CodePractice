package com.example.redditdemo.repository;

import com.example.redditdemo.model.Comment;
import com.example.redditdemo.model.Post;
import com.example.redditdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPost(Post post);
    List<Comment> findByUser(User user);

}
