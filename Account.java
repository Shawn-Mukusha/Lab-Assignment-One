/** Common state and behaviour for all bank accounts. */
public abstract class Account {
    protected final String accountNumber;
    protected double balance;

    public Account(String accountNumber, double openingBalance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number must not be blank.");
        }
        if (!Double.isFinite(openingBalance)) {
            throw new IllegalArgumentException("Opening balance must be finite.");
        }
        this.accountNumber = accountNumber.trim();
        this.balance = openingBalance;
    }

    /** Reject invalid deposits without changing the balance. */
    public void deposit(double amount) {
        if (!isValidAmount(amount, "Deposit")) {
            return;
        }
        if (!Double.isFinite(balance + amount)) {
            System.out.println(accountNumber + ": Deposit rejected; balance would overflow.");
            return;
        }
        balance += amount;
        System.out.printf(java.util.Locale.US,
                "%s: Deposited %.2f; balance = %.2f%n", accountNumber, amount, balance);
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    /** Shared validation used by both withdrawal implementations. */
    protected boolean isValidAmount(double amount, String transaction) {
        if (!Double.isFinite(amount) || amount <= 0) {
            System.out.println(accountNumber + ": " + transaction
                    + " rejected; amount must be positive and finite.");
            return false;
        }
        return true;
    }

    public abstract void withdraw(double amount);

    public abstract void endOfMonth();
}
