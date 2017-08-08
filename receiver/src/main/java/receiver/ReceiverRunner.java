package receiver;

import com.rabbitmq.client.*;
import messages.UpdateCacheMessage;

public class ReceiverRunner {

    private static final String HOST = "localhost";
    private static final String EXCHANGE_NAME = "updated_cache";


    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {
        run(HOST, EXCHANGE_NAME);
    }


    public static void run(String host, String exchange) throws java.io.IOException, java.lang.InterruptedException {

        Logger logger = new Logger();
        Storage storage = new Storage();



        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        // autogenerate the queue
        String queueName = channel.queueDeclare().getQueue();
        // bind to the exchange, to a certain topic.
        channel.queueBind(queueName, exchange, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // registering the listeners, basically which controller responds to which message
        Consumer updateCacheConsumer = new LocalConsumer(channel, logger, new UpdateMessageController(storage), UpdateCacheMessage.class);

        // this is to make sure the queue retires polling if messages are not ack




        boolean autoAck = false;
        channel.basicConsume(queueName, autoAck, updateCacheConsumer);
    }

}