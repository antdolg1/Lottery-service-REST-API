package lv.lottery.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

        private Enum status;
        private Long id;
        private String winnerCode;
        private String reason;

        public ResponseDTO(Enum status, Long id) {
            this.status = status;
            this.id = id;
        }

        public ResponseDTO(Enum status) {
            this.status = status;
        }

        public ResponseDTO() {
        }

        public ResponseDTO(Enum status, String reason) {
            this.status = status;
            this.reason = reason;
        }

        public ResponseDTO(Enum status, Long id, String winnerCode) {
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

        public Enum getStatus() {
            return status;
        }

        public void setStatus(Enum status) {
            this.status = status;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }


        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

