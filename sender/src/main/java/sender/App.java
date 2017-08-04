package sender;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

public class App  {
    private final static String EXCHANGE_NAME = "xc";

    public static void main(String[] args) throws java.io.IOException {
        boolean durable = true;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String message = "Hello World!";

        channel.basicPublish(EXCHANGE_NAME, args[0], null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
