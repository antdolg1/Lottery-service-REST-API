package lv.lottery.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    private String status;
    private Integer id;
    private String winnerCode;
    private String reason;
    private String title;
    private String startDate;
    private String endDate;
    private Integer participants;

    public ResponseDTO(String status, Integer id) {
        this.status = status;
        this.id = id;
    }

    public ResponseDTO(String status) {
        this.status = status;
    }

    public ResponseDTO() {
    }

    public ResponseDTO(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public ResponseDTO(String status, Integer id, String winnerCode) {
        this.status = status;
        this.id = id;
        this.winnerCode = winnerCode;
    }

    public ResponseDTO(Integer id, String title, String startDate, String endDate, Integer participants) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participants = participants;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWinnerCode() {
        return winnerCode;
    }

    public void setWinnerCode(String winnerCode) {
        this.winnerCode = winnerCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }
}

