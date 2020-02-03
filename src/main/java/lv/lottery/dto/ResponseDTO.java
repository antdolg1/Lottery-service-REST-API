package lv.lottery.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

        private String status;
        private Integer id;
        private String winnerCode;
        private String reason;

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

        public String getWinnerCode() {
            return winnerCode;
        }

        public void setWinnerCode(String winnerCode) {
            this.winnerCode = winnerCode;
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


        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

