package lv.lottery.controller;


import lv.lottery.config.Status;
import lv.lottery.dto.LotteryRegistrationDTO;
import lv.lottery.dto.ResponseDTO;
import lv.lottery.model.Lottery;
import lv.lottery.repository.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

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
        Lottery lottery = new Lottery();
        lottery.setTitle(dto.getTitle());
        lottery.setLimit(dto.getLimit());
        lottery.setStatus(Status.OPEN);
        lottery.setStartDate(LocalDateTime.now());
        try {
            lotteryRepository.save(lottery);
            return new ResponseDTO(Status.OK, lottery.getId());
        } catch (Exception e) {
            return new ResponseDTO(Status.FAIL, e.getMessage());
        }
    }


}
