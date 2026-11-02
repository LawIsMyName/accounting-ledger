package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);
//    static
    public static void main(String[] args) {

        Transactions transaction = new Transactions(
                LocalDate.of(2026, 10, 30),
                LocalTime.of(14, 30),
                "Coffee",
                "Starbucks",
                -5.75);

        System.out.println(transaction);
    }

}

