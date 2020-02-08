package lv.lottery.controller;

import lv.lottery.dto.ParticipantRegistrationDTO;
import lv.lottery.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParticipantController {

    @Autowired
    private ParticipantControllerService participantControllerService;

    @PostMapping("/register")
    ResponseDTO register (@RequestBody ParticipantRegistrationDTO dto) {
        return participantControllerService.createParticipant(dto);
    }

    @GetMapping("/status")
    public ResponseDTO getParticipantByUniqueCode(@RequestParam Integer id,
                                                  @RequestParam String email,
                                                  @RequestParam String code) {
    return participantControllerService.getWinnerResponseDTO(id, email, code);
    }

}