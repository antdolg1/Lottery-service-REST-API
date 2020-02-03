package lv.lottery.controller;


import lv.lottery.config.Status;
import lv.lottery.dto.LotteryRegistrationDTO;
import lv.lottery.dto.ResponseDTO;
import lv.lottery.dto.StopRegistrationDTO;
import lv.lottery.model.Lottery;
import lv.lottery.repository.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static lv.lottery.config.Status.LOTTERY_CLOSED;

@RestController
public class LotteryController {

    @Autowired
    private LotteryRepository lotteryRepository;

    @GetMapping("/lotteries")
    public List<Lottery> getLotteries() {
        List<Lottery> lotteries = lotteryRepository.findAll();
        return lotteries;
    }

    @PostMapping("/start-registration")
    ResponseDTO startLottery(@Valid @RequestBody LotteryRegistrationDTO dto) {
        if (dto.getLimit() <= 1) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Participant limit cannot be less than 1");
        }
        Lottery lottery = new Lottery();
        lottery.setTitle(dto.getTitle());
        lottery.setLimit(dto.getLimit());
        lottery.setStatus(Status.LOTTERY_OPEN);
        lottery.setStartDate(LocalDateTime.now());
        try {
            lotteryRepository.save(lottery);
            return new ResponseDTO(Status.RESPONSE_OK, lottery.getId());
        } catch (Exception e) {
            return new ResponseDTO(Status.RESPONSE_FAIL, e.getMessage());
        }
    }

    @PostMapping("/stop-registration")
    ResponseDTO stopLottery(@Valid @RequestBody StopRegistrationDTO dto) throws Exception {
        Optional<Lottery> lottery = lotteryRepository.findById(dto.getId());
        Lottery lotteryToModify = lottery.get();
        lotteryToModify.setStatus(LOTTERY_CLOSED);
        lotteryToModify.setEndDate(LocalDateTime.now());
        try {
            lotteryRepository.save(lotteryToModify);
            return new ResponseDTO(Status.RESPONSE_OK, dto.getId());
        } catch (Exception e) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "No lottery with such an ID" + dto.getId());
        }

    }
}
