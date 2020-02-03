package lv.lottery.dto;

public class ParticipantRegistrationDTO {
    private Integer id;
    private String email;
    private Byte age;
    private String code;

    public ParticipantRegistrationDTO(Integer id, String email, Byte age, String code) {
        this.id = id;
        this.email = email;
        this.age = age;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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


