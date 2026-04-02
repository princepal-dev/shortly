package com.princeworks.shortify.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_code", unique = true, nullable = false)
    private String shortCode;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Url(User user, String originalUrl) {
        this.user = user;
        this.originalUrl = originalUrl;
    }

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "click_count")
    private Long clickCount = 0L;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
}
