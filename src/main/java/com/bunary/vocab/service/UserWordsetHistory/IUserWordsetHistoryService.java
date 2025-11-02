package com.bunary.vocab.service.UserWordsetHistory;

import com.bunary.vocab.dto.reponse.UserWordsetHistoryResDTO;

public interface IUserWordsetHistoryService {
    UserWordsetHistoryResDTO recordWordSetStudy(Long wordSetId);
}
