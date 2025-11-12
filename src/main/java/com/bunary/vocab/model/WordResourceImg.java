package com.bunary.vocab.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "WordResourceImgs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordResourceImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WordResource_id", nullable = false)
    private WordResource wordResource;

    @Column(nullable = false)
    private String mediaType;

    @Column(nullable = false, length = 500)
    private String url;

    private LocalDateTime createdAt = LocalDateTime.now();
}
