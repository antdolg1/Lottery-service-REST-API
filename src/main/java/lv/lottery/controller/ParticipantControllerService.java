package lv.lottery.controller;

import lv.lottery.config.Status;
import lv.lottery.dto.ParticipantRegistrationDTO;
import lv.lottery.dto.ResponseDTO;
import lv.lottery.model.Lottery;
import lv.lottery.model.Participant;
import lv.lottery.repository.ParticipantRepository;
import lv.lottery.validation.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ParticipantControllerService {

    @Autowired
    private Validations validations;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private LotteryControllerService lotteryControllerService;

    public ResponseDTO createParticipant(ParticipantRegistrationDTO dto) {
        Lottery lottery = lotteryControllerService.getLotteryById(dto.getId());
        if(lottery == null){
            return validations.noSuchLotteryResponseDTO(dto.getId());
        }
        ResponseDTO validationResponse = validations.participantValidation(dto);
        if(validationResponse == null) {
            Participant participant = new Participant();
            participant.setLottery(lottery);
            participant.setEmail(dto.getEmail());
            participant.setAge(dto.getAge());
            participant.setUniqueCode(dto.getCode());
            participantRepository.save(participant);
            return new ResponseDTO(Status.RESPONSE_OK);
        }
        return validationResponse;
    }

}
