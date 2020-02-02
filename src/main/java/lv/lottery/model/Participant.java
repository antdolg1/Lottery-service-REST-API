package lv.lottery.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "participants")
public class Participant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer id;


    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "unique_code")
    private String uniqueCode;

    @ManyToOne
    @JoinColumn(name = "lottery_id")
    @JsonIgnore
    private Lottery lottery;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    public Long getLotteryId(){
        return lottery.getId();
    }

    public void setLotteryId(Long id){
        lottery.setId(id);
    }

    public Participant(String email, int age, String uniqueCode, Lottery lottery) {
        this.email = email;
        this.age = age;
        this.uniqueCode = uniqueCode;
        this.lottery = lottery;
    }

    public Participant() {
    }

}
