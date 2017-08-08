package receiver;

import com.rabbitmq.client.*;
import messages.DeleteCacheMessage;
import messages.GenericMessage;
import messages.UpdateCacheMessage;

import java.lang.reflect.InvocationTargetException;

public class ReceiverRunner {

    private static final String HOST = "localhost";

    private final static String UPDATE_EXCHANGE_NAME = "updated_cache";
    private final static String DELETE_EXCHANGE_NAME = "delete_cache";


    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        run(HOST);
    }


    public static void run(String host) throws java.io.IOException, java.lang.InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Logger logger = new Logger();
        Storage storage = new Storage();
        // this is to make sure the queue retires polling if messages are not ack
        boolean autoAck = false;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();


        // Start all the listeners
        for (Subscription i : Subscription.values()) {
            i.startListener(channel,storage,logger);
        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    }

}