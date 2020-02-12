package lv.lottery.service;

import lv.lottery.model.Lottery;
import lv.lottery.repository.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LotteryServiceImpl {

    @Autowired
    private LotteryRepository lotteryRepository;

    List<String> getUniqueTitles() {
        return lotteryRepository.getUniqueTitles();
    }

    void deleteById(Integer id) {
        lotteryRepository.deleteById(id);
    }

    void deleteByTitle(String title) {
      lotteryRepository.deleteByTitle(title);
    }

    Optional<Lottery> getLotteryByTitle(String title) {
        return lotteryRepository.getLotteryByTitle(title);
    }
}
