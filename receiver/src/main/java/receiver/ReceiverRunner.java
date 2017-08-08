package receiver;

import com.rabbitmq.client.*;
import messages.DeleteCacheMessage;
import messages.GenericMessage;
import messages.UpdateCacheMessage;

public class ReceiverRunner {

    private static final String HOST = "localhost";

    private final static String UPDATE_EXCHANGE_NAME = "updated_cache";
    private final static String DELETE_EXCHANGE_NAME = "delete_cache";


    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {
        run(HOST);
    }


    public static void run(String host) throws java.io.IOException, java.lang.InterruptedException {

        Logger logger = new Logger();
        Storage storage = new Storage();
        // this is to make sure the queue retires polling if messages are not ack
        boolean autoAck = false;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();


        //TODO commoditize the creation of the consumer / controller
        // UPDATE
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, UPDATE_EXCHANGE_NAME, "");

        // registering the listeners, basically which controller responds to which message
        Consumer updateCacheConsumer = new LocalConsumer(channel, logger, new UpdateMessageController(storage), UpdateCacheMessage.class);
        channel.basicConsume(queueName, autoAck, updateCacheConsumer);


        // DELETE
        String queueNameDelete = channel.queueDeclare().getQueue();
        channel.queueBind(queueNameDelete, DELETE_EXCHANGE_NAME, "");

        // registering the listeners, basically which controller responds to which message
        Consumer deleteCacheConsumer = new LocalConsumer(channel, logger, new DeleteMessageControler(storage), DeleteCacheMessage.class);
        channel.basicConsume(queueNameDelete, autoAck, deleteCacheConsumer);


        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");



    }

}