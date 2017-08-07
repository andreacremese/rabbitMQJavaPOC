package receiver;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiverRunner {

    private String _exchangeName;
    private Logger _log;
    private String[] _topics;
    private Storage _storage;

    public ReceiverRunner(String exchangeName, String[] topics, Logger log, Storage storage) {
        _log = log;
        _exchangeName = exchangeName;
        _topics = topics;
        _storage = storage;
    }

    public void run() throws java.io.IOException, java.lang.InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        // autogenerate the queue
        String queueName = channel.queueDeclare().getQueue();
        // bind to the exchange, to a certain topic.
        channel.queueBind(queueName, _exchangeName, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new LocalConsumer(channel, _log, _storage);
        // this is to make sure the queue retires polling if messages are not ack
        boolean autoAck = false;
        channel.basicConsume(queueName, autoAck, consumer);
    }


}
