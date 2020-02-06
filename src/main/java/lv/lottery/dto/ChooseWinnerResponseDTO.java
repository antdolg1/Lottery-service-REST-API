package lv.lottery.dto;

public class ChooseWinnerResponseDTO {
    private String status;
    private String winnerCode;

    public ChooseWinnerResponseDTO(String status, String winnerCode) {
        this.status = status;
        this.winnerCode = winnerCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWinnerCode() {
        return winnerCode;
    }

    public void setWinnerCode(String winnerCode) {
        this.winnerCode = winnerCode;
    }
}
