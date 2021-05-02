package com.example.redditdemo.repository;

import com.example.redditdemo.model.Post;
import com.example.redditdemo.model.User;
import com.example.redditdemo.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}