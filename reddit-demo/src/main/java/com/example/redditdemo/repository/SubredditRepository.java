package com.example.redditdemo.repository;

import com.example.redditdemo.model.Comment;
import com.example.redditdemo.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit,Long> {
    Optional<Subreddit> findByName(String subredditName);
}
