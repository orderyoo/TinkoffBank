import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private double balance;
    private final Lock lock = new ReentrantLock();
    private final Condition sufficientFundsCondition = lock.newCondition();

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.println("Пополнение на " + amount + ". Баланс: " + balance);
            sufficientFundsCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) throws InterruptedException {
        lock.lock();
        try {
            while (balance < amount) {
                System.out.println("Ожидание пополнения баланса для снятия...");
                sufficientFundsCondition.await();
            }
            balance -= amount;
            System.out.println("Снятие " + amount + ". Баланс: " + balance);
        } finally {
            lock.unlock();
        }
    }
}
