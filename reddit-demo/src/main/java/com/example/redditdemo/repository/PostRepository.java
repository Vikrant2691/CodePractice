package com.example.redditdemo.repository;

import com.example.redditdemo.model.Comment;
import com.example.redditdemo.model.Post;
import com.example.redditdemo.model.Subreddit;
import com.example.redditdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
