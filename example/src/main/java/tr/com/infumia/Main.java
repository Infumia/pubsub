package tr.com.infumia;

public final class Main {
    public static void main(final String[] args) throws InterruptedException {
        sleep();
    }

    private static void sleep() throws InterruptedException {
        while (true) {
            Thread.sleep(5L);
        }
    }
}
