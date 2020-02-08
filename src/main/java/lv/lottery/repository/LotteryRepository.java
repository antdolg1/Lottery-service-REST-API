package lv.lottery.repository;

import lv.lottery.model.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LotteryRepository extends JpaRepository<Lottery, Integer> {

    @Query(nativeQuery = true, value = "SELECT DISTINCT ON (title)  title FROM lottery")
    List<String> getUniqueTitles();
}
