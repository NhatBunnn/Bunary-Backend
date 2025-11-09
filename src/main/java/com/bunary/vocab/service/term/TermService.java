package com.bunary.vocab.service.term;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.TermMediaResDTO;
import com.bunary.vocab.dto.reponse.TermResDTO;
import com.bunary.vocab.mapper.TermMapper;
import com.bunary.vocab.model.Term;
import com.bunary.vocab.repository.TermRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TermService implements ITermService {
    private final TermRepo termRepo;
    private final TermMapper termMapper;

    @Override
    public List<TermResDTO> search(String keyword) {

        List<Term> terms = this.termRepo.search(keyword);

        List<TermResDTO> termDtos = terms.stream().map((term) -> {
            TermResDTO termResDTO = this.termMapper.convertToResDTO(term);
            if (term.getMedias() != null && !term.getMedias().isEmpty()) {
                termResDTO.setTermMedias(term.getMedias().stream().map((media) -> {
                    TermMediaResDTO mediaDTO = TermMediaResDTO.builder().url(media.getUrl()).build();
                    return mediaDTO;
                }).toList());
            }

            return termResDTO;
        }).toList();

        return termDtos;
    }

}
