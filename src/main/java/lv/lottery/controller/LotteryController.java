package lv.lottery.controller;

import lv.lottery.dto.*;
import lv.lottery.model.Lottery;
import lv.lottery.repository.LotteryRepository;
import lv.lottery.service.LotteryControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Status;
import javax.transaction.Transactional;
import java.util.*;

@RestController
public class LotteryController {

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private LotteryControllerService lotteryControllerService;

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

    @GetMapping("/stats")
    public HashMap<Integer, ResponseDTO> getStatistics() {
        return lotteryControllerService.getStatisticsResponse();
    }

    @DeleteMapping(value = "/delete-lottery/{title}")
    @Transactional
    public void deleteByTitle(@PathVariable String title){
        lotteryRepository.deleteByTitle(title);
    }
}
