package lv.lottery.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UniqueCodeValidator {
    private static String uniqueCode = "0402200812345662";
    private static String email = "1@gl.com";


    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyy");
        LocalDate now = LocalDate.now();
        String dateNow = dtf.format(now);
        String shortEmail = String.format("%02d" , email.length());

        if (uniqueCode.length() != 16) {
            System.out.println("Code length should be 16");
        }

        if (dateNow.equals(uniqueCode.substring(0, 6))){
            System.out.println("First 6 numbers verified");
        } else {
            System.out.println("Unique code not valid");
        }

        if ((email.length() < 10 && shortEmail.equals(uniqueCode.substring(6 , 8)))
                || email.length() >= 10 && shortEmail.equals(uniqueCode.substring(7 , 8)) ) {
            System.out.println("Numbers 7 and 8 verified");
        } else {
            System.out.println("Unique code not valid");
        }

        if (!(uniqueCode.substring(8)).chars().allMatch(Character::isDigit)) {
            System.out.println("Last 8 values should be numbers");
        } else {
            System.out.println("Number 9-16 verified");
        }

    }

}
