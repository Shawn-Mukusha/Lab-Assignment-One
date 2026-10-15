/** A savings account preserves a minimum balance and earns monthly interest. */
public class SavingsAccount extends Account {
    private static final double MONTHLY_INTEREST_RATE = 0.02;
    private final double minimumBalance;

    public SavingsAccount(String accountNumber, double openingBalance,
                          double minimumBalance) {
        super(accountNumber, openingBalance);
        if (!Double.isFinite(minimumBalance) || minimumBalance < 0) {
            throw new IllegalArgumentException("Minimum balance must be non-negative and finite.");
        }
        if (openingBalance < minimumBalance) {
            throw new IllegalArgumentException("Opening balance must meet the minimum balance.");
        }
        this.minimumBalance = minimumBalance;
    }

    @Override
    public void withdraw(double amount) {
        if (!isValidAmount(amount, "Withdrawal")) {
            return;
        }
        if (balance - amount < minimumBalance) {
            System.out.printf(java.util.Locale.US,
                    "%s: Savings withdrawal of %.2f rejected; minimum balance = %.2f.%n",
                    accountNumber, amount, minimumBalance);
            return;
        }
        balance -= amount;
        System.out.printf(java.util.Locale.US,
                "%s: Savings withdrawal of %.2f accepted; balance = %.2f%n",
                accountNumber, amount, balance);
    }

    @Override
    public void endOfMonth() {
        double interest = balance * MONTHLY_INTEREST_RATE;
        if (!Double.isFinite(balance + interest)) {
            System.out.println(accountNumber + ": Interest rejected; balance would overflow.");
            return;
        }
        balance += interest;
        System.out.printf(java.util.Locale.US,
                "%s: Savings month end: 2%% interest = %.2f; balance = %.2f%n",
                accountNumber, interest, balance);
    }
}
