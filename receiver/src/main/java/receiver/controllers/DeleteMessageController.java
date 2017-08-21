package receiver.controllers;

import com.rabbitmq.client.AMQP;
import messages.DeleteCacheMessage;
import messages.GenericMessage;
import messages.UpdateCacheMessage;
import receiver.services.Storage;

import java.io.IOException;

public class DeleteMessageController extends GenericController<DeleteCacheMessage> {
    public DeleteMessageController(Storage storage) {
        super(storage, DeleteCacheMessage.class);
    }

    public void handleMessage(DeleteCacheMessage msg) throws IOException {
        getStorage().delete(msg.key);
    }
}
