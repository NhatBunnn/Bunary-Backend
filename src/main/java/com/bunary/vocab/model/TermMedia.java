package com.bunary.vocab.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "term_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TermMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;

    @Column(nullable = false)
    private String mediaType;

    @Column(nullable = false, length = 500)
    private String url;

    private LocalDateTime createdAt = LocalDateTime.now();
}
