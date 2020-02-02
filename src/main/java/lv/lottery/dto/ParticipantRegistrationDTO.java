package lv.lottery.dto;

public class ParticipantRegistrationDTO {
    private Long id;
    private String email;
    private Byte age;
    private String code;

    public ParticipantRegistrationDTO(Long id, String email, Byte age, String code) {
        this.id = id;
        this.email = email;
        this.age = age;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}


