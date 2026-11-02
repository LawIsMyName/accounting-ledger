package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transactions> transactions = new ArrayList<>();

    public static void main(String[] args) {

        displayWelcomeBanner();

    }

    public static void displayWelcomeBanner() {
        System.out.println("==============================");
        System.out.println("       ACCOUNTING LEDGER      ");
        System.out.println("==============================");
        System.out.println("Welcome to your financial tracker!");
    }
}


