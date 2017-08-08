package receiver;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import messages.DeleteCacheMessage;
import messages.GenericMessage;
import messages.UpdateCacheMessage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public enum Subscription {

    UPDATE_CACHE ("updated_cache", UpdateCacheMessage.class, UpdateMessageController.class),
    DELETE_CACHE ("delete_cache", DeleteCacheMessage.class, DeleteMessageControler.class);



    private Class<?> _msg;
    private Class<?> _ctrl;
    private String _exchagneName;

    private Subscription(String exchageName, Class<?> msg, Class<?> ctrl) {
        this._exchagneName = exchageName;
        this._msg = msg;
        this._ctrl = ctrl;
    }

    public void startListener(Channel channel, Storage storage, Logger logger) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, this._exchagneName, "");
        Class[] cArg = new Class[1];
        cArg[0] = Storage.class;

        GenericController ctrl = (GenericController) this._ctrl.getDeclaredConstructor(cArg).newInstance(storage);
        // registering the listeners, basically which controller responds to which message
        Consumer updateCacheConsumer = new LocalConsumer(channel, logger, ctrl , this._msg);
        channel.basicConsume(queueName, true, updateCacheConsumer);

        logger.print("started listener on XC " + this._exchagneName);
    }

}
