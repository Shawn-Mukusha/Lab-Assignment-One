import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/** Demonstrates dynamic dispatch using only Account references in the loop. */
public class BankDemo {
    public static void main(String[] args) {
        List<Account> accounts = Arrays.asList(
                new SavingsAccount("S001", 800.00, 500.00),
                new CurrentAccount("C001", 250.00, 500.00),
                new SavingsAccount("S002", 1600.00, 500.00),
                new CurrentAccount("C002", 100.00, 300.00)
        );

        System.out.println("SHARED DEPOSIT BEHAVIOUR");
        accounts.get(0).deposit(100.00);
        accounts.get(0).deposit(0.00);

        System.out.println();
        System.out.println("POLYMORPHIC ACCOUNT PROCESSING");
        System.out.println("Each Account reference receives withdraw(600.00) and endOfMonth().");

        for (Account account : accounts) {
            System.out.println();
            System.out.printf(Locale.US, "%s (%s): starting balance = %.2f%n",
                    account.getAccountNumber(), account.getClass().getSimpleName(),
                    account.getBalance());
            // Java selects the overrides belonging to the actual object.
            // No cast or account-type condition is needed.
            account.withdraw(600.00);
            account.endOfMonth();
            System.out.printf(Locale.US, "%s: final balance = %.2f%n",
                    account.getAccountNumber(), account.getBalance());
        }
    }
}
