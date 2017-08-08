package receiver;

import messages.GenericMessage;
import messages.UpdateChacheMessage;

public class UpdateMessageController extends GenericController {

    public UpdateMessageController(Storage storage) {
        super(storage);
    }

    public void handleMessage(GenericMessage msg) {

        this._storage.add((UpdateChacheMessage) msg);
    }
}
