package lv.lottery.service;

import java.util.Optional;

public interface ParticipantService {

    Optional<String> selectWinner(Integer id);
}
