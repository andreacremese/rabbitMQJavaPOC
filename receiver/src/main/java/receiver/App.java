package receiver;

import com.rabbitmq.client.*;

import java.io.IOException;

public class App {
    private final static String EXCHANGE_NAME = "logs_topics";


    public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        // autogenerate the queue
        String queueName = channel.queueDeclare().getQueue();
        // bind to the exchange, to a certain topic.
        channel.queueBind(queueName, EXCHANGE_NAME, "*.red.*");
        channel.queueBind(queueName, EXCHANGE_NAME, "*.amber.*");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // this is to make sure the queue retires polling if messages are not ack
        boolean autoAck = false;
        channel.basicConsume(queueName, autoAck, consumer);
    }

}