package receiver;

import messages.GenericMessage;
import messages.UpdateChacheMessage;

import java.io.IOException;

public abstract class GenericController<MessageType extends GenericMessage> {

    public Storage _storage;

    public GenericController(Storage storage) {
        _storage = storage;
    }

    public abstract void handleMessage(GenericMessage msg) throws IOException;
}
