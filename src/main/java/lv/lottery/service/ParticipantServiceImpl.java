package lv.lottery.service;

import lv.lottery.repository.ParticipantRepository;

import java.util.Collection;
import java.util.Optional;

public class ParticipantServiceImpl {

    private ParticipantRepository participantRepository;

    Optional<String> selectWinner(Integer id) {
        return participantRepository.selectWinner(id);
    }

}
