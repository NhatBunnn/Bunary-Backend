package com.bunary.vocab.dto.request;

import com.bunary.vocab.code.ErrorCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionReqDTO {
    @NotBlank(message = "COLLECTION_NAME_NOT_BLANK")
    @Size(max = 100, message = "Tên bộ sưu tập không được vượt quá 100 ký tự")
    private String name;
}
