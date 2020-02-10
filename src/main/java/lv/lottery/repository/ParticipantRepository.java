package lv.lottery.repository;

import lv.lottery.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    @Query(nativeQuery = true, value = "SELECT p.id \n" +
                                        "FROM Participants p \n" +
                                        "WHERE p.lottery_id=:id \n" +
                                        "ORDER BY RANDOM() LIMIT 1")
    Integer selectWinnerId(Integer id);

    Optional<Participant> findByUniqueCode(String uniqueCode);


    @Query(nativeQuery = true, value = "SELECT DISTINCT ON (unique_code)  unique_code FROM participants")
    List<String> getUniqueCodes();

    void deleteById(Integer id);

}

