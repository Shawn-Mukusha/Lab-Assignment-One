/** A current account permits an overdraft and charges a monthly fee. */
public class CurrentAccount extends Account {
    private static final double MONTHLY_MAINTENANCE_FEE = 10.00;
    private final double overdraftLimit;

    public CurrentAccount(String accountNumber, double openingBalance,
                          double overdraftLimit) {
        super(accountNumber, openingBalance);
        if (!Double.isFinite(overdraftLimit) || overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit must be non-negative and finite.");
        }
        if (openingBalance < -overdraftLimit) {
            throw new IllegalArgumentException("Opening balance exceeds the overdraft limit.");
        }
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (!isValidAmount(amount, "Withdrawal")) {
            return;
        }
        if (balance - amount < -overdraftLimit) {
            System.out.printf(java.util.Locale.US,
                    "%s: Current withdrawal of %.2f rejected; overdraft limit = %.2f.%n",
                    accountNumber, amount, overdraftLimit);
            return;
        }
        balance -= amount;
        System.out.printf(java.util.Locale.US,
                "%s: Current withdrawal of %.2f accepted; balance = %.2f%n",
                accountNumber, amount, balance);
    }

    @Override
    public void endOfMonth() {
        // The brief requires a fixed fee each month, including when overdrawn.
        balance -= MONTHLY_MAINTENANCE_FEE;
        System.out.printf(java.util.Locale.US,
                "%s: Current month end: maintenance fee = %.2f; balance = %.2f%n",
                accountNumber, MONTHLY_MAINTENANCE_FEE, balance);
    }
}
