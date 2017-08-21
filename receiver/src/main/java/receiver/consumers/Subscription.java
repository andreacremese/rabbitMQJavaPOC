package receiver.consumers;

import com.rabbitmq.client.Channel;
import receiver.services.Logger;
import receiver.controllers.GenericController;

import java.io.IOException;

public class Subscription<K extends GenericController> {

    private String _exchangeName;

    private K _ctrClass;
    private LocalConsumer _consumer;

    public LocalConsumer getConsumer() {
        return this._consumer;
    }

    public Subscription(String exchageName, K cClass) {
        this._exchangeName = exchageName;
        this._ctrClass = cClass;
    }

    public void startListener(Channel channel, Logger logger) throws IOException {
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, this._exchangeName, "");

        // registering the listeners, basically which controllers responds to which message
        this._consumer = new LocalConsumer(channel, logger, _ctrClass, _ctrClass.getMsg());
        channel.basicConsume(queueName, true, this._consumer);

        logger.print("started listener on XC " + this._exchangeName);
    }
}

