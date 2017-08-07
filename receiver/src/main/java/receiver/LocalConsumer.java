package receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import messages.UpdateChacheMessage;

import java.io.IOException;

public class LocalConsumer extends DefaultConsumer {

    private Channel channel;
    private Logger log;
    private Storage _storage;

    // manual dependency injection
    public LocalConsumer(Channel channel, Logger logger, Storage storage) {
        super(channel);
        this.log = logger;
        _storage = storage;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        String message = new String(body, "UTF-8");
        // instert here whatever logic is needed, using whatever service you injected.
        this.addToCache(message);
        log.print(" [x] Received and processed '" + message + "'");
    }

    private void addToCache(String s) throws IOException {
        UpdateChacheMessage msg = new ObjectMapper().readValue(s, UpdateChacheMessage.class);
        _storage.add(msg);
    }
}
