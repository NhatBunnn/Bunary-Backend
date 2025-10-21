package com.bunary.vocab.mapper;

public interface IMapper<E, ReqDTO, ResDTO> {
    E convertToEntity(ReqDTO dto);

    ResDTO convertToResDTO(E entity);
}
