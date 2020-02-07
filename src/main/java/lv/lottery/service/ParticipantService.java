package lv.lottery.service;

import lv.lottery.model.Participant;

import java.util.Optional;

public interface ParticipantService {

    Integer selectWinnerId(Integer id);

    Optional<Participant> findByUniqueCode(String uniqueCode);
}
