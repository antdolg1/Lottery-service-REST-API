package lv.lottery.validation;

import lv.lottery.config.Status;
import lv.lottery.dto.LotteryRegistrationDTO;
import lv.lottery.dto.ResponseDTO;
import lv.lottery.repository.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Validations {

    @Autowired
    private LotteryRepository lotteryRepository;

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
}


