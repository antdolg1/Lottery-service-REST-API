package lv.lottery.controller;

import lv.lottery.config.Status;
import lv.lottery.dto.ParticipantRegistrationDTO;
import lv.lottery.dto.ResponseDTO;
import lv.lottery.model.Lottery;
import lv.lottery.model.Participant;
import lv.lottery.repository.LotteryRepository;
import lv.lottery.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.validator.routines.EmailValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private LotteryRepository lotteryRepository;

    @GetMapping("/participants")
    public List<Participant> getParticipants() {
        List<Participant> participants = participantRepository.findAll();
        return participants;
    }

    @PostMapping("/register")
    ResponseDTO register(@Valid @RequestBody ParticipantRegistrationDTO dto) {
        if (dto.getAge() < 21) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Participation allowed only from 21 years");
        }
        if (!EmailValidator.getInstance().isValid(dto.getEmail())) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Please enter valid email");
        }
        Participant participant = new Participant();
        Optional<Lottery> lottery = lotteryRepository.findById(dto.getId());
        if (!lottery.isPresent()) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Lottery with such an id doesn't exist");
        }
        participant.setLottery(lottery.get());
        participant.setEmail(dto.getEmail());
        participant.setAge(dto.getAge());
        participant.setUniqueCode(dto.getCode());
        try {
            participantRepository.save(participant);
            return new ResponseDTO(Status.RESPONSE_OK);
        } catch (Exception e) {
            return new ResponseDTO(Status.RESPONSE_FAIL, e.getMessage());

        }


    }
}

