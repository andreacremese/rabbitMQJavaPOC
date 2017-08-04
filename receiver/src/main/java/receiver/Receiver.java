package receiver;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Receiver {


    public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException {



        ReceiverRunner rr = new ReceiverRunner(new Logger());
        rr.run();
    }

}