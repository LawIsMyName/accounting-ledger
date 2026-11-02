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
        runHomeScreen();

    }

    public static void runHomeScreen() {

        String command = "";

        while (!command.equalsIgnoreCase("X")) {

            System.out.println("""
                
                ===== HOME MENU =====
                D - Add Deposit
                P - Make Payment
                L - Ledger
                X - Exit
                =====================
                """);

            System.out.print("Enter your choice: ");
            command = scanner.nextLine();

            switch (command.toUpperCase()) {
                case "D" -> System.out.println("Add Deposit coming soon!");
                case "P" -> System.out.println("Make Payment coming soon!");
                case "L" -> System.out.println("Ledger coming soon!");
                case "X" -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }



    public static void displayWelcomeBanner() {
        System.out.println("==============================");
        System.out.println("       ACCOUNTING LEDGER      ");
        System.out.println("==============================");
        System.out.println("Welcome to your financial tracker!");
    }
}


