package receiver;

import com.rabbitmq.client.*;
import messages.UpdateChacheMessage;

public class ReceiverRunner {

    private static final String HOST = "localhost";
    private static final String EXCHANGE_NAME = "updated_cache";






    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {
        run(HOST, EXCHANGE_NAME);
    }



    public static void run(String host, String exchange) throws java.io.IOException, java.lang.InterruptedException {

        Logger logger = new Logger();
        Storage storage = new Storage();
        GenericController controller = new UpdateMessageController(storage);



        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        // autogenerate the queue
        String queueName = channel.queueDeclare().getQueue();
        // bind to the exchange, to a certain topic.
        channel.queueBind(queueName, exchange, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new LocalConsumer(channel, logger, controller, UpdateChacheMessage.class);
        // this is to make sure the queue retires polling if messages are not ack
        boolean autoAck = false;
        channel.basicConsume(queueName, autoAck, consumer);
    }

}