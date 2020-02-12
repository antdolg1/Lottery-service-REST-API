package lv.lottery;

import com.fasterxml.jackson.databind.ObjectMapper;
import lv.lottery.config.Status;
import lv.lottery.controller.LotteryController;
import lv.lottery.model.Lottery;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LotteryApplicationTests {

    Logger logger = LoggerFactory.getLogger(LotteryController.class);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyy");
    LocalDate now = LocalDate.now();
    String dateNow = dtf.format(now);

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    public void lotteryApplicationTest() throws Exception {
        String title = "Test_lottery";
        createLottery(title);
        ResultActions lottery = getLotteryByTitle(title);
        String lotteryJsonString = lottery.andReturn().getResponse().getContentAsString();
        JSONObject lotteryJson = new JSONObject(lotteryJsonString);
        int lotteryId = lotteryJson.getInt("id");
        logger.info("Lottery is created, registration started. Lottery id: " + lotteryId + " and title: " + title);

        String uniqueCode = dateNow + "1312345678";
        registerParticipant(lotteryId, uniqueCode);
        logger.info("Participant is added to lottery with id " + lotteryId);

        stopRegistration(lotteryId);
        logger.info("Registration has stopped for lottery with id " + lotteryId);

        ResultActions winnerResponse = chooseWinner(lotteryId);
        String winnerJsonString = winnerResponse.andReturn().getResponse().getContentAsString();
        JSONObject winnerJson = new JSONObject(winnerJsonString);
        String winnerCode = winnerJson.getString("winnerCode");
        logger.info("Winner is selected for lottery with id " + lotteryId + ". Winner code is: " + winnerCode);

        ResultActions participant = getParticipantByUniqueCode(uniqueCode);
        String participantJsonString = participant.andReturn().getResponse().getContentAsString();
        JSONObject participantJson = new JSONObject(participantJsonString);
        String participantEmail = participantJson.getString("email");
        int participantId = participantJson.getInt("id");

        ResultActions participantResult = getParticipantResult(lotteryId, participantEmail, uniqueCode);
        String jsonParticipantResult = participantResult.andReturn().getResponse().getContentAsString();
        JSONObject participantWinnerCode = new JSONObject(jsonParticipantResult);
        String winnerCodeStatus = participantWinnerCode.getString("status");
        logger.info("Participant with id " + participantId + " got this winner status " + winnerCodeStatus);

        deleteParticipant(participantId);
        logger.info("Participant with id " + participantId + " deleted");

        deleteLottery(title);
        logger.info("Lottery with title " + title + " deleted");
    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    public void shouldGetLotteriesStats() throws Exception {
        String title = "Test_lottery";
        createLottery(title);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/stats")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].startDate").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].startDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].title").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].title").isNotEmpty());
        deleteLottery(title);
    }

    public void registerParticipant(Integer lotteryId, String uniqueCode) throws Exception {
        JSONObject json = new JSONObject();
        json.put("id", lotteryId);
        json.put("email", "test@test.com");
        json.put("age", 25);
        json.put("code", uniqueCode);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .content(String.valueOf(json))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"));
    }

    public void stopRegistration(Integer lotteryId) throws Exception {
        JSONObject json = new JSONObject();
        json.put("id", lotteryId);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/stop-registration")
                .content(String.valueOf(json))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"));
    }

    public ResultActions chooseWinner(Integer lotteryId) throws Exception {
        JSONObject json = new JSONObject();
        json.put("id", lotteryId);
        return mockMvc.perform(MockMvcRequestBuilders
                .post("/choose-winner")
                .content(String.valueOf(json))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.winnerCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.winnerCode").isNotEmpty());
    }

    public ResultActions getParticipantResult(Integer lotteryId, String email, String code) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get("/status?id={lotteryId}&email={email}&code={code}", lotteryId, email, code)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").isNotEmpty());
    }

    public ResultActions createLottery(String title) throws Exception {
        String startDate = simpleDateFormat.format(new Date());
        return mockMvc.perform(MockMvcRequestBuilders
                .post("/start-registration")
                .content(asJsonString(new Lottery(100, Status.LOTTERY_OPEN, title, startDate, null, null, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"));
    }

    public ResultActions getLotteryByTitle(String title) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get("/get-lottery/" + title)
                .accept(MediaType.APPLICATION_JSON));
    }

    public ResultActions getParticipantByUniqueCode(String uniqueCode) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get("/get/" + uniqueCode)
                .accept(MediaType.APPLICATION_JSON));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteParticipant(Integer id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/delete-participant/{id}", id));

    }

    public void deleteLottery(String title) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/delete-lottery/{title}", title));
    }

}
