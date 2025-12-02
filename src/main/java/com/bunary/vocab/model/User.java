package com.bunary.vocab.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.bunary.vocab.model.enums.AuthProviderEnum;
import com.bunary.vocab.model.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String firstName;

    private String lastName;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Instant dateOfBirth;

    private String address;

    @Column(length = 1000)
    private String avatar;

    private int status;

    private boolean isEmailVerified = false;

    @Enumerated(EnumType.STRING)
    private AuthProviderEnum provider;

    private String providerId;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updateFullName();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
        updateFullName();
    }

    private void updateFullName() {
        this.fullName = (firstName != null ? firstName : "")
                + " "
                + (lastName != null ? lastName : "");
        this.fullName = this.fullName.trim();
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RefreshToken> refreshToken;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<WordSet> wordSet;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Collection> collection;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Setting> settings;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordSetRating> wordSetRatings;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserWordsetHistory> userWordsetHistories;

}
