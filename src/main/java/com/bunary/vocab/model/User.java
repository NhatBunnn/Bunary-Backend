package com.bunary.vocab.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.bunary.vocab.learning.model.UserWordSetDaily;
import com.bunary.vocab.learning.model.UserWordSetRecent;
import com.bunary.vocab.model.enums.AuthProviderEnum;
import com.bunary.vocab.model.enums.GenderEnum;
import com.bunary.vocab.profile.model.Profile;
import com.bunary.vocab.user.model.Follow;
import com.bunary.vocab.user.model.Friendship;
import com.bunary.vocab.user.model.UserWordSetProgress;
import com.bunary.vocab.util.StringUtil;
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
import jakarta.persistence.OneToOne;
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

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    private String nickname;

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
        updateUsername();
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

    public void updateUsername() {
        this.username = (firstName != null ? StringUtil.removeVietnameseAccent(firstName) : "")
                + (lastName != null ? StringUtil.removeVietnameseAccent(lastName) : "");
        this.username = this.username.trim();
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
    private List<UserWordSetProgress> userWordSetProgresses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserWordSetRecent> userWordSetRecents;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserWordSetDaily> userWordSetDaily;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followers;

    @OneToMany(mappedBy = "followee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followees;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Profile profile;

    @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friendship> requesters;

    @OneToMany(mappedBy = "addressee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friendship> addressees;

}
