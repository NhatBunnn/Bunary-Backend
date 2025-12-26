package com.bunary.vocab.post.dto.response;

import com.bunary.vocab.post.model.enums.MediaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaResDTO {
    private MediaType mediaType;

    private String url;
}
