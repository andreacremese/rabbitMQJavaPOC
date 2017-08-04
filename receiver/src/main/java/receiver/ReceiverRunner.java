package receiver;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiverRunner {

    private String _exchangeName;
    private Logger _log;
    private String[] _topics;

    public ReceiverRunner(String exchangeName, String[] topics, Logger log) {
        _log = log;
        _exchangeName = exchangeName;
        _topics = topics;
    }

    public void run() throws java.io.IOException, java.lang.InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        // autogenerate the queue
        String queueName = channel.queueDeclare().getQueue();
        // bind to the exchange, to a certain topic.
        for (String topic : _topics) {
            channel.queueBind(queueName, _exchangeName, topic);
        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new LocalConsumer(channel, _log);
        // this is to make sure the queue retires polling if messages are not ack
        boolean autoAck = false;
        channel.basicConsume(queueName, autoAck, consumer);
    }


}
