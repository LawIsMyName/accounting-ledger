package com.pluralsight;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();

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
                case "A" -> System.out.println("All Transactions coming soon!");
                case "D" -> System.out.println("Deposits coming soon!");
                case "P" -> System.out.println("Payments coming soon!");
                case "R" -> runReportsScreen();
                case "H" -> System.out.println("Returning Home...");
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void loadTransactions() {

        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();

            while (line != null) {

                try {
                    String[] parts = line.split("\\|", -1);

                    if (parts.length != 5) {
                        System.out.println("Skipping invalid row: " + line);

                    } else {
                        LocalDate date = LocalDate.parse(parts[0]);
                        LocalTime time = LocalTime.parse(parts[1]);

                        String description = parts[2];
                        String vendor = parts[3];
                        double amount = Double.parseDouble(parts[4]);

                        Transaction transaction = new Transaction(
                                date, time, description, vendor, amount
                        );

                        transactions.add(transaction);
                    }

                    line = bufferedReader.readLine();
                } catch (RuntimeException e) {
                    System.out.println("Skipping invalid row: " + line);
                }
            }

            bufferedReader.close();

            System.out.println(transactions.size() + " transactions loaded.");

        } catch (IOException e) {
            System.out.println("Could not read transactions.csv");
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

    public static void addDeposit() {

        System.out.println("===== ADD DEPOSIT =====");

        String description = promptForString("Enter description: ");
        String vendor = promptForString("Enter vendor: ");

        double amount = 0;

        while (amount <= 0) {

            amount = promptForDouble("Enter amount: ");

            if (amount <= 0) {
                System.out.println("Amount must be greater than 0.");
            }
        }

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        Transaction transaction = new Transaction(
                date, time, description, vendor, amount
        );

        if (saveTransaction(transaction)) {
            System.out.println("Deposit saved successfully!");
        }
    }

    public static void makePayment() {

        System.out.println("===== MAKE PAYMENT =====");

        String description = promptForString("Enter description: ");
        String vendor = promptForString("Enter vendor: ");

        double amount = 0;

        while (amount <= 0) {

            amount = promptForDouble("Enter amount: ");

            if (amount <= 0) {
                System.out.println("Amount must be greater than 0.");
            }
        }

        amount = -amount;

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        Transaction transaction = new Transaction(
                date, time, description, vendor, amount
        );

        if (saveTransaction(transaction)) {
            System.out.println("Deposit saved successfully!");
        }
    }

    public static boolean saveTransaction(Transaction transaction) {

        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");

        String time = transaction.getTime().format(timeFormatter);

        String line = String.format(
                "%s|%s|%s|%s|%.2f\n",
                transaction.getDate(),
                time,
                transaction.getDescription(),
                transaction.getVendor(),
                transaction.getAmount()
        );

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


}


