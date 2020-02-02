package lv.lottery.repository;

import lv.lottery.model.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotteryRepository extends JpaRepository<Lottery, Integer> {
}
