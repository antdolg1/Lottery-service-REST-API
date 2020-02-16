package lv.lottery.service;

import lv.lottery.config.Status;
import lv.lottery.controller.LotteryController;
import lv.lottery.dto.*;
import lv.lottery.model.Lottery;
import lv.lottery.model.Participant;
import lv.lottery.repository.LotteryRepository;
import lv.lottery.repository.ParticipantRepository;
import lv.lottery.validation.Validations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class LotteryControllerService {

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private Validations validations;

    Logger logger = LoggerFactory.getLogger(LotteryController.class);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public ResponseDTO saveLottery(LotteryRegistrationDTO dto) {
        ResponseDTO validationResponse = validations.lotteryRegistrationValidation(dto);
        if (validationResponse == null) {
            Lottery lottery = createLottery(dto);
            lotteryRepository.save(lottery);
            logger.info("Lottery successfully created.");
            return new ResponseDTO(Status.RESPONSE_OK, lottery.getId());
        }
        return validationResponse;
    }

    public Lottery createLottery(LotteryRegistrationDTO dto) {
        Lottery lottery = new Lottery();
        String startDate = simpleDateFormat.format(new Date());
        lottery.setStartDate(startDate);
        logger.info("Lottery Start date successfully set");
        lottery.setTitle(dto.getTitle());
        logger.info("Lottery Title successfully set.");
        lottery.setLimit(dto.getLimit());
        logger.info("Lottery Limit successfully set.");
        lottery.setStatus(Status.LOTTERY_OPEN);
        logger.info("Lottery status successfully set to OPEN.");
        return lottery;
    }

    public ResponseDTO stopLotteryRegistration(StopRegistrationDTO dto) {
        Lottery lotteryToModify = getLotteryById(dto.getId());

        if (lotteryToModify != null) {
            lotteryToModify.setStatus(Status.LOTTERY_CLOSED);
            logger.info("Lottery status successfully changed to CLOSED.");
            lotteryRepository.save(lotteryToModify);
            logger.info("Lottery successfully updated.");
            return new ResponseDTO(Status.RESPONSE_OK);
        }
        return validations.noSuchLotteryResponseDTO(dto.getId());
    }

    public Lottery getLotteryById(Integer id) {
        Optional<Lottery> optionalLottery = lotteryRepository.findById(id);
        if (optionalLottery.isPresent()) {
            Lottery lottery = optionalLottery.get();
            return lottery;
        }
        return null;
    }

    public ResponseDTO getWinnerResponse(ChooseWinnerDTO dto) {
        Lottery lotteryToModify = getLotteryById(dto.getId());
        if (lotteryToModify != null) {
            if (lotteryToModify.getWinnerId() != null){
                return new ResponseDTO(Status.RESPONSE_FAIL, "Winner is already selected");
            }
            if (lotteryToModify.getStatus().equals(Status.LOTTERY_OPEN)){
                return new ResponseDTO(Status.RESPONSE_FAIL, "Lottery is still open");
            }
            Integer winnerId = participantRepository.selectWinnerId(dto.getId());
            if(winnerId == null){
                return new ResponseDTO(Status.RESPONSE_FAIL, "Cannot select winner, because you have no participants in this lottery");
            }
            logger.info("Winner successfully selected.");
            Optional<Participant> participant = participantRepository.findById(winnerId);
            Participant participantToModify = participant.get();
            lotteryToModify.setWinnerId(winnerId);
            String endDate = simpleDateFormat.format(new Date());
            lotteryToModify.setEndDate(endDate);
            logger.info("Lottery End date successfully set");
            lotteryRepository.save(lotteryToModify);
            logger.info("Lottery winner id successfully added.");
            String winnerCode = participantToModify.getUniqueCode();
            return new ResponseDTO(Status.RESPONSE_OK, null, winnerCode);
        }
        return validations.noSuchLotteryResponseDTO(dto.getId());
    }

    public HashMap<Integer, ResponseDTO> getStatisticsResponse() {
        List<Lottery> listOfAllLotteries = lotteryRepository.findAll();

        HashMap<Integer, ResponseDTO> listOfAllLotteriesStats = new HashMap<>();
        Integer i = 0;
        for (Lottery lottery : listOfAllLotteries) {
            i++;
            ResponseDTO lotteryStats = new ResponseDTO();
            lotteryStats.setId(lottery.getId());
            lotteryStats.setTitle(lottery.getTitle());
            lotteryStats.setStatus(lottery.getStatus());
            lotteryStats.setStartDate(lottery.getStartDate());
            lotteryStats.setEndDate(lottery.getEndDate());
            lotteryStats.setParticipants(lottery.getParticipants().size());
            listOfAllLotteriesStats.put(i, lotteryStats);
        }
        return listOfAllLotteriesStats;
    }

}

