package lv.lottery.controller;

import lv.lottery.config.Status;
import lv.lottery.dto.ParticipantRegistrationDTO;
import lv.lottery.dto.ResponseDTO;
import lv.lottery.model.Lottery;
import lv.lottery.model.Participant;
import lv.lottery.repository.LotteryRepository;
import lv.lottery.repository.ParticipantRepository;
import lv.lottery.validation.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private ParticipantControllerService participantControllerService;

    @Autowired
    private Validations validations;

    @PostMapping("/register")
    ResponseDTO register (@RequestBody ParticipantRegistrationDTO dto) {
        return participantControllerService.createParticipant(dto);
    }

}