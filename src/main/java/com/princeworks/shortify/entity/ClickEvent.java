package com.princeworks.shortify.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ClickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_url_id", nullable = false)
    private Long shortUrlId;

    @Column(name = "clicked_at", nullable = false)
    private Long clickedAt;

    @Column(name = "ip_address", nullable = false)
    public String ipAddress;

    @Column(name = "country")
    public String country;

    @Column(name = "device_type")
    public String deviceType;

    @Column(name = "browser")
    public String browser;

    @Column(name = "referrer")
    public String referrer;
}
