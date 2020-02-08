package lv.lottery.controller;


import lv.lottery.config.Status;
import lv.lottery.dto.*;
import lv.lottery.model.Lottery;
import lv.lottery.model.Participant;
import lv.lottery.repository.LotteryRepository;
import lv.lottery.repository.ParticipantRepository;
import lv.lottery.validation.Validations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LotteryController {

    Logger logger = LoggerFactory.getLogger(LotteryController.class);

    @Autowired
    private LotteryControllerService lotteryControllerService;

    @Autowired
    private Validations validations;

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @PostMapping("/start-registration")
    ResponseDTO startLottery(@RequestBody LotteryRegistrationDTO dto) {
        return lotteryControllerService.saveLottery(dto);
    }

    @PostMapping("/stop-registration")
    ResponseDTO stopLottery(@RequestBody StopRegistrationDTO dto) {
        return lotteryControllerService.stopLotteryRegistration(dto);
    }

    @PostMapping("/choose-winner")
    ResponseDTO chooseWinner(@RequestBody ChooseWinnerDTO dto) {
        return lotteryControllerService.getWinnerResponse(dto);
    }

    @GetMapping("/status")
    public ResponseDTO getParticipantByUniqueCode(@RequestParam Integer id,
                                                  @RequestParam String email,
                                                  @RequestParam String code) {
        Optional<Lottery> lottery = lotteryRepository.findById(id);
        Lottery lottery1 = lottery.get();

        Optional<Participant> participant = participantRepository.findByUniqueCode(code);
        Participant participant1 = participant.get();

        if (lottery1.getWinnerId() == null) {
            return new ResponseDTO(Status.STATUS_PENDING);
        }
        if (lottery1.getWinnerId().equals(participant1.getId()) && participant1.getEmail().equals(email)) {
            return new ResponseDTO(Status.STATUS_WIN);
        } else {
            return new ResponseDTO(Status.STATUS_LOSE);
        }
    }

    @GetMapping("/stats")
    public HashMap<Integer, ResponseDTO> getStatistics() {
        List<Lottery> listOfAllLotteries = lotteryRepository.findAll();

        HashMap<Integer, ResponseDTO> listOfAllLotteriesStats = new HashMap<>();
        Integer i = 0;
        for (Lottery lottery : listOfAllLotteries) {
            i++;
            ResponseDTO lotteryStats = new ResponseDTO();
            lotteryStats.setId(lottery.getId());
            lotteryStats.setTitle(lottery.getTitle());
            lotteryStats.setStartDate(lottery.getStartDate());
            lotteryStats.setEndDate(lottery.getEndDate());
            lotteryStats.setParticipants(lottery.getParticipants().size());
            listOfAllLotteriesStats.put(i, lotteryStats);
        }
        return listOfAllLotteriesStats;
    }
}
