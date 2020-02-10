package lv.lottery;

import com.fasterxml.jackson.databind.ObjectMapper;
import lv.lottery.config.Status;
import lv.lottery.model.Lottery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LotteryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @After
    public void setUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/delete-lottery/Test_lottery"));
    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
    public void shouldCreateNewLottery() throws Exception {
        createLottery()
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"));
    }

//    @Test
//    @WithMockUser(username = "admin", password = "adminpass", roles = "ADMIN")
//    public void shouldGetLottery() throws Exception {
//        createLottery();
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/stats")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.limit").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.limit").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.participants").exists());
//    }

    public ResultActions createLottery() throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post("/start-registration")
                .content(asJsonString(new Lottery(100, Status.LOTTERY_CLOSED, "Test_lottery", null, null, null, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
