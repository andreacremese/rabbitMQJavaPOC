package receiver.consumers;

import com.rabbitmq.client.Channel;
import messages.GenericMessage;
import receiver.services.Logger;
import receiver.services.Storage;
import receiver.controllers.GenericController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Subscription<T extends GenericMessage, K extends GenericController> {

    private String _exchagneName;

    private Class<T> _msgClass;
    private Class<K> _ctrClass;
    private LocalConsumer _consumer;

    public LocalConsumer getConsumer() {
        return this._consumer;
    }

    public Subscription(String exchageName, Class<T> tClass, Class<K> cClass) {
        this._exchagneName = exchageName;
        this._msgClass = tClass;
        this._ctrClass = cClass;
    }

    public void startListener(Channel channel, Storage storage, Logger logger) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, this._exchagneName, "");
        Class[] cArg = new Class[1];
        cArg[0] = Storage.class;

        GenericController ctrl = (GenericController) this._ctrClass.getDeclaredConstructor(cArg).newInstance(storage);

        // registering the listeners, basically which controllers responds to which message
        this._consumer = new LocalConsumer(channel, logger, ctrl , this._msgClass);
        channel.basicConsume(queueName, true, this._consumer);

        logger.print("started listener on XC " + this._exchagneName);
    }
}

