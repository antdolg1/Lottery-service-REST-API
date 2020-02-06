package lv.lottery.controller;


import lv.lottery.config.Status;
import lv.lottery.dto.*;
import lv.lottery.model.Lottery;
import lv.lottery.repository.LotteryRepository;
import lv.lottery.repository.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static lv.lottery.config.Status.LOTTERY_CLOSED;

@RestController
public class LotteryController {

    Logger logger = LoggerFactory.getLogger(LotteryController.class);

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @GetMapping("/lotteries")
    public List<Lottery> getLotteries() {
        return lotteryRepository.findAll();
    }

    @PostMapping("/start-registration")
    ResponseDTO startLottery(@Valid @RequestBody LotteryRegistrationDTO dto) {
        if (dto.getLimit() <= 1) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Participant limit cannot be less than 1");
        }
        Lottery lottery = new Lottery();
        String pattern = "dd.MM.yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String startDate = simpleDateFormat.format(new Date());
        lottery.setStartDate(startDate);
        logger.info("Lottery Start date successfully set");
        lottery.setTitle(dto.getTitle());
        logger.info("Lottery Title successfully set.");
        lottery.setLimit(dto.getLimit());
        logger.info("Lottery Limit successfully set.");
        lottery.setStatus(Status.LOTTERY_OPEN);
        logger.info("Lottery status successfully set to OPEN.");

        try {
            lotteryRepository.save(lottery);
            logger.info("Lottery successfully created.");
            return new ResponseDTO(Status.RESPONSE_OK, lottery.getId());
        } catch (Exception e) {
            return new ResponseDTO(Status.RESPONSE_FAIL, e.getMessage());
        }
    }

    @PutMapping("/stop-registration")
    ResponseDTO stopLottery(@Valid @RequestBody StopRegistrationDTO dto) {
        Optional<Lottery> lottery = lotteryRepository.findById(dto.getId());
        Lottery lotteryToModify = lottery.get();
        lotteryToModify.setStatus(LOTTERY_CLOSED);
        logger.info("Lottery status successfully changed to CLOSED.");
        String pattern = "dd.MM.yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String endDate = simpleDateFormat.format(new Date());
        lotteryToModify.setEndDate(endDate);
        logger.info("Lottery end date successfully set.");
        try {
            lotteryRepository.save(lotteryToModify);
            logger.info("Lottery successfully updated.");
            return new ResponseDTO(Status.RESPONSE_OK);
        } catch (NoSuchElementException e) {
            return new ResponseDTO(Status.RESPONSE_FAIL, e.getMessage());
        }
    }

    @PostMapping("/choose-winner")
    ChooseWinnerResponseDTO chooseWinner(@Valid @RequestBody ChooseWinnerDTO dto) {
        String winner =  participantRepository.selectWinner(dto.getId()).get();
        logger.info("Winner is successfully selected.");
        return new ChooseWinnerResponseDTO(Status.RESPONSE_OK, winner);
    }
}
