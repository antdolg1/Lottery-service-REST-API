package lv.lottery.controller;

import lv.lottery.dto.ParticipantRegistrationDTO;
import lv.lottery.dto.ResponseDTO;
import lv.lottery.model.Participant;
import lv.lottery.repository.ParticipantRepository;
import lv.lottery.service.ParticipantControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

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

    @GetMapping(value = "/get-participant/{uniqueCode}")
    public Optional<Participant> getParticipantByUniqueCode(@PathVariable String uniqueCode) {
        return participantControllerService.findParticipantByUniqueCode(uniqueCode);
    }

    @DeleteMapping(value = "/delete-participant/{id}")
    @Transactional
    public void deleteById(@PathVariable Integer id){
        participantRepository.deleteById(id);
    }
}