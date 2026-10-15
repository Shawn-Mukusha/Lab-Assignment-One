# Practical Assignment OOP

## Overview

This Java program models savings and current bank accounts. Both account types share deposit and balance operations but implement different withdrawal rules and month-end processing. The program demonstrates abstraction, inheritance, method overriding and runtime polymorphism.

## Submission files

| File | Purpose |
| --- | --- |
| `Account.java` | Abstract superclass containing shared account state and operations. |
| `SavingsAccount.java` | Savings account implementation with a minimum balance and monthly interest. |
| `CurrentAccount.java` | Current account implementation with an overdraft limit and monthly maintenance fee. |
| `BankDemo.java` | Entry point that demonstrates both account types through a collection of `Account` references. |
| `SampleOutput.txt` | Console output captured from running `BankDemo`. |

## Compilation and execution

A Java Development Kit (JDK) version 8 or later is required. The program uses only the Java standard library.

Open a terminal in the folder containing the Java files and compile them:

```powershell
javac Account.java SavingsAccount.java CurrentAccount.java BankDemo.java
```

Run the demonstration:

```powershell
java BankDemo
```

Compilation creates `.class` files in the same folder. The program runs without interactive input and prints its results to the console.

## Class design and account rules

### Account

`Account` declares the protected fields `accountNumber` and `balance`, which are initialised by its constructor. Its concrete `deposit(double amount)` method increases the balance only when the amount is positive and finite. The concrete `getBalance()` method returns the current balance.

The abstract methods `withdraw(double amount)` and `endOfMonth()` define the operations that each subclass must implement. An `Account` cannot be instantiated directly.

### SavingsAccount

`SavingsAccount` extends `Account` and stores a private, final `minimumBalance`. A withdrawal is accepted when `balance - amount >= minimumBalance`. A withdrawal that would leave less than the minimum prints a rejection message and leaves the balance unchanged.

At month end, the account adds interest calculated as `balance * 0.02`. The fixed monthly interest rate is 2%.

### CurrentAccount

`CurrentAccount` extends `Account` and stores a private, final `overdraftLimit`. A withdrawal is accepted when `balance - amount >= -overdraftLimit`, allowing the balance to become negative within the limit. A withdrawal beyond this limit prints a rejection message and leaves the balance unchanged.

At month end, the account deducts a fixed maintenance fee of 10.00. This fee is charged even when the account is overdrawn; the overdraft check applies to withdrawal requests.

Both subclasses reject non-positive and non-finite withdrawal amounts. Constructors reject blank account numbers, non-finite balances and account settings that violate the applicable opening balance limits by throwing `IllegalArgumentException`.

The interest rate, maintenance fee and demonstration account values are chosen for this implementation because the assignment does not specify numerical values. Balances use `double` as required and are displayed to two decimal places.

## Polymorphism demonstration

`BankDemo` creates a `List<Account>` containing two `SavingsAccount` objects and two `CurrentAccount` objects. Before processing the list, it deposits 100.00 into S001 and demonstrates the rejection of a zero deposit.

The loop processes each object through an `Account` reference and calls:

```java
account.withdraw(600.00);
account.endOfMonth();
```

Java selects the overridden method belonging to the object's actual class at runtime. The loop uses no subclass casts or account-type conditions. Consequently, the same method calls enforce the savings minimum or current account overdraft limit and then apply either interest or a maintenance fee.

## Expected results

Each account receives a withdrawal request of 600.00. Month-end processing follows immediately, including when the withdrawal is rejected.

| Account | Balance before withdrawal | Withdrawal outcome | Month-end adjustment | Final balance |
| --- | ---: | --- | ---: | ---: |
| S001 | 900.00 | Rejected: would leave 300.00 below the 500.00 minimum | +18.00 interest | 918.00 |
| C001 | 250.00 | Accepted: leaves -350.00 within the 500.00 overdraft limit | -10.00 fee | -360.00 |
| S002 | 1600.00 | Accepted: leaves 1000.00 above the 500.00 minimum | +20.00 interest | 1020.00 |
| C002 | 100.00 | Rejected: would leave -500.00 beyond the 300.00 overdraft limit | -10.00 fee | 90.00 |

S001 demonstrates the required rejected savings withdrawal. C001 demonstrates the required permitted overdraft. S002 and C002 additionally demonstrate an accepted savings withdrawal and a rejected current account withdrawal. The complete execution output is provided in `SampleOutput.txt`.
