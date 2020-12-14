package myApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        System.out.println("program start...");
        SpringApplication.run(App.class, args);

        Thread eventsProducerThread = new Thread(new Generator());
        eventsProducerThread.start();
    }
}
