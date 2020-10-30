package project.queue;

//import com.google.gson.Gson;

import java.util.concurrent.ThreadLocalRandom;

public class SimulatedQueue implements Queue<String> {

//    private final Gson gson;

    public SimulatedQueue() {
    	super();
//        this.gson = new Gson();
    }

    public String poll(int timeout) throws PollingException {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
        } catch (InterruptedException e) {
            // Ignore. No Siesta today
        }
        int randomNumber = ThreadLocalRandom.current().nextInt(100) % 5;
        switch (randomNumber) {
            case 0 : {
                throw new PollingException("Polling error. At lunch, please try again later");
            }
            case 1 : {
                // Message message = new YourMessageType();
                // return gson.toJson(message);
            }
            case 2: {
                throw new RuntimeException("Encountered an unexpected error. Probably Mas Hachnasa got me this time.");
            }
            case 3: {
                return "Coz I can " + timeout;
            }
        }
        return null;
    }
}
