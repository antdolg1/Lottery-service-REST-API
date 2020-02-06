package lv.lottery.repository;

import lv.lottery.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    @Query(nativeQuery = true, value = "SELECT\n" +
            "p.unique_code \n" +
            "FROM Participants p \n" +
            "WHERE p.lottery_id=:id \n" +
            "ORDER BY RANDOM() LIMIT 1")
    Optional<String> selectWinner(Integer id);

}

//@Query(nativeQuery = true, value = "SELECT formatdatetime(c.date, 'MM-yyyy') as yearMonthPeriod, sum(c.volume * f.price) as totalPrice from Consumptions c, Fuel f\n" +
//        "where f.ID = c.fuel_id and " +
//        "c.Driver_ID= :id " +
//        "group by formatdatetime(c.date, 'MM-yyyy')")

//    SELECT unique_code, lottery_id FROM participants
//    WHERE lottery_id IN (105)
//    ORDER BY RANDOM() LIMIT 1