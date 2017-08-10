package receiver.controllers;

import messages.DeleteCacheMessage;
import messages.GenericMessage;
import messages.UpdateCacheMessage;
import receiver.services.Storage;

import java.io.IOException;

public class DeleteMessageController extends GenericController {
    public DeleteMessageController(Storage storage) {
        super(storage, DeleteCacheMessage.class);
    }

    public void handleMessage(GenericMessage msg) throws IOException {
        DeleteCacheMessage m = (DeleteCacheMessage) msg;
        getStorage().delete(m.key);
    }
}
