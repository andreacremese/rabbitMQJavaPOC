package receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import messages.GenericMessage;

import java.io.IOException;

public class LocalConsumer<MessageType extends GenericMessage> extends DefaultConsumer {

    private Channel channel;
    private Logger _log;
    private GenericController _controller;
    private Class<MessageType> _type;

    // manual dependency injection
    public LocalConsumer(Channel channel, Logger logger, GenericController controller, Class<MessageType> type) {
        super(channel);
        _log = logger;
        _controller = controller;
        _type = type;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        String message = new String(body, "UTF-8");
        // instert here whatever logic is needed, using whatever service you injected.
        this.addToCache(message);
        _log.print(" [x] Received and processed '" + message + "'");
    }

    // businss logic may need to be separated!

    private void addToCache(String s) throws IOException {
        GenericMessage msg = new ObjectMapper().readValue(s, _type);
        _controller.handleMessage(msg);
    }

}
