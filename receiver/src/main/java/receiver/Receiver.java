package receiver;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Receiver {


    public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException {


        String exchangeName = "xc";
        String[] topics = new String[2];
        topics[0] = "*.red.*";
        topics[1] = "*.amber.*";
        ReceiverRunner rr = new ReceiverRunner(exchangeName, topics, new Logger());
        rr.run();
    }

}