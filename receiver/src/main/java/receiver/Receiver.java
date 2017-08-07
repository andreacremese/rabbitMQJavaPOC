package receiver;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Receiver {


    public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException {


        String exchangeName = "updated_cache";
        String[] topics = new String[2];
        Storage storage = new Storage();
        ReceiverRunner rr = new ReceiverRunner(exchangeName, topics, new Logger(), storage);
        rr.run();
    }

}