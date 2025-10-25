package com.bunary.vocab.service.wordSetStat;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.mapper.WordSetMapper;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.model.enums.VisibilityEnum;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.service.specification.WordSetStatSpec;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("wordSetStatService")
public class WordSetStatService implements IWordSetStatService {
    private final WordSetRepository wordSetRepository;
    private final WordSetMapper wordSetMapper;
    private final UserMapper userMapper;

    @Override
    public Page<WordSetReponseDTO> findAll(Map<String, String> params, Pageable pageable) {

        Specification<WordSet> spec = WordSetStatSpec.buildDynamicFilter(params);

        Page<WordSet> page = wordSetRepository.findAll(spec, pageable);

        List<WordSetReponseDTO> dtoList = page.getContent().stream()
                .map(ws -> {
                    WordSetReponseDTO wsDTO = this.wordSetMapper.convertToWordSetReponseDTO(ws);

                    if (Hibernate.isInitialized(ws.getUser())) {
                        wsDTO.setAuthor(this.userMapper.convertToUserResponseDTO(ws.getUser()));
                    }

                    return wsDTO;
                }).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    public Page<WordSetReponseDTO> findAllWordSets(Map<String, String> params, Pageable pageable) {

        return null;
    }

}
