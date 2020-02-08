package lv.lottery.controller;

import lv.lottery.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LotteryController {

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
}
