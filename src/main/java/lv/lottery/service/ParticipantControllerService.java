package lv.lottery.service;

import lv.lottery.config.Status;
import lv.lottery.dto.ParticipantRegistrationDTO;
import lv.lottery.dto.ResponseDTO;
import lv.lottery.model.Lottery;
import lv.lottery.model.Participant;
import lv.lottery.repository.ParticipantRepository;
import lv.lottery.validation.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


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
        if (lottery == null) {
            return validations.noSuchLotteryResponseDTO(dto.getId());
        }
        if (lottery.getStatus().equals(Status.LOTTERY_CLOSED)){
            return new ResponseDTO(Status.RESPONSE_FAIL, "Lottery is already closed");
        }
        if (lottery.getParticipants().size() == lottery.getLimit()){
            return new ResponseDTO(Status.RESPONSE_FAIL, "Lottery is already full");
        }
        ResponseDTO validationResponse = validations.participantValidation(dto);
        if (validationResponse == null) {
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

    public ResponseDTO getWinnerResponseDTO(Integer id, String email, String code) {
        Lottery lottery = lotteryControllerService.getLotteryById(id);
        if (lottery == null) {
            return validations.noSuchLotteryResponseDTO(id);
        }
        Optional<Participant> participant = participantRepository.findByUniqueCode(code);
        if (participant.isEmpty()) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Wrong request data was passed");
        }
        return validations.winnerStatusValidation(participant.get(), email, lottery);
    }

    public Optional<Participant> findParticipantByUniqueCode(String uniqueCode){
        return participantRepository.findByUniqueCode(uniqueCode);
    }
}
