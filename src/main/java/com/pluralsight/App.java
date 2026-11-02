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
                case "L" -> runLedgerScreen();
                case "X" -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void runLedgerScreen() {

        String command = "";

        while (!command.equalsIgnoreCase("H")) {

            System.out.println("""
                
                ===== LEDGER MENU =====
                A - All Transactions
                D - Deposits
                P - Payments
                R - Reports
                H - Home
                =======================
                """);

            System.out.print("Enter your choice: ");
            command = scanner.nextLine();

            switch (command.toUpperCase()) {
                case "A" -> System.out.println("All Transactions coming soon!");
                case "D" -> System.out.println("Deposits coming soon!");
                case "P" -> System.out.println("Payments coming soon!");
                case "R" -> runReportsScreen();
                case "H" -> System.out.println("Returning Home...");
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void runReportsScreen() {

        String command = "";

        while (!command.equals("0")) {

            System.out.println("""
                
                ===== REPORTS MENU =====
                1 - Month To Date
                2 - Previous Month
                3 - Year To Date
                4 - Previous Year
                5 - Search by Vendor
                0 - Back to Ledger
                ========================
                """);

            System.out.print("Enter your choice: ");
            command = scanner.nextLine();

            switch (command) {
                case "1" -> System.out.println("Month To Date coming soon!");
                case "2" -> System.out.println("Previous Month coming soon!");
                case "3" -> System.out.println("Year To Date coming soon!");
                case "4" -> System.out.println("Previous Year coming soon!");
                case "5" -> System.out.println("Search by Vendor coming soon!");
                case "0" -> System.out.println("Returning to Ledger...");
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


