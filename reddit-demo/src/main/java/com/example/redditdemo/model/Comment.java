package com.example.redditdemo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    private String text;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId",referencedColumnName = "postId")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
}
