package lv.lottery.service;

import lv.lottery.model.Lottery;

import java.util.List;
import java.util.Optional;

public interface LotteryService {

    List<String> getUniqueTitles();

    void deleteById(Integer id);

    void deleteByTitle(String title);

    Optional<Lottery> getLotteryByTitle(String title);
}
