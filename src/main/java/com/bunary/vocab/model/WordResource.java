package com.bunary.vocab.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "WordResources")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String word;

    @Column(columnDefinition = "TEXT")
    private String meaning;

    @Column(name = "thumbnail", length = 1000)
    private String thumbnail;

    private String term;

    private String partOfSpeech;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "wordResource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordResourceImg> wordResourceImgs;
}
