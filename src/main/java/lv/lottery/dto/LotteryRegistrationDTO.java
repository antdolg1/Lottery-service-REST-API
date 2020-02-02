package lv.lottery.dto;

public class LotteryRegistrationDTO {

    private Integer limit;
    private String title;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LotteryRegistrationDTO(Integer limit, String title) {
        this.limit = limit;
        this.title = title;
    }
}
