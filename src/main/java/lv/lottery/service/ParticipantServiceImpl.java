package lv.lottery.service;

import lv.lottery.model.Participant;
import lv.lottery.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantServiceImpl {

    @Autowired
    private ParticipantRepository participantRepository;

    Integer selectWinnerId(Integer id) {
        return participantRepository.selectWinnerId(id);
    }

    Optional<Participant> findByUniqueCode(String uniqueCode) {
        return participantRepository.findByUniqueCode(uniqueCode);
    }

    List<String> getUniqueCodes(){
        return participantRepository.getUniqueCodes();
    }

}
