package lv.lottery.service;

import lv.lottery.repository.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryServiceImpl {

    @Autowired
    private LotteryRepository lotteryRepository;

    List<String> getUniqueTitles(){
        return lotteryRepository.getUniqueTitles();
    }
}
