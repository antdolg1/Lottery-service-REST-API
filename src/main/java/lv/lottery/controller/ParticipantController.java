package lv.lottery.controller;

import lv.lottery.config.Status;
import lv.lottery.dto.ParticipantRegistrationDTO;
import lv.lottery.dto.ResponseDTO;
import lv.lottery.model.Participant;
import lv.lottery.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.validator.routines.EmailValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

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
        if (EmailValidator.getInstance().isValid(dto.getEmail()) == false) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Please enter valid email");
        }
        Participant participant = new Participant();
        participant.setLotteryId(dto.getId());
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

