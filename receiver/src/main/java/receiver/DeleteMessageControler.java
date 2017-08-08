package receiver;

import com.rabbitmq.client.AMQP;
import messages.DeleteCacheMessage;
import messages.GenericMessage;

import java.io.IOException;

public class DeleteMessageControler  extends GenericController {
    public DeleteMessageControler(Storage storage) {
        super(storage);
    }

    public void handleMessage(GenericMessage msg) throws IOException {
        DeleteCacheMessage m = (DeleteCacheMessage) msg;
        _storage.delete(m.key);
    }
}
