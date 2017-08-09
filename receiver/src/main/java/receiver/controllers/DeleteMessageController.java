package receiver.controllers;

import messages.DeleteCacheMessage;
import messages.GenericMessage;
import receiver.services.Storage;

import java.io.IOException;

public class DeleteMessageController extends GenericController {
    public DeleteMessageController(Storage storage) {
        super(storage);
    }

    public void handleMessage(GenericMessage msg) throws IOException {
        DeleteCacheMessage m = (DeleteCacheMessage) msg;
        _storage.delete(m.key);
    }
}
