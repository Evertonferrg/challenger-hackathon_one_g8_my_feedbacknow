package com.fedbacknow.fedbacknow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "social_feedback", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"platform", "external_id"})
})
@Getter
@Setter
public class SocialFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String platform;
    private String externalId;
    private String authorName;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String sentiment;

    private LocalDateTime createdAt;
    private LocalDateTime receivedAt;
}
