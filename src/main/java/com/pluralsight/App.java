package com.pluralsight;

import java.io.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {

        loadTransactions();
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
                case "D" -> addDeposit();
                case "P" -> makePayment();
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
                case "A" -> displayAllTransactions();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> runReportsScreen();
                case "H" -> System.out.println("Returning Home...");
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        pressEnterToContinue();
    }

    public static void displayAllTransactions() {

        System.out.println("===== ALL TRANSACTIONS =====");

        displayTransactions(transactions);
    }

    public static void displayTransactions(ArrayList<Transaction> matches) {

        if (matches.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : matches) {
                System.out.println(transaction);
            }
        }

        System.out.println("Total transactions: " + matches.size());

        pressEnterToContinue();
    }

    public static void displayDeposits() {

        System.out.println("===== DEPOSITS =====");

        ArrayList<Transaction> matches = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                matches.add(transaction);
            }
        }

        displayTransactions(matches);
    }

    public static void displayPayments() {

        System.out.println("===== PAYMENTS =====");

        ArrayList<Transaction> matches = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                matches.add(transaction);
            }
        }

        displayTransactions(matches);
    }

    public static void loadTransactions() {

        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();

            while (line != null) {

                // Skip blank lines and the CSV header.
                if (!line.isBlank() && !line.startsWith("date|")) {

                    Transaction transaction = parseTransaction(line);

                    if (transaction != null) {
                        transactions.add(transaction);
                    }
                }

                line = bufferedReader.readLine();
            }

            bufferedReader.close();

        } catch (IOException e) {
            System.out.println("Could not read transactions.csv");
        }
    }

    public static Transaction parseTransaction(String line) {

        String[] parts = line.split("\\|", -1);

        if (parts.length != 5) {
            System.out.println("Skipping invalid transaction.");
            return null;
        }

        try {
            LocalDate date = LocalDate.parse(parts[0]);
            LocalTime time = LocalTime.parse(parts[1]);
            String description = parts[2];
            String vendor = parts[3];
            double amount = Double.parseDouble(parts[4]);

            return new Transaction(date, time, description, vendor, amount);

        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("Skipping invalid transaction.");
            return null;
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
                case "1" -> displayMonthToDate();
                case "2" -> displayPreviousMonth();
                case "3" -> displayYearToDate();
                case "4" -> displayPreviousYear();
                case "5" -> searchByVendor();
                case "0" -> System.out.println("Returning to Ledger...");
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        pressEnterToContinue();

    }

    public static void displayMonthToDate() {

        LocalDate today = LocalDate.now();
        ArrayList<Transaction> matches = new ArrayList<>();

        displayReportHeader("MONTH TO DATE");

        for (Transaction transaction : transactions) {

            LocalDate date = transaction.getDate();

            if (date.getMonthValue() == today.getMonthValue() && date.getYear() == today.getYear() && !date.isAfter(today)) {

                matches.add(transaction);
            }
        }

        displayTransactions(matches);

    }

    public static void displayPreviousMonth() {

        LocalDate previousMonth = LocalDate.now().minusMonths(1);
        ArrayList<Transaction> matches = new ArrayList<>();

        displayReportHeader("PREVIOUS MONTH");

        for (Transaction transaction : transactions) {

            LocalDate date = transaction.getDate();

            if (date.getMonthValue() == previousMonth.getMonthValue() && date.getYear() == previousMonth.getYear()) {

                matches.add(transaction);
            }
        }

        displayTransactions(matches);


    }

    public static void displayYearToDate() {

        LocalDate today = LocalDate.now();
        ArrayList<Transaction> matches = new ArrayList<>();

        displayReportHeader("YEAR TO DATE");

        for (Transaction transaction : transactions) {

            LocalDate date = transaction.getDate();

            if (date.getYear() == today.getYear() && !date.isAfter(today)) {

                matches.add(transaction);
            }
        }

        displayTransactions(matches);

    }

    public static void displayPreviousYear() {

        int previousYear = LocalDate.now().getYear() - 1;
        ArrayList<Transaction> matches = new ArrayList<>();

        displayReportHeader("PREVIOUS YEAR");

        for (Transaction transaction : transactions) {

            LocalDate date = transaction.getDate();

            if (date.getYear() == previousYear) {
                matches.add(transaction);
            }
        }

        displayTransactions(matches);

    }

    public static void searchByVendor() {

        String vendor = promptForString("Enter vendor name: ");
        ArrayList<Transaction> matches = new ArrayList<>();

        displayReportHeader("SEARCH BY VENDOR");

        for (Transaction transaction : transactions) {

            if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                matches.add(transaction);
            }
        }

        displayTransactions(matches);
    }

    public static void displayReportHeader(String title) {

        System.out.println("\n===== " + title + " =====");
    }

    public static void displayWelcomeBanner() {
            System.out.println();
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║                                        ║");
            System.out.println("║               L E D G R                ║");
            System.out.println("║        Your Financial Dashboard        ║");
            System.out.println("║                                        ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║                                        ║");
            System.out.println("║   Welcome back!                        ║");
            System.out.println("║   Take control of your finances.       ║");
            System.out.println("║                                        ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println();

    }

    public static void addDeposit() {

        System.out.println("===== ADD DEPOSIT =====");

        String description = promptForString("Enter description: ");
        String vendor = promptForString("Enter vendor: ");
        double amount = promptForAmount();

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        if (saveTransaction(transaction)) {
            transactions.add(transaction);
            System.out.println("Deposit saved successfully!");
        }

        pressEnterToContinue();
    }

    public static void makePayment() {

        System.out.println("===== MAKE PAYMENT =====");

        String description = promptForString("Enter description: ");
        String vendor = promptForString("Enter vendor: ");
        double amount = promptForAmount();

        amount = -amount;

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        if (saveTransaction(transaction)) {

            transactions.add(transaction);

            System.out.println("Payment saved successfully!");
        }

        pressEnterToContinue();
    }

    public static boolean saveTransaction(Transaction transaction) {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String time = transaction.getTime().format(timeFormatter);

        String line = String.format("%s|%s|%s|%s|%.2f\n", transaction.getDate(), time, transaction.getDescription(), transaction.getVendor(), transaction.getAmount());

        try {
            FileWriter writer = new FileWriter("transactions.csv", true);

            writer.write(line);
            writer.close();

            return true;

        } catch (IOException e) {
            System.out.println("Error saving transaction.");
            return false;
        }
    }

    // ---------- Input helpers ----------

    public static String promptForString(String prompt) {

        while (true) {

            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isBlank()) {
                System.out.println("Input cannot be blank.");

            } else if (input.contains("|")) {
                System.out.println("Input cannot contain pipe symbol. Example: |");

            } else {
                return input;
            }
        }
    }

    public static double promptForDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {

                return Double.parseDouble(scanner.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number. Example: 12.50");
            }
        }
    }

    public static double promptForAmount() {

        double amount = 0;

        while (amount <= 0) {

            amount = promptForDouble("Enter amount: ");

            if (amount <= 0) {
                System.out.println("Amount must be greater than 0.");
            }
        }

        return amount;
    }

    public static void pressEnterToContinue() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }


}


