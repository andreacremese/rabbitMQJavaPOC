package receiver;

import com.rabbitmq.client.*;
import java.io.IOException;

public class LocalConsumer extends DefaultConsumer {

    private Channel channel;
    private Logger log;

    // manual dependency injection
    public LocalConsumer(Channel channel, Logger logger) {
        super(channel);
        this.log = logger;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        String message = new String(body, "UTF-8");
        // instert here whatever logic is needed, using whatever service you injected.
        log.print(" [x] Received and processed '" + message + "'");
    }
}
