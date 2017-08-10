package receiver;

import com.rabbitmq.client.*;
import messages.DeleteCacheMessage;
import messages.UpdateCacheMessage;
import receiver.consumers.Subscription;
import receiver.controllers.DeleteMessageController;
import receiver.controllers.UpdateMessageController;
import receiver.services.Logger;
import receiver.services.Storage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ReceiverBootstrapper {


    private final static String UPDATE_EXCHANGE_NAME = "updated_cache";
    private final static String DELETE_EXCHANGE_NAME = "delete_cache";

    private final static String HOST = "127.0.0.1";
    private final static int PORT = 11211;


    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        run(HOST);
    }


    public static void run(String host) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Logger logger = new Logger();

        Storage storage = new Storage(HOST, PORT);

        // this is to make sure the queue retires polling if messages are not ack
        boolean autoAck = false;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();



        // new up controller and injectin into localConsumer instead?


        // this is where the queue => messagetype => controllers are bound.
        new Subscription<UpdateMessageController>(UPDATE_EXCHANGE_NAME, new UpdateMessageController(storage)).startListener(channel, logger);
        new Subscription<DeleteMessageController>(DELETE_EXCHANGE_NAME, new DeleteMessageController(storage)).startListener(channel, logger);



        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    }

}