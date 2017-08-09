package receiver.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import messages.GenericMessage;
import receiver.services.Logger;
import receiver.controllers.GenericController;

import java.io.IOException;

public class LocalConsumer<M extends GenericMessage> extends DefaultConsumer {

    private Channel channel;
    private Logger _log;
    private GenericController _controller;
    private Class<M> _type;

    // manual dependency injection
    public LocalConsumer(Channel channel, Logger logger, GenericController controller, Class<M> type) {
        super(channel);
        _log = logger;
        _controller = controller;
        _type = type;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        String message = new String(body, "UTF-8");
        // pass to the controller
        GenericMessage msg = new ObjectMapper().readValue(message, _type);
        _controller.handleMessage(msg);
        _log.print(" [x] Received and processed '" + message + "'");
    }
}
