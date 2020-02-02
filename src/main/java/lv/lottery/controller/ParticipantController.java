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

//        if (dto.getAge() < 21){
//            return new Response("Fail", "Sorry, choosen by you lottery is full");
//            //to do validation
//        }

        Participant participant = new Participant();
        participant.setLotteryId(dto.getId());
        participant.setEmail(dto.getEmail());
        participant.setAge(dto.getAge());
        participant.setUniqueCode(dto.getCode());
        try {
            participantRepository.save(participant);
            return new ResponseDTO(Status.OK);
        } catch (Exception e) {
            return new ResponseDTO(Status.FAIL, e.getMessage());

        }


    }
}