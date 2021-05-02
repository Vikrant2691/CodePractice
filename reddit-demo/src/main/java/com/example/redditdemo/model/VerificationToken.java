package com.example.redditdemo.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@AllArgsConstructor
@Table(name = "token")
@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private Instant expiryDate;

}
