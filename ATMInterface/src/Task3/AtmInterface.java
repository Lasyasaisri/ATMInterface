package Task3;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class User {
    private String userId;
    private String pin;

  
    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }
}

class Account {
    private String accountId;
    private double balance;
    private List<Transaction> transactions;

    public Account(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", amount));
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (balance >= amount) {
            withdraw(amount);
            recipient.deposit(amount);
            transactions.add(new Transaction("Transfer to " + recipient.getAccountId(), amount));
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }
}

class ATM {
    private User currentUser;
    private Account currentAccount;

    public void start() {
        System.out.println("Welcome to the ATM!");
        authenticateUser();
    }

    private void authenticateUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        User user = new User("lasya", "1234");
        Account account = new Account("78901234", 1000.0);

        if (userId.equals(user.getUserId()) && pin.equals(user.getPin())) {
            currentUser = user;
            currentAccount = account;
            System.out.println("Login successful. Welcome, " + currentUser.getUserId() + "!");
            displayMenu();
        } else {
            System.out.println("Authentication failed. Please try again.");
            authenticateUser();
        }
    }

    private void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Show Available Balance");
            System.out.println("6. Quit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Transaction History:");
                    List<Transaction> transactions = currentAccount.getTransactions();
                    for (Transaction transaction : transactions) {
                        System.out.println(transaction.getType() + ": " + transaction.getAmount());
                    }
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    currentAccount.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    currentAccount.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient's account ID: ");
                    String recipientAccountId = scanner.next();
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    Account recipientAccount = new Account(recipientAccountId, 0.0);
                    currentAccount.transfer(recipientAccount, transferAmount);
                    break;
                case 5:
                    System.out.println("Available Balance: " + currentAccount.getBalance());
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

public class AtmInterface {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
