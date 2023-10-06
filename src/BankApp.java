import java.util.Random;

public class BankApp {
    public static void main(String[] args) {
        Account account = new Account(1000.0);

        Thread depositThread = new Thread(() -> {
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                double depositAmount = random.nextDouble() * 500;
                account.deposit(depositAmount);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        depositThread.start();

        try {
            account.withdraw(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Остаток на балансе: " + account.getBalance());
    }
}
