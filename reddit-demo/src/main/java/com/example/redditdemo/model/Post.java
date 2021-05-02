package com.example.redditdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long postId;
    @NotBlank(message = "Post Name cannot be empty or null")
    private String postName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
    private Instant createdDate;
    @Nullable
    @Lob
    private String description;
    @Nullable
    private String url;
    private Integer voteCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id",referencedColumnName = "id")
    private Subreddit subreddit;


}
