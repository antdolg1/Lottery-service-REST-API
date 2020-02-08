package lv.lottery.validation;

import lv.lottery.config.Status;
import lv.lottery.controller.LotteryController;
import lv.lottery.dto.LotteryRegistrationDTO;
import lv.lottery.dto.ParticipantRegistrationDTO;
import lv.lottery.dto.ResponseDTO;
import lv.lottery.model.Lottery;
import lv.lottery.model.Participant;
import lv.lottery.repository.LotteryRepository;
import lv.lottery.repository.ParticipantRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class Validations {

    Logger logger = LoggerFactory.getLogger(LotteryController.class);

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    public ResponseDTO noSuchLotteryResponseDTO(Integer id) {
        return new ResponseDTO(Status.RESPONSE_FAIL, "No lottery with such id: " + id);
    }

    public ResponseDTO lotteryRegistrationValidation(LotteryRegistrationDTO dto) {
        if (dto.getLimit() == null) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Participant limit cannot be null");
        }
        if (dto.getLimit() < 1) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Participant limit cannot be less than 1");
        }
        if (dto.getTitle().isEmpty() || dto.getTitle().startsWith(" ") || dto.getTitle().endsWith(" ")) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Title cannot be empty or start/end with space");
        }
        if (!checkTitleDuplicate(dto.getTitle())) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Title must be unique");
        }
        return null;
    }

    private boolean checkTitleDuplicate(String title) {
        List<String> listOfUniqueTitles = lotteryRepository.getUniqueTitles();
        if (listOfUniqueTitles.contains(title)) {
            return false;
        }
        return true;
    }

    public ResponseDTO participantValidation(ParticipantRegistrationDTO dto) {
        if (dto.getAge() == null) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Participant age cannot be null");
        }
        if (dto.getAge() < 21) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Participation allowed only from 21 years");
        }
        if (!EmailValidator.getInstance().isValid(dto.getEmail())) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Please enter valid email");
        }
        if (!uniqueCodeValidation(dto.getCode(), dto.getEmail())) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Please enter valid code");
        }
        if (!checkUniqueCodeDuplicate(dto.getCode())) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "This code is already used");
        }
        return null;
    }

    private boolean uniqueCodeValidation(String code, String email) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyy");
        LocalDate now = LocalDate.now();
        String dateNow = dtf.format(now);
        String shortEmail = String.format("%02d", email.length());
        if (code.length() != 16) {
            logger.warn("Code length should be 16");
            return false;
        }
        if (!dateNow.equals(code.substring(0, 6))) {
            logger.warn("First 6 digits should be as today's date in format ddMMyy");
            return false;
        }
        if (!((email.length() < 10 && shortEmail.equals(code.substring(6, 8)))
                || email.length() >= 10 && shortEmail.equals(code.substring(7, 8)))) {
            logger.warn("7 & 8 digit should represent emails length");
            return false;
        }
        if (!(code.substring(8)).chars().allMatch(Character::isDigit)) {
            logger.warn("Last 8 digits should be numbers");
            return false;
        }
        return true;
    }

    private boolean checkUniqueCodeDuplicate(String code) {
        List<String> listOfUniqueCodes = participantRepository.getUniqueCodes();
        if (listOfUniqueCodes.contains(code)) {
            return false;
        }
        return true;
    }

    public ResponseDTO winnerStatusValidation(Participant participant, String email, Lottery lottery) {
        if (!participant.getEmail().equals(email)) {
            return new ResponseDTO(Status.RESPONSE_FAIL, "Participant email and code doesn't match");
        }
        if (lottery.getWinnerId() == null) {
            return new ResponseDTO(Status.STATUS_PENDING);
        }
        if (lottery.getWinnerId().equals(participant.getId()) && participant.getEmail().equals(email)) {
            return new ResponseDTO(Status.STATUS_WIN);
        } else {
            return new ResponseDTO(Status.STATUS_LOSE);
        }
    }



}


