package com.bunary.vocab.mapper;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.TermResDTO;
import com.bunary.vocab.dto.request.TermReqDTO;
import com.bunary.vocab.model.Term;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TermMapper implements IMapper<Term, TermReqDTO, TermResDTO> {
    @Override
    public Term convertToEntity(TermReqDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToEntity'");
    }

    @Override
    public TermResDTO convertToResDTO(Term entity) {

        return TermResDTO.builder()
                .id(entity.getId())
                .word(entity.getWord())
                .build();
    }

}
